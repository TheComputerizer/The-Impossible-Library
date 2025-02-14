package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

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
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader.TILModFileForge1_16_5;
import net.minecraftforge.fml.loading.LibraryFinder;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileLocator;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.jar.Manifest;

@IndirectCallers
public class MultiVersionModLocator1_16_5 implements TILForgeModLocator {
    
    private static final String MANIFEST = "META-INF/MANIFEST.MF";
    
    private final Map<MultiVersionModCandidate,TILModFileForge1_16_5> candidateMap = new HashMap<>();
    
    public MultiVersionModLocator1_16_5() {
        TILRef.logInfo("1.16.5 Forge Locator plugin loaded on {}",getClass().getClassLoader());
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
        Path path = LibraryFinder.findJarPathFor(MANIFEST,"manifest_jar", url);
        checkPath(loader, path, filter);
    }
    
    void findFiles(MultiVersionLoaderAPI loader, Predicate<Path> filter, File... files) {
        TILRef.logInfo("Loading {} mod files",files.length);
        for(File mod : files) {
            TILRef.logInfo("Loading mod file at path",mod.toPath());
            checkPath(loader, mod.toPath(), filter);
        }
    }
    
    void findPaths(ClassLoader classLoader, IModLocator locator, MultiVersionLoaderAPI loader) {
        Predicate<Path> filter = path -> locator.findManifest(path)
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
    
    @Override public IModFile createModFile(Path path, IModLocator locator, Collection<?> infos) {
        return new TILModFileForge1_16_5(path,locator,infos);
    }
    
    public void initFor(ClassLoader loader, IModLocator locator) {
        Object core = CoreAPI.findInstance(loader);
        if(Objects.isNull(core))
            throw new RuntimeException("Failed to initialize multiversion mod loader! Cannot find CoreAPI on "+loader);
        findPaths(loader,locator,(MultiVersionLoaderAPI)CoreAPI.invoke(core,"getLoader"));
        loadMods(loader,locator,core);
    }
    
    public void loadCandidateInfos(IModLocator locator, Map<?,?> infoMap) {
        for(Entry<?,?> entry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = (MultiVersionModCandidate)entry.getKey();
            Path sourcePath = candidate.getFile().toPath();
            Collection<?> infos = (Collection<?>)entry.getValue();
            this.candidateMap.put(candidate,new TILModFileForge1_16_5(sourcePath,locator,infos));
        }
    }
    
    private void loadMods(ClassLoader loader, IModLocator locator, Object core) {
        Class<?>[] withLoader = new Class<?>[]{ClassLoader.class};
        CoreAPI.invoke(core,"loadCoreModInfo",withLoader,loader);
        CoreAPI.invoke(core,"instantiateCoreMods");
        CoreAPI.invoke(core,"writeModContainers",withLoader,loader);
        Object infoMap = CoreAPI.invoke(core,"getModInfo");
        loadCandidateInfos(locator,(Map<?,?>)infoMap);
    }
    
    @SuppressWarnings({"unchecked","resource"})
    @Override public List<IModFile> scanMods(IModLocator locator) {
        TILRef.logDebug("Scanning for mods in multiversion jars");
        List<IModFile> mods = new ArrayList<>();
        Object instance = CoreAPI.invoke(null,"getInstance");
        if(Objects.isNull(instance)) TILRef.logError("Failed to get CoreAPI instance :(");
        Object data = CoreAPI.invoke(instance,"getModData",new Class<?>[]{File.class},new File("."));
        for(TILModFileForge1_16_5 candidate : this.candidateMap.values()) {
            Map<IModFile,FileSystem> jars = (Map<IModFile,FileSystem>)ReflectionHelper.getFieldInstance(
                    locator,AbstractJarFileLocator.class,"modJars");
            if(Objects.isNull(jars)) {
                TILRef.logError("Failed to reflect modJars field! Things might break");
                continue;
            }
            jars.compute(candidate,(file,system) -> (FileSystem)ReflectionHelper.invokeMethod(
                    AbstractJarFileLocator.class,"createFileSystem",locator,new Class<?>[]{IModFile.class},file));
            candidate.populateMultiversionData((Map<String,MultiVersionModData>)data);
            mods.add(candidate);
        }
        return Collections.unmodifiableList(mods);
    }
}
