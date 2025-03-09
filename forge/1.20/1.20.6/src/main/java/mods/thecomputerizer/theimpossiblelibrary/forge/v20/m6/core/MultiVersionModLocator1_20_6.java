package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.core;

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
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.core.loader.TILModFileForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.core.loader.TILModFileForge1_20_6.TILLanguageProviderLoader;
import net.minecraftforge.fml.loading.ClasspathLocatorUtils;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;
import net.minecraftforge.forgespi.locating.IModLocator.ModFileOrException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.jar.Manifest;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

@SuppressWarnings({"FieldCanBeLocal","unused"}) @IndirectCallers
public class MultiVersionModLocator1_20_6 implements TILForgeModLocator {
    
    private static final String MANIFEST = "META-INF/MANIFEST.MF";
    
    private final CoreAPI core;
    private final Map<MultiVersionModCandidate,TILModFileForge1_20_6> candidateMap = new HashMap<>();
    
    public MultiVersionModLocator1_20_6(CoreAPI core) {
        this.core = core;
        TILRef.logInfo("1.20.6 Forge Locator plugin loaded on {}",getClass().getClassLoader());
    }
    
    void checkPath(MultiVersionLoaderAPI loader, Path path, Predicate<SecureJar> filter) {
        String loaderName = loader.getName();
        if(Files.isDirectory(path)) return;
        String fileName = path.getFileName().toString();
        TILDev.logInfo("[{}]: Checking if file {} is the loader",loaderName,fileName);
        if(Objects.isNull(MultiVersionModCandidate.loaderFile) && TILDev.isLoader(fileName)) {
            TILDev.logInfo("[{}]: File is the loader",loaderName);
            MultiVersionModCandidate.loaderFile = path.toFile();
        }
        SecureJar sj = jarFromPath(path);
        if(filter.test(sj)) {
            TILRef.logInfo("[{}]: Found mod candidate at {}",loaderName,path);
            loader.addPotentialModPath(path);
        }
    }
    
    void checkURL(MultiVersionLoaderAPI loader, URL url, Predicate<SecureJar> filter) {
        TILRef.logDebug("[{}]: Checking URL {} for MANIFEST {}",loader.getName(),url,MANIFEST);
        Path path = ClasspathLocatorUtils.findJarPathFor(MANIFEST,MANIFEST,url);
        checkPath(loader,path,filter);
    }
    
    void findFiles(MultiVersionLoaderAPI loader, Predicate<SecureJar> filter, File... files) {
        TILRef.logInfo("[{}]: Loading {} mod files",loader.getName(),files.length);
        for(File mod : files) {
            TILRef.logInfo("[{}]: Loading mod file at path",loader.getName(),mod.toPath());
            checkPath(loader,mod.toPath(),filter);
        }
    }
    
    void findPaths(ClassLoader classLoader, MultiVersionLoaderAPI loader) {
        Predicate<SecureJar> filter = jar -> {
            if(Objects.isNull(jar)) return false;
            Manifest manifest = jar.moduleDataProvider().getManifest();
            if(Objects.isNull(manifest)) return false;
            return MultiVersionModFinder.hasMods(manifest.getMainAttributes());
        };
        findURLs(loader,classLoader,filter);
        findFiles(loader,filter,FileHelper.list(loader.findModRoot(),File::isFile));
    }
    
    void findURLs(MultiVersionLoaderAPI loader, ClassLoader classLoader, Predicate<SecureJar> filter) {
        try {
            final Enumeration<URL> manifests = ClassLoader.getSystemClassLoader().getResources(MANIFEST);
            while(manifests.hasMoreElements()) checkURL(loader,manifests.nextElement(),filter);
        } catch(IOException ex) {
            TILRef.logError("[{}]: Failed to calculate URLs for paths with {} using {}",loader.getName(),MANIFEST,classLoader,ex);
        }
    }
    
    @Override public IModFile createModFile(Path path, IModLocator locator, Collection<?> infos) {
        return new TILModFileForge1_20_6(jarFromPath(path),locator,infos);
    }
    
    @Override public void initFor(ClassLoader loader, IModLocator locator) {
        Object core = CoreAPI.getInstance(loader);
        if(Objects.isNull(core))
            throw new RuntimeException("Failed to initialize multiversion mod loader! Cannot find CoreAPI on "+loader);
        findPaths(loader,(MultiVersionLoaderAPI)CoreAPI.invoke(core,"getLoader"));
        loadMods(loader,locator,core);
    }
    
    SecureJar jarFromPath(Path path) {
        return SecureJar.from(Manifest::new,jar -> JarMetadata.from(jar,path),
                              (root,p) -> true,path);
    }
    
    public void loadCandidateInfos(IModLocator locator, Map<?,?> infoMap) {
        for(Entry<?,?> entry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = (MultiVersionModCandidate)entry.getKey();
            Path sourcePath = candidate.getFile().toPath();
            Collection<?> infos = (Collection<?>)entry.getValue();
            SecureJar jar = jarFromPath(sourcePath);
            this.candidateMap.put(candidate,new TILModFileForge1_20_6(jar,locator,infos));
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
    @Override public List<ModFileOrException> scanMods(IModLocator locator) {
        TILRef.logDebug("Scanning for mods in multiversion jars (context = {})",Thread.currentThread().getContextClassLoader());
        List<IModFile> mods = new ArrayList<>();
        TILRef.logDebug("Getting CoreAPI instance");
        CoreAPI instance = CoreAPI.getInstance();
        if(Objects.isNull(instance)) TILRef.logError("Failed to get CoreAPI instance :(");
        Object data = CoreAPI.invoke(instance,"getModData",new Class<?>[]{File.class},new File("."));
        for(TILModFileForge1_20_6 candidate : this.candidateMap.values()) {
            candidate.populateMultiversionData((Map<String,MultiVersionModData>)data);
            if(MODID.equals(candidate.getModFileInfo().moduleName()))
                mods.add(new TILLanguageProviderLoader(candidate.getSecureJar(),candidate.getProvider()));
            mods.add(candidate);
        }
        List<ModFileOrException> annoyingList = new ArrayList<>();
        for(IModFile file : mods) annoyingList.add(new ModFileOrException(file,null));
        return Collections.unmodifiableList(annoyingList);
    }
}