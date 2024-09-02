package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

public class TILLoadingPluginFabric implements PreLaunchEntrypoint {
    
    static {
        TILRef.logInfo("Loaded TILLoadingPluginFabric class");
    }
    
    private static final String MANIFEST = "META-INF/MANIFEST.MF";
    
    public TILLoadingPluginFabric() {
        TILRef.logInfo("Instantiating Fabric loading plugin");
    }
    
    public void loadCandidateInfos(Map<MultiVersionModCandidate,Collection<MultiVersionModInfo>> infoMap) {
        for(Entry<MultiVersionModCandidate,Collection<MultiVersionModInfo>> fileEntry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = fileEntry.getKey();
            //this.candidateMap.put(candidate,new TILModFileForge1_16_5(candidate.getFile().toPath(), this, fileEntry.getValue()));
        }
    }
    
    private void loadURL(MultiVersionLoaderAPI loader, URL url, Predicate<Path> filter) {
        //Path path = LibraryFinder.findJarPathFor(MANIFEST,"manifest_jar",url);
        //loadURL(loader,url,path,filter,false);
    }
    
    private void loadURL(MultiVersionLoaderAPI loader, URL url, Path path, Predicate<Path> filter, boolean addToClassLoader) {
        if(Files.isDirectory(path)) return;
        String fileName = path.getFileName().toString();
        TILRef.logInfo("Checking if file {} is the loader",fileName);
        if(Objects.isNull(MultiVersionModCandidate.loaderFile) && TILDev.isLoader(fileName)) {
            TILRef.logInfo("File is the loader");
            MultiVersionModCandidate.loaderFile = path.toFile();
        }
        if(filter.test(path)) {
            TILRef.logInfo("Found classpath mod candidate: {}",path);
            //if(addToClassLoader) CoreAPI.getInstance().addURLToClassLoader(ClassLoader.getSystemClassLoader(),url);
            loader.addPotentialModPath(path);
        }
    }
    
    @Override public void onPreLaunch() {
        TILRef.logInfo("Running Fabric loading plugin");
        //MultiVersionLoaderAPI loader = core.getLoader();
        //try {
        //    final Enumeration<URL> manifests = ClassLoader.getSystemClassLoader().getResources(MANIFEST);
        //    Predicate<Path> filter = path -> findManifest(path).map(Manifest::getMainAttributes)
        //            .map(attributes -> MultiVersionModFinder.hasMods(attributes) ? attributes : null).isPresent();
        //    while(manifests.hasMoreElements()) loadURL(loader,manifests.nextElement(),filter);
        //    File[] mods = core.getLoader().findModRoot().listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        //    if(Objects.nonNull(mods))
        //        for(File mod : mods) loadURL(loader,mod.toURI().toURL(),mod.toPath(),filter,true);
        //
        //} catch(IOException ex) {
        //    TILRef.logError("Error finding multiversion mods to load",ex);
        //    throw new RuntimeException("Error finding multiversion mods to load",ex);
        //}
    }
}
