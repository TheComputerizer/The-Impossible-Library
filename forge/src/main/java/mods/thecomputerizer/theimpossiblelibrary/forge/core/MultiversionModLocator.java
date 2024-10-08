package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.TransformingClassLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILModFileForge;
import net.minecraftforge.fml.loading.LibraryFinder;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileLocator;
import net.minecraftforge.forgespi.locating.IModFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.jar.Manifest;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

@IndirectCallers
public class MultiversionModLocator extends AbstractJarFileLocator {
    
    private static final Set<ClassLoader> LOADERS = new HashSet<>(
            Collections.singletonList(ClassLoader.getSystemClassLoader()));
    
    static {
        URL source = ClassHelper.getSourceURL(MultiversionModLocator.class);
        ClassLoader pluginLoader = MultiversionModLocator.class.getClassLoader();
        String version = System.getenv("MC_VERSION");//INSTANCE.environment().getProperty(VERSION.get()).orElse("no version map");
        String coreName = CoreAPI.findLoadingClass(FORGE,version);
        for(ClassLoader loader : LOADERS) {
            if(loader!=pluginLoader) {
                if(addURL(loader,source)) TILRef.logInfo("Added {} to {}", source, loader);
                else TILRef.logError("Failed to add {} to {}",source,loader);
            } else TILDev.logDebug("{} already present on {}", source, loader);
            Class<?> coreClass = ClassHelper.findClass(coreName,loader);
            if(Objects.nonNull(coreClass)) {
                ClassHelper.initialize(coreClass);
                TILRef.logInfo("Initialized {} on {}",coreName,loader);
            } else TILRef.logError("Failed to initialize {} on {}",coreName,loader);
        }
    }
    
    static boolean addURL(ClassLoader loader, URL url) {
        if(loader instanceof URLClassLoader) return ClassHelper.loadURL((URLClassLoader)loader, url);
        if(loader instanceof TransformingClassLoader) {
            Field field = ReflectionHelper.getField(TransformingClassLoader.class,"delegatedClassLoader");
            if(Objects.nonNull(field)) {
                Object instance = ReflectionHelper.getFieldInstance(loader,field);
                if(instance instanceof URLClassLoader) {
                    if(ClassHelper.loadURL((URLClassLoader)instance,url)) {
                        TILRef.logDebug("Successfully loaded URL to mod class loader {}",url);
                        return true;
                    } else TILRef.logError("Failed to load URL to mod class loader {}",url);
                } else TILRef.logError("delegatedClassLoader is not an instance of URLClassLoader??");
            } else TILRef.logError("Unable to find delegatedClassLoader field??");
        }
        return false;
    }
    
    private static final String MANIFEST = "META-INF/MANIFEST.MF";
    
    private final Map<MultiVersionModCandidate,TILModFileForge> candidateMap = new HashMap<>();
    
    public MultiversionModLocator() {
        TILRef.logInfo("Loading plugin loaded with {}",getClass().getClassLoader());
    }
    
    void checkPath(MultiVersionLoaderAPI loader, Path path, Predicate<Path> filter) {
        if(Files.isDirectory(path)) return;
        String fileName = path.getFileName().toString();
        TILRef.logInfo("Checking if file {} is the loader",fileName);
        if(Objects.isNull(MultiVersionModCandidate.loaderFile) && TILDev.isLoader(fileName)) {
            TILRef.logInfo("File is the loader");
            MultiVersionModCandidate.loaderFile = path.toFile();
        }
        if(filter.test(path)) {
            TILRef.logInfo("Found mod candidate at {}",path);
            loader.addPotentialModPath(path);
        }
    }
    
    void checkURL(MultiVersionLoaderAPI loader, URL url, Predicate<Path> filter) {
        Path path = LibraryFinder.findJarPathFor(MANIFEST, "manifest_jar", url);
        checkPath(loader, path, filter);
    }
    
    void findFiles(MultiVersionLoaderAPI loader, Predicate<Path> filter, File... files) {
        TILRef.logInfo("Loading {} mod files",files.length);
        for(File mod : files) {
            TILRef.logInfo("Loading mod file at path",mod.toPath());
            checkPath(loader, mod.toPath(), filter);
        }
    }
    
    void findPaths(ClassLoader classLoader, MultiVersionLoaderAPI loader) {
        Predicate<Path> filter = path -> findManifest(path)
                .map(Manifest::getMainAttributes)
                .filter(MultiVersionModFinder::hasMods)
                .isPresent();
        findURLs(loader, classLoader, filter);
        findFiles(loader, filter, FileHelper.list(loader.findModRoot(), File::isFile));
    }
    
    void findURLs(MultiVersionLoaderAPI loader, ClassLoader classLoader, Predicate<Path> filter) {
        try {
            final Enumeration<URL> manifests = classLoader.getResources(MANIFEST);
            while(manifests.hasMoreElements()) checkURL(loader, manifests.nextElement(), filter);
        } catch(IOException ex) {
            TILRef.logError("Failed to calculate URLs for paths with {} using {}",MANIFEST,classLoader,ex);
        }
    }
    
    @Override public void initArguments(Map<String,?> arguments) {
        for(ClassLoader loader : LOADERS) initFor(loader);
    }
    
    void initFor(ClassLoader loader) {
        Object core = CoreAPI.findInstance(loader);
        if(Objects.isNull(core))
            throw new RuntimeException("Failed to initialize multiversion mod loader! Cannot find CoreAPI on "+loader);
        findPaths(loader,(MultiVersionLoaderAPI)CoreAPI.invoke(core,"getLoader"));
        loadMods(loader,core);
    }
    
    public void loadCandidateInfos(Map<?,?> infoMap) {
        for(Entry<?,?> entry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = (MultiVersionModCandidate)entry.getKey();
            Path sourcePath = candidate.getFile().toPath();
            Collection<?> infos = (Collection<?>)entry.getValue();
            this.candidateMap.put(candidate,new TILModFileForge(sourcePath,this,infos));
        }
    }
    
    private void loadMods(ClassLoader loader, Object core) {
        Class<?>[] withLoader = new Class<?>[]{ClassLoader.class};
        CoreAPI.invoke(core,"loadCoreModInfo",withLoader,loader);
        CoreAPI.invoke(core,"instantiateCoreMods");
        CoreAPI.invoke(core,"writeModContainers",withLoader,loader);
        Object infoMap = CoreAPI.invoke(core,"getModInfo");
        loadCandidateInfos((Map<?,?>)infoMap);
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @SuppressWarnings("resource")
    @Override public List<IModFile> scanMods() {
        TILRef.logDebug("Scanning for mods in multiversion jars");
        List<IModFile> mods = new ArrayList<>();
        Map<String,MultiVersionModData> data = CoreAPI.getInstance().getModData(new File("."));
        for(TILModFileForge candidate : this.candidateMap.values()) {
            this.modJars.compute(candidate,(file,system) -> createFileSystem(file));
            candidate.populateMultiversionData(data);
            mods.add(candidate);
        }
        return Collections.unmodifiableList(mods);
    }
}
