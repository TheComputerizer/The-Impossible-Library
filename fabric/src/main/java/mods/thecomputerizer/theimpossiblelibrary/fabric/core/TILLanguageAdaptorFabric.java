package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import com.chocohead.mm.api.ClassTinkerers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabricTest;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.loader.TILModInjectorFabric;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.ModContainerImpl;
import net.fabricmc.loader.impl.discovery.ModCandidateImpl;
import net.fabricmc.loader.impl.entrypoint.EntrypointStorage;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.loader.impl.metadata.DependencyOverrides;
import net.fabricmc.loader.impl.metadata.EntrypointMetadata;
import net.fabricmc.loader.impl.metadata.LoaderModMetadata;
import net.fabricmc.loader.impl.metadata.ModMetadataParser;
import net.fabricmc.loader.impl.metadata.ParseMetadataException;
import net.fabricmc.loader.impl.metadata.VersionOverrides;
import net.fabricmc.loader.impl.util.UrlUtil;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static net.fabricmc.loader.impl.FabricLoaderImpl.INSTANCE;

@IndirectCallers
public class TILLanguageAdaptorFabric implements LanguageAdapter {
    
    private final CoreAPI core;
    Collection<ModContainerImpl> queuedContainers;
    
    public TILLanguageAdaptorFabric() {
        FabricLauncher launcher = FabricLauncherBase.getLauncher();
        String target = addCoreSources(launcher);
        Class<?> pairCls = ClassHelper.findClass("org.apache.commons.lang3.tuple.Pair",ClassLoader.getSystemClassLoader());
        if(Objects.nonNull(pairCls)) launcher.addToClassPath(UrlUtil.getCodeSource(pairCls));
        else TILRef.logFatal("Failed to load Pair class! Mod writing will likely break");
        this.core = initializeCore(launcher.getTargetClassLoader(),target);
        scheduleContainers();
        TILDev.logInfo("Instantiated multiversionAdaptor");
    }
    
    String addCoreSources(FabricLauncher launcher) {
        if(Boolean.parseBoolean(System.getProperty("til.dev"))) {
            String coreAPI = "mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI";
            try {
                Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(coreAPI);
                launcher.addToClassPath(Paths.get(clazz.getProtectionDomain().getCodeSource().getLocation().toURI()));
            } catch(Exception ex) {
                throw new RuntimeException("Failed to load CoreAPI at "+coreAPI, ex);
            }
        }
        return addMoreSources(launcher);
    }
    
    String addMoreSources(FabricLauncher launcher) {
        String version = INSTANCE.getGameProvider().getNormalizedGameVersion().split("-")[0];
        String className = CoreAPI.findLoadingClass(FABRIC,version);
        ClassLoader loader = DEV ? ClassLoader.getSystemClassLoader() : launcher.getTargetClassLoader();
        Class<?> clazz = ClassHelper.findClass(className,loader);
        while(Objects.nonNull(clazz) && clazz!=Object.class) {
            addSource(launcher,ClassHelper.getSourceURL(clazz));
            clazz = clazz.getSuperclass();
            if(CoreAPI.class.getName().equals(clazz.getName())) break;
        }
        return className;
    }
    
    void addSource(FabricLauncher launcher, @Nullable URL url) {
        if(Objects.nonNull(url)) {
            try {
                launcher.addToClassPath(Paths.get(url.toURI()));
                TILDev.logDebug("Added loader source {}",url);
            } catch(URISyntaxException ex) {
                TILRef.logError("Failed to add {} to the classpath",url,ex);
            }
        }
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
        if(core.isClientSide() && info.isClient())
            buildEntryPoint(json,"client",mainClasspath+"$LoaderClient");
        else if(core.isServerSide() && info.isServer())
            buildEntryPoint(json,"server",mainClasspath+"$LoaderServer");
        return json;
    }
    
