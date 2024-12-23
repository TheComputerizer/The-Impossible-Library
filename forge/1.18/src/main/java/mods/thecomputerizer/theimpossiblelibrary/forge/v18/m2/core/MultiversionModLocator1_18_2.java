package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core;

import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core.loader.TILModFileForge1_18_2;
import net.minecraftforge.fml.loading.ClasspathLocatorUtils;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.jar.Manifest;

@IndirectCallers
public class MultiversionModLocator1_18_2 implements TILForgeModLocator {
    
    private static final String MANIFEST = "META-INF/MANIFEST.MF";
    
    private final Map<MultiVersionModCandidate,TILModFileForge1_18_2> candidateMap = new HashMap<>();
    
    public MultiversionModLocator1_18_2() {
        TILRef.logInfo("Loading plugin loaded with {}",getClass().getClassLoader());
    }
    
    void checkPath(MultiVersionLoaderAPI loader, Path path, Predicate<SecureJar> filter) {
        if(Files.isDirectory(path)) return;
        String fileName = path.getFileName().toString();
        TILRef.logInfo("Checking if file {} is the loader",fileName);
        if(Objects.isNull(MultiVersionModCandidate.loaderFile) && TILDev.isLoader(fileName)) {
            TILRef.logInfo("File is the loader");
            MultiVersionModCandidate.loaderFile = path.toFile();
        }
        SecureJar sj = SecureJar.from(Manifest::new,jar -> JarMetadata.from(jar,path));
        if(filter.test(sj)) {
            TILRef.logInfo("Found mod candidate at {}",path);
            loader.addPotentialModPath(path);
        }
    }
    
    void checkURL(MultiVersionLoaderAPI loader, URL url, Predicate<SecureJar> filter) {
        Path path = ClasspathLocatorUtils.findJarPathFor(MANIFEST,MANIFEST,url);
        checkPath(loader,path,filter);
    }
    
    void findFiles(MultiVersionLoaderAPI loader, Predicate<SecureJar> filter, File... files) {
        TILRef.logInfo("Loading {} mod files",files.length);
        for(File mod : files) {
            TILRef.logInfo("Loading mod file at path",mod.toPath());
            checkPath(loader, mod.toPath(),filter);
        }
    }
    
    void findPaths(ClassLoader classLoader, MultiVersionLoaderAPI loader) {
        Predicate<SecureJar> filter = jar -> {
            if(Objects.isNull(jar)) return false;
            Manifest manifest = jar.getManifest();
            if(Objects.isNull(manifest)) return false;
            return MultiVersionModFinder.hasMods(manifest.getMainAttributes());
        };
        findURLs(loader,classLoader,filter);
        findFiles(loader,filter,FileHelper.list(loader.findModRoot(),File::isFile));
    }
    
    void findURLs(MultiVersionLoaderAPI loader, ClassLoader classLoader, Predicate<SecureJar> filter) {
        try {
            final Enumeration<URL> manifests = classLoader.getResources(MANIFEST);
            while(manifests.hasMoreElements()) checkURL(loader,manifests.nextElement(),filter);
        } catch(IOException ex) {
            TILRef.logError("Failed to calculate URLs for paths with {} using {}",MANIFEST,classLoader,ex);
        }
    }
    
    @Override public IModFile createModFile(Path path, IModLocator locator, Collection<?> infos) {
        return new TILModFileForge1_18_2(jarFromPath(path),locator,infos);
    }
    
    @Override public void initFor(ClassLoader loader, IModLocator locator) {
        Object core = CoreAPI.findInstance(loader);
        if(Objects.isNull(core))
            throw new RuntimeException("Failed to initialize multiversion mod loader! Cannot find CoreAPI on "+loader);
        findPaths(loader,(MultiVersionLoaderAPI)CoreAPI.invoke(core,"getLoader"));
        loadMods(loader,locator,core);
    }
    
    SecureJar jarFromPath(Path path) {
        return SecureJar.from(Manifest::new,jar -> JarMetadata.from(jar,path));
    }
    
    public void loadCandidateInfos(IModLocator locator, Map<?,?> infoMap) {
        for(Entry<?,?> entry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = (MultiVersionModCandidate)entry.getKey();
            Path sourcePath = candidate.getFile().toPath();
            Collection<?> infos = (Collection<?>)entry.getValue();
            SecureJar jar = jarFromPath(sourcePath);
            this.candidateMap.put(candidate,new TILModFileForge1_18_2(jar,locator,infos));
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
    
    @SuppressWarnings("unchecked")
    @Override public List<IModFile> scanMods(IModLocator locator) {
        TILRef.logDebug("Scanning for mods in multiversion jars");
        List<IModFile> mods = new ArrayList<>();
        Object instance = CoreAPI.invoke(null,"getInstance");
        if(Objects.isNull(instance)) TILRef.logError("Failed to get CoreAPI instance :(");
        Object data = CoreAPI.invoke(instance,"getModData",new Class<?>[]{File.class},new File("."));
        for(TILModFileForge1_18_2 candidate : this.candidateMap.values()) {
            candidate.populateMultiversionData((Map<String,MultiVersionModData>)data);
            mods.add(candidate);
        }
        return Collections.unmodifiableList(mods);
    }
}
