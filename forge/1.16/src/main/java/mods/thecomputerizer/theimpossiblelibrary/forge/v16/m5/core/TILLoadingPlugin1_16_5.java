package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader.TILModFile1_16_5;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.loading.LibraryFinder;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileLocator;
import net.minecraftforge.forgespi.locating.IModFile;

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

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.INSTANCE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

/**
 Targeted version of ClasspathLocator
 */
@EventBusSubscriber
public class TILLoadingPlugin1_16_5 extends AbstractJarFileLocator {
    
    static {
        new TILCore1_16_5();
    }
    
    private static final String MANIFEST = "META-INF/MANIFEST.MF";
    
    private final Map<MultiVersionModCandidate,TILModFile1_16_5> candidateMap = new HashMap<>();
    
    public TILLoadingPlugin1_16_5() {
        TILDev.logDebug("Loading plugin loaded with {}",getClass().getClassLoader());
        TILDev.logDebug("System classloader is {}",ClassLoader.getSystemClassLoader());
    }
    
    public void loadCandidateInfos(Map<MultiVersionModCandidate,Collection<MultiVersionModInfo>> infoMap) {
        for(Entry<MultiVersionModCandidate,Collection<MultiVersionModInfo>> fileEntry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = fileEntry.getKey();
            this.candidateMap.put(candidate,new TILModFile1_16_5(candidate.getFile().toPath(),this,fileEntry.getValue()));
        }
    }
    
    @Override public void initArguments(Map<String,?> arguments) {
        CoreAPI core = INSTANCE;
        MultiVersionLoaderAPI loader = core.getLoader();
        ((MultiVersionLoader1_16_5)loader).setLocator(this);
        try {
            final Enumeration<URL> manifests = ClassLoader.getSystemClassLoader().getResources(MANIFEST);
            Predicate<Path> filter = path -> findManifest(path).map(Manifest::getMainAttributes)
                    .map(attributes -> MultiVersionModFinder.hasMods(attributes) ? attributes : null).isPresent();
            while(manifests.hasMoreElements()) {
                URL url = manifests.nextElement();
                Path path = LibraryFinder.findJarPathFor(MANIFEST,"manifest_jar",url);
                if(Files.isDirectory(path)) continue;
                String fileName = path.getFileName().toString();
                TILRef.logDebug("Checking if file {} is the loader",fileName);
                if(Objects.isNull(MultiVersionModCandidate.loaderFile) && fileName.equals(MODID+"-"+VERSION+".jar")) {
                    TILRef.logDebug("File is the loader");
                    MultiVersionModCandidate.loaderFile = path.toFile();
                }
                if(filter.test(path)) {
                    TILRef.logDebug("Found classpath mod candidate: {}",path);
                    loader.addPotentialModPath(path);
                }
            }
        } catch(IOException ex) {
            TILRef.logError("Error finding multiversion mods to load",ex);
            throw new RuntimeException("Error finding multiversion mods to load",ex);
        }
        ClassLoader classLoader = getClass().getClassLoader();
        core.loadCoreModInfo(classLoader);
        core.instantiateCoreMods();
        core.writeModContainers(classLoader);
        loadCandidateInfos(core.getModInfo());
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override
    public List<IModFile> scanMods() {
        TILRef.logDebug("Scanning for mods in multiversion jars");
        List<IModFile> mods = new ArrayList<>();
        Map<String,MultiVersionModData> data = INSTANCE.getModData(new File("."));
        for(TILModFile1_16_5 candidate : this.candidateMap.values()) {
            this.modJars.compute(candidate,(file,system) -> createFileSystem(file));
            candidate.populateMultiversionData(data);
            mods.add(candidate);
        }
        return Collections.unmodifiableList(mods);
    }
}