    JsonObject buildEntryPointTests(CoreAPI core) {
        JsonObject json = new JsonObject();
        String mainClasspath = TILCommonEntryPointFabricTest.class.getName();
        buildEntryPoint(json,"main",mainClasspath);
        if(core.isClientSide()) buildEntryPoint(json,"client",mainClasspath+"$LoaderClient");
        else if(core.isServerSide()) buildEntryPoint(json,"server",mainClasspath+"$LoaderServer");
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
        buildModClasses(core,candidate,info);
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
    
    @SneakyThrows
    void buildModClasses(CoreAPI core, MultiVersionModCandidate candidate, MultiVersionModInfo info) {
        for(Pair<String,byte[]> classBytes : core.getModData(new File("."),candidate,info).writeModClass()) {
            String name = classBytes.getLeft();
            if(ClassTinkerers.define(name,classBytes.getRight()))
                TILDev.logInfo("Built mod entrypoint at {}",name);
            else TILRef.logError("Failed to define class {}");
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override public <T> T create(ModContainer mod, String value, Class<T> type) {
        ClassLoader loader = //DEV ? ClassLoader.getSystemClassLoader() :
                FabricLauncherBase.getLauncher().getTargetClassLoader();
        Object instance = ClassHelper.initialize(ClassHelper.findClass(value,loader));
        if(instance instanceof TILModInjectorFabric && Objects.nonNull(this.queuedContainers)) {
            TILDev.logInfo("Queuing {} new mod containers",this.queuedContainers.size());
            ((TILModInjectorFabric)instance).setContainers(this.queuedContainers);
        }
        return (T)instance;
    }
    
    CoreAPI initializeCore(ClassLoader classLoader, String classname) {
        CoreAPI core = (CoreAPI)ClassHelper.initialize(ClassHelper.findClass(classname,classLoader));
        if(Objects.nonNull(core)) {
            TILRef.logInfo("Loading core mods");
            core.loadCoreModInfo(classLoader,false);
            core.instantiateCoreMods();
            TILRef.logInfo("Writing mods");
            core.writeModContainers(classLoader,false);
        } else TILRef.logFatal("Failed in instantiate CoreAPI!");
        return core;
    }
    
    Collection<ModContainerImpl> loadCandidateInfos(CoreAPI core) {
        Collection<ModContainerImpl> candidates = new HashSet<>();
        VersionOverrides versionOverrides = new VersionOverrides();
        DependencyOverrides dependencyOverrides = new DependencyOverrides(INSTANCE.getConfigDir());
        TILRef.logInfo("Finding multiversion mod candidates");
        for(Entry<MultiVersionModCandidate,Collection<MultiVersionModInfo>> fileEntry : core.getModInfo().entrySet()) {
            MultiVersionModCandidate candidate = fileEntry.getKey();
            TILRef.logInfo("Candidate at {} has {} mods",candidate.getFile().toPath(),fileEntry.getValue().size());
            for(MultiVersionModInfo info : fileEntry.getValue()) {
                LoaderModMetadata metadata = buildMetaData(core,candidate,info,versionOverrides,dependencyOverrides);
                List<Path> paths = new ArrayList<>();
                if(DEV) {
                    try {
                        URL url = ClassHelper.getSourceURL(CoreAPI.class);
                        if(Objects.nonNull(url)) paths.add(Paths.get(url.toURI()));
                    } catch(URISyntaxException ex) {
                        TILRef.logError("Failed to get path for {}", core.getClass());
                    }
                } else paths.add(candidate.getFile().toPath());
                //if(MODID.equals(info.getModID())) paths.addAll(this.loaderSources);
                TILRef.logInfo("Source paths for {} are {}",info.getModID(),paths);
                TILRef.logInfo("Successfully built mod metadata! Attempting some reflection magic");
                Object mod = ReflectionHelper.invokeStaticMethod(ModCandidateImpl.class,"createPlain",
                        new Class<?>[]{List.class,LoaderModMetadata.class,boolean.class,Collection.class},
                        paths,metadata,INSTANCE.isDevelopmentEnvironment(),Collections.emptyList());
                TILRef.logInfo("Successfully performed reflection magic",info);
                if(mod instanceof ModCandidateImpl) {
                    candidates.add(new ModContainerImpl((ModCandidateImpl)mod));
                    TILRef.logInfo("Successfully collected mod candidate for {}",info);
                } else TILRef.logError("Loaded object isn't a mod candidate?? {}",mod);
            }
        }
        return candidates;
    }
    
    void queueContainers(Collection<ModContainerImpl> containers) {
        if(Objects.isNull(this.queuedContainers)) this.queuedContainers = containers;
        else this.queuedContainers.addAll(containers);
    }
    
    void scheduleContainers() {
        Collection<ModContainerImpl> containers = loadCandidateInfos(this.core);
        Object modMapObj = ReflectionHelper.getFieldInstance(INSTANCE,INSTANCE.getClass(),"modMap");
        Object storageObj = ReflectionHelper.getFieldInstance(INSTANCE,INSTANCE.getClass(),"entrypointStorage");
        if(modMapObj instanceof Map<?,?> && storageObj instanceof EntrypointStorage) {
            @SuppressWarnings("unchecked")
            Map<String,ModContainerImpl> modMap = (Map<String,ModContainerImpl>)modMapObj;
            EntrypointStorage storage = (EntrypointStorage)storageObj;
            Map<String,LanguageAdapter> adapters = new HashMap<>();
            adapters.put("multiversionAdaptor",this);
            for(ModContainerImpl container : containers) {
                LoaderModMetadata metadata = container.getMetadata();
                modMap.put(metadata.getId(),container);
                for(String entrypoint : metadata.getEntrypointKeys()) {
                    for(EntrypointMetadata entryMeta : metadata.getEntrypoints(entrypoint)) {
                        try {
                            storage.add(container,entrypoint,entryMeta,adapters);
                        } catch(Exception ex) {
                            TILRef.logError("Failed to add entrypoint {} {} for {}", entryMeta,
                                            entryMeta.getValue(), metadata.getId(), ex);
                        }
                    }
                }
            }
        } else TILDev.logError("modMap is {} and storage is {}",modMapObj,storageObj);
        TILRef.logDebug("Adding {} mod containers to the queue",containers.size());
        queueContainers(containers);
    }
}