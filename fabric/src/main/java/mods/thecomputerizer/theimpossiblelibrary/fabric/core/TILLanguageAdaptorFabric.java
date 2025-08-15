package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabricTest;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm.TILFabricASMTarget;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm.TILFabricCoreModLoader;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.loader.TILModInjectorFabric;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
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
import net.fabricmc.loader.impl.util.log.Log;
import org.burningwave.core.assembler.StaticComponentContainer.Configuration.Default;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.charset.StandardCharsets.UTF_8;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static net.fabricmc.loader.impl.FabricLoaderImpl.INSTANCE;
import static net.fabricmc.loader.impl.util.log.LogCategory.ENTRYPOINT;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

@IndirectCallers
public class TILLanguageAdaptorFabric implements LanguageAdapter {
    
    private static final String BURNINGWAVE_PATH = "org.burningwave.core.assembler.StaticComponentContainer";
    private static final String CORE_PATH = "mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI";
    private static final String TOOLFACTORY_PATH = "io.github.toolfactory.jvm.Info";
    
    static void burningWaveProperties() {
        Map<Object,Object> properties = new HashMap<>();
        properties.put("banner.hide","true");
        properties.put("jvm.driver.type","org.burningwave.jvm.NativeDriver");
        properties.put("managed-logger.repository.enabled","false");
        properties.put("priority-of-this-configuration","1000");
        properties.put("resource-releaser.enabled","false");
        try {
            Default.add(properties);
        } catch(Throwable t) {
            Log.error(ENTRYPOINT,"Failed to set default BurningWave properties??",t);
        }
    }
    
    private final CoreAPI core;
    Collection<ModContainerImpl> queuedContainers;
    
    public TILLanguageAdaptorFabric() {
        FabricLauncher launcher = FabricLauncherBase.getLauncher();
        String target = addCoreSources(launcher);
        this.core = scheduleContainers(initializeCore(launcher.getTargetClassLoader(),target));
        if(Objects.nonNull(this.core)) TILDev.logInfo("Successfully nstantiated multiversionAdaptor");
    }
    
    String addCoreSources(FabricLauncher launcher) {
        if(Boolean.parseBoolean(System.getProperty("til.dev"))) {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            for(String className : new String[]{TOOLFACTORY_PATH,BURNINGWAVE_PATH,CORE_PATH}) {
                try {
                    Class<?> clazz = loader.loadClass(className);
                    addSource(launcher,clazz.getProtectionDomain().getCodeSource().getLocation());
                } catch(Exception ex) {
                    throw new RuntimeException("Failed to load class "+className,ex);
                }
            }
        }
        return addMoreSources(launcher);
    }
    
    String addMoreSources(FabricLauncher launcher) {
        String version = INSTANCE.getGameProvider().getNormalizedGameVersion().split("-")[0];
        String className = CoreAPI.findLoadingClass(FABRIC,version);
        ClassLoader loader = launcher.getTargetClassLoader();
        Class<?> clazz = ClassHelper.findClass(className,loader);
        while(Objects.nonNull(clazz) && clazz!=Object.class) {
            addSource(launcher,ClassHelper.getSourceURL(className,loader));
            clazz = clazz.getSuperclass();
            String name = clazz.getName();
            if(CoreAPI.class.getName().equals(name)) break;
        }
        burningWaveProperties();
        return className;
    }
    
    void addSource(FabricLauncher launcher, @Nullable URL url) {
        if(Objects.nonNull(url)) {
            try {
                launcher.addToClassPath(UrlUtil.asPath(url));
                Log.debug(ENTRYPOINT,"Added loader source "+url);
            } catch(Exception ex) {
                Log.error(ENTRYPOINT,"Failed to add "+url+" to the classpath",ex);
            }
        }
    }
    
    void addTransformer(FabricLoader loader, CoreAPI core) {
        if(loader instanceof FabricLoaderImpl)
            TILFabricCoreModLoader.patchTransformer((FabricLoaderImpl)loader,core);
        else TILRef.logError("Unknown FabricLoader type! Cannot add coremod transformer patch to {}",loader);
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
        json.addProperty("license",info.getLicense());
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
        for(Entry<String,byte[]> classBytes : core.getModData(new File("."),candidate,info).writeModClass()) {
            String name = classBytes.getKey();
            TILFabricASMTarget.registerDefinition(name,classBytes.getValue());
            TILRef.logInfo("Built mod entrypoint at {}",name);
        }
    }
    
    @Nullable ModCandidateImpl buildCandidate(List<Path> paths, LoaderModMetadata metadata, String modid) {
        TILRef.logDebug("Successfully built mod metadata for {}! Attempting some reflection magic",modid);
        try {
            return Methods.invokeStaticDirect(ModCandidateImpl.class,"createPlain",paths,metadata,
                                              INSTANCE.isDevelopmentEnvironment(),Collections.emptyList());
        } catch(Throwable t) {
            TILRef.logFatal("Failed to build mod candidate for {}!",metadata.getId(),t);
        }
        return null;
    }
    
    void buildCandidateContainer(Collection<ModContainerImpl> containers, MultiVersionModCandidate candidate,
            MultiVersionModInfo info,
            BiFunction<MultiVersionModCandidate,MultiVersionModInfo,LoaderModMetadata> metaBuilder) {
        ModContainerImpl container = buildCandidateContainer(candidate,info,metaBuilder);
        if(Objects.nonNull(container)) {
            TILRef.logInfo("Successfully built mod container for {}!",info.getModID());
            containers.add(container);
        } else TILRef.logError("Failed to build mod container for {}",info.getModID());
    }
    
