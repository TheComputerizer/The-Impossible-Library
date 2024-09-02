package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.TILClientEntryPointFabricTest;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabricTest;
import mods.thecomputerizer.theimpossiblelibrary.fabric.server.TILServerEntryPointFabricTest;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.discovery.ModCandidateImpl;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.loader.impl.launch.knot.Knot;
import net.fabricmc.loader.impl.metadata.DependencyOverrides;
import net.fabricmc.loader.impl.metadata.LoaderModMetadata;
import net.fabricmc.loader.impl.metadata.ModMetadataParser;
import net.fabricmc.loader.impl.metadata.ParseMetadataException;
import net.fabricmc.loader.impl.metadata.VersionOverrides;
import net.fabricmc.loader.impl.util.UrlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.nio.charset.StandardCharsets.UTF_8;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

@IndirectCallers
public class TILLanguageAdaptor implements LanguageAdapter {
    
    private final List<Path> loaderSources;
    private final CoreAPI core;
    
    public TILLanguageAdaptor() {
        this.loaderSources = new ArrayList<>();
        FabricLoaderImpl loader = FabricLoaderImpl.INSTANCE;
        if(loader.isDevelopmentEnvironment()) {
            String sources = System.getProperty("til.dev.sources");
            if(Objects.nonNull(sources)) {
                Logger log = LogManager.getLogger("TIL Source Loader");
                log.info("Attempting to load sources from {}",sources);
                Path source = UrlUtil.getCodeSource(TILLanguageAdaptor.class);
                log.info("Found entrypoint source path {}",source);
                Path root = source.getParent().getParent().getParent().getParent();
                log.info("Assuming root path {}",root);
                for(String sourcePath : sources.split(File.pathSeparator)) {
                    Path path = root.resolve(sourcePath);
                    log.info("Adding source path {}",path);
                    if(path.toFile().exists()) {
                        Knot.getLauncher().addToClassPath(path);
                        this.loaderSources.add(path);
                    } else log.error("Failed to add nonexistent file from path");
                }
            }
        }
        GameVersion version = CoreAPI.parseVersion(loader.getGameProvider().getNormalizedGameVersion().split("-")[0]);
        if(Objects.nonNull(version)) {
            String versionName = version.getName().replace('.','_');
            String className = version.getPackageName(FABRIC,BASE_PACKAGE)+".core.TILCoreFabric"+versionName;
            ClassLoader classLoader = FabricLauncherBase.getLauncher().getTargetClassLoader();
            Class<?> clazz = ClassHelper.findClass(className,classLoader);
            this.core = initializeCore(classLoader,clazz);
        } else this.core = null;
        TILRef.logInfo("Instantiated multiversionAdaptor");
    }
    
    JsonObject buildDependencies(String modid) {
        JsonObject json = new JsonObject();
        json.addProperty("fabric","*");
        json.addProperty("fabricloader",">=0.14.0");
        json.addProperty("java",">=8");
        json.addProperty("minecraft",">=1.16.5");
        if(!MODID.equals(modid)) json.addProperty(MODID,">="+VERSION);
        return json;
    }
    
    void buildEntryPoint(JsonObject json, String name, String ... entryPoints) {
        JsonArray array = new JsonArray();
        for(String entryPoint : entryPoints) {
            JsonObject entry = new JsonObject();
            entry.addProperty("adapter","multiversionAdaptor");
            entry.addProperty("value",entryPoint);
            array.add(entry);
        }
        json.add(name,array);
    }
    
    JsonObject buildEntryPoints(CoreAPI core, MultiVersionModInfo info) {
        JsonObject json = new JsonObject();
        String mainClasspath = info.getModClasspath();
        buildEntryPoint(json,"main",mainClasspath);
        if(core.getSide().isClient())
            buildEntryPoint(json,"client",mainClasspath.replace("CommonMod","ClientMod"));
        else buildEntryPoint(json,"server",mainClasspath.replace("CommonMod","ServerMod"));
        return json;
    }
    
    JsonObject buildEntryPointTests(CoreAPI core) {
        JsonObject json = new JsonObject();
        buildEntryPoint(json,"main",TILCommonEntryPointFabricTest.class.getName());
        if(core.getSide().isClient())
            buildEntryPoint(json,"client",TILClientEntryPointFabricTest.class.getName());
        else buildEntryPoint(json,"server",TILServerEntryPointFabricTest.class.getName());
        return json;
    }
    
    LoaderModMetadata buildMetaData(CoreAPI core, MultiVersionModCandidate candidate, MultiVersionModInfo info,
            VersionOverrides versionOverrides, DependencyOverrides dependencyOverrides) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setLenient().setPrettyPrinting().create();
        JsonObject json = new JsonObject();
        String modid = info.getModID();
        json.addProperty("schemaVersion",1);
        json.addProperty("description",info.getDescription());
        json.addProperty("environment","*");
        json.addProperty("icon","logo.png");
        json.addProperty("id",modid);
        json.addProperty("license","NYI");
        json.addProperty("name",info.getName());
        json.addProperty("version",info.getVersion());
        json.add("depends",buildDependencies(modid));
        if(Objects.nonNull(System.getProperty("til.dev.testModLoading")))
            json.add("entrypoints",buildEntryPointTests(core));
        else json.add("entrypoints",buildEntryPoints(core,info));
        try(InputStream stream = new ByteArrayInputStream(gson.toJson(json).getBytes(UTF_8))) {
            Path path = candidate.getFile().toPath();
            return ModMetadataParser.parseMetadata(stream,path.toString(),null,versionOverrides,
                                                   dependencyOverrides,DEV);
        } catch(IOException ex) {
            TILRef.logError("Failed to read mod metadata from stream of {}",json,ex);
        } catch(ParseMetadataException ex) {
            TILRef.logError("Failed to parse mod metadata from stream of {}",json,ex);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <T> T create(ModContainer mod, String value, Class<T> type) {
        ClassLoader loader = FabricLauncherBase.getLauncher().getTargetClassLoader();
        return (T)ClassHelper.initialize(ClassHelper.findClass(value,loader));
    }
    
    CoreAPI initializeCore(ClassLoader classLoader, Class<?> clazz) {
        CoreAPI core = (CoreAPI)ClassHelper.initialize(clazz);
        if(Objects.nonNull(core)) {
            core.loadCoreModInfo(classLoader,false);
            core.instantiateCoreMods();
            core.writeModContainers(classLoader,false);
            scheduleContainer();
            
        } else TILRef.logFatal("Failed in instantiate CoreAPI!");
        return core;
    }
    
    Collection<ModCandidateImpl> loadCandidateInfos(FabricLoaderImpl loader, CoreAPI core) {
        Collection<ModCandidateImpl> candidates = new HashSet<>();
        VersionOverrides versionOverrides = new VersionOverrides();
        DependencyOverrides dependencyOverrides = new DependencyOverrides(loader.getConfigDir());
        for(Entry<MultiVersionModCandidate,Collection<MultiVersionModInfo>> fileEntry : core.getModInfo().entrySet()) {
            MultiVersionModCandidate candidate = fileEntry.getKey();
            for(MultiVersionModInfo info : fileEntry.getValue()) {
                LoaderModMetadata metadata = buildMetaData(core,candidate,info,versionOverrides,dependencyOverrides);
                List<Path> paths = new ArrayList<>();
                try {
                    URL url = ClassHelper.getSourceURL(CoreAPI.class);
                    if(Objects.nonNull(url)) paths.add(Paths.get(url.toURI()));
                } catch(URISyntaxException ex) {
                    TILRef.logError("Failed to get path for {}",core.getClass());
                }
                //if(MODID.equals(info.getModID())) paths.addAll(this.loaderSources);
                TILRef.logDebug("Source paths for {} are {}",info.getModID(),paths);
                TILRef.logInfo("Successfully built mod metadata! Attempting some reflection magic");
                Object mod = ReflectionHelper.invokeStaticMethod(ModCandidateImpl.class,"createPlain",
                        new Class<?>[]{List.class,LoaderModMetadata.class,boolean.class,Collection.class},
                                                                 paths,metadata,
                        loader.isDevelopmentEnvironment(),Collections.emptyList());
                TILRef.logInfo("Successfully performed reflection magic",info);
                if(mod instanceof ModCandidateImpl) {
                    candidates.add((ModCandidateImpl)mod);
                    TILRef.logInfo("Successfully collected mod candidate for {}",info);
                } else TILRef.logError("Loaded object isn't a mod candidate?? {}",mod);
            }
        }
        return candidates;
    }
    
    /**
     * The adaptor is initialized during an iteration, but we need to add a container to the list being iterated over.
     * Ideally, this can synchronize with the loader and wait for the iteration to finish...
     */
    void scheduleContainer() {
        new ScheduledThreadPoolExecutor(1).execute(() -> {
            Collection<ModCandidateImpl> candidates = loadCandidateInfos(FabricLoaderImpl.INSTANCE,this.core);
            TILRef.logInfo("Collected {} mod candidates! Injecting after the adaptor iteration is finished",
                           candidates.size());
            FabricLoaderImpl loader = FabricLoaderImpl.INSTANCE;
            for(ModCandidateImpl candidate : candidates) {
                synchronized(loader) {
                    ReflectionHelper.invokeMethod(FabricLoaderImpl.class,"addMod",loader,new Class<?>[]{
                            ModCandidateImpl.class},candidate);
                }
            }
        });
    }
}