    @Nullable ModContainerImpl buildCandidateContainer(MultiVersionModCandidate candidate, MultiVersionModInfo info,
            BiFunction<MultiVersionModCandidate,MultiVersionModInfo,LoaderModMetadata> metaBuilder) {
        List<Path> paths = new ArrayList<>();
        loadCandidatePath(candidate,paths);
        return buildContainer(paths,metaBuilder.apply(candidate,info),info.getModID());
    }
    
    Collection<ModContainerImpl> buildCandidateContainers(CoreAPI core,
            BiFunction<MultiVersionModCandidate,MultiVersionModInfo,LoaderModMetadata> metaBuilder) {
        Collection<ModContainerImpl> containers = new ArrayList<>();
        for(Entry<MultiVersionModCandidate,Collection<MultiVersionModInfo>> fileEntry : core.getModInfo().entrySet())
            for(MultiVersionModInfo info : fileEntry.getValue())
                buildCandidateContainer(containers,fileEntry.getKey(),info,metaBuilder);
        return containers;
    }
    
    @Nullable ModContainerImpl buildContainer(List<Path> paths, LoaderModMetadata metadata, String modid) {
        ModCandidateImpl candidate = buildCandidate(paths,metadata,modid);
        if(Objects.nonNull(candidate)) {
            TILRef.logDebug("Successfully built ModCandidateImpl instance for {}",modid);
            return new ModContainerImpl(candidate);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <T> T create(ModContainer mod, String value, Class<T> type) {
        ClassLoader loader = FabricLauncherBase.getLauncher().getTargetClassLoader();
        Object instance = ClassHelper.initialize(ClassHelper.findClass(value,loader));
        if(instance instanceof TILModInjectorFabric && Objects.nonNull(this.queuedContainers)) {
            TILDev.logInfo("Queuing {} new mod containers",this.queuedContainers.size());
            ((TILModInjectorFabric)instance).setContainers(this.queuedContainers);
        }
        return (T)instance;
    }
    
    CoreAPI initializeCore(ClassLoader targetLoader, String classname) {
        CoreAPI core = (CoreAPI)ClassHelper.initialize(ClassHelper.findClass(classname,targetLoader));
        if(Objects.isNull(core)) {
            TILRef.logFatal("Failed to initialize CoreAPI instance for {} on {}!",classname,targetLoader);
            return null;
        }
        TILRef.logInfo("Loading core mods");
        core.loadCoreModInfo(targetLoader);
        core.instantiateCoreMods();
        addTransformer(FabricLoader.getInstance(),core);
        TILRef.logDebug("Writing mods");
        core.writeModContainers(targetLoader);
        return core;
    }
    
    Collection<ModContainerImpl> loadCandidateInfos(CoreAPI core) {
        TILRef.logDebug("Finding multiversion mod candidates");
        Collection<ModContainerImpl> containers = buildCandidateContainers(core,
                (candidate,info) -> buildMetaData(core,candidate,info,
                        new VersionOverrides(),new DependencyOverrides(INSTANCE.getConfigDir())));
        TILRef.logInfo("Built {} multiversion mod containers",containers.size());
        TILFabricASMTarget.loadDefinitions();
        return containers;
    }
    
    void loadCandidatePath(MultiVersionModCandidate candidate, List<Path> paths) {
        if(DEV) {
            try {
                URL url = ClassHelper.getSourceURL(CoreAPI.class);
                if(Objects.nonNull(url)) paths.add(FileHelper.toPath(url));
            } catch(Exception ex) {
                TILRef.logError("Failed to get path for {}", core.getClass());
            }
        } else paths.add(candidate.getFile().toPath());
    }
    
    void scheduleContainer(EntrypointStorage storage, ModContainerImpl container, String entryPoint,
            EntrypointMetadata metadata, Map<String,LanguageAdapter> adaptors) {
        try {
            storage.add(container,entryPoint,metadata,adaptors);
        } catch(Exception ex) {
            String modid = container.getMetadata().getId();
            TILRef.logError("Failed to add entrypoint {} {} for {}",metadata,metadata.getValue(),modid,ex);
        }
    }
    
    void scheduleContainer(EntrypointStorage storage, ModContainerImpl container, Collection<String> entryPoints,
            Map<String,LanguageAdapter> adaptors, Function<String,Collection<EntrypointMetadata>> metaGetter) {
        for(String entryPoint : entryPoints)
            for(EntrypointMetadata metadata : metaGetter.apply(entryPoint))
                scheduleContainer(storage,container,entryPoint,metadata,adaptors);
    }
    
    void scheduleContainers(Collection<ModContainerImpl> containers, Map<String,ModContainerImpl> modMap,
            EntrypointStorage storage, Map<String,LanguageAdapter> adaptors) {
        for(ModContainerImpl container : containers) {
            LoaderModMetadata metadata = container.getMetadata();
            modMap.put(metadata.getId(),container);
            scheduleContainer(storage,container,metadata.getEntrypointKeys(),adaptors,metadata::getEntrypoints);
        }
    }
    
    CoreAPI scheduleContainers(@Nullable CoreAPI core) {
        if(Objects.isNull(core)) return null;
        Collection<ModContainerImpl> containers = loadCandidateInfos(core);
        Map<String,ModContainerImpl> modMap = Fields.getDirect(INSTANCE,"modMap");
        EntrypointStorage storage = Fields.getDirect(INSTANCE,"entrypointStorage");
        Map<String,LanguageAdapter> adapters = new HashMap<>();
        adapters.put("multiversionAdaptor",this);
        scheduleContainers(containers,modMap,storage,adapters);
        TILRef.logDebug("Adding {} mod containers to the queue",containers.size());
        if(Objects.isNull(this.queuedContainers)) this.queuedContainers = containers;
        else this.queuedContainers.addAll(containers);
        return core;
    }
}