package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionCoreMod;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionMod;

import javax.annotation.Nullable;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.Attributes;
import java.util.Map.Entry;

public abstract class MultiVersionLoaderAPI {

    protected final CoreAPI parent;
    protected final Set<Path> potentialModPaths;
    protected Collection<MultiVersionModCandidate> candidates;

    protected MultiVersionLoaderAPI(CoreAPI parent) {
        this.parent = parent;
        this.potentialModPaths = new LinkedHashSet<>();
    }

    public void addPotentialModPath(Path path) {
        this.potentialModPaths.add(path);
    }
    
    protected abstract File findCoreModRoot();
    public abstract File findModRoot();
    protected abstract List<File> gatherCandidateModFiles(File root);
    protected abstract @Nullable Attributes getFileAttributes(File file);

    private boolean isValidContext(MultiVersionCoreMod mod) {
        return isValidSide(mod.client(),mod.server()) &&
                isValidLoader(mod.fabric(),mod.forge(),mod.legacy(),mod.neoforge()) &&
                isValidVersion(mod.version12(),mod.version16(),mod.version18(),mod.version19(),mod.version20(),mod.version21());
    }

    private boolean isValidContext(MultiVersionMod mod) {
        return isValidSide(mod.client(),mod.server()) &&
                isValidLoader(mod.fabric(),mod.forge(),mod.legacy(),mod.neoforge()) &&
                isValidVersion(mod.version12(),mod.version16(),mod.version18(),mod.version19(),mod.version20(),mod.version21());
    }

    private boolean isValidLoader(boolean fabric, boolean forge, boolean legacy, boolean neoforge) {
        ModLoader loader = this.parent.getModLoader();
        if(loader.isFabric()) return fabric;
        if(loader.isLegacyForge()) return legacy;
        if(loader.isModernForge()) return forge;
        if(loader.isNeoForge()) return neoforge;
        return false;
    }

    private boolean isValidSide(boolean client, boolean server) {
        Side side = this.parent.getSide();
        if(side.isClient()) return client;
        if(side.isServer()) return server;
        return false;
    }

    private boolean isValidVersion(boolean v12, boolean v16, boolean v18, boolean v19, boolean v20, boolean v21) {
        GameVersion version = this.parent.getVersion();
        if(version.isV12()) return v12;
        if(version.isV16()) return v16;
        if(version.isV18()) return v18;
        if(version.isV19()) return v19;
        if(version.isV20()) return v20;
        if(version.isV21()) return v21;
        return false;
    }

    public void loadCoreMods(Map<MultiVersionModCandidate,Collection<MultiVersionCoreModInfo>> infoMap,
            ClassLoader loader, boolean loadSources) {
        File root = findCoreModRoot();
        TILRef.logInfo("Finding multiversion coremods from root `{}`", root);
        Map<MultiVersionModCandidate,Collection<Class<? extends CoreEntryPoint>>> classes = new HashMap<>();
        this.candidates = MultiVersionModFinder.discover(this,root,true);
        for(MultiVersionModCandidate candidate : this.candidates)
            candidate.findCoreClasses(classes,candidate,loader,loadSources);
        TILRef.logInfo("{} coremods will attempt to be loaded",classes.size());
        for(Entry<MultiVersionModCandidate,Collection<Class<? extends CoreEntryPoint>>> entry : classes.entrySet()) {
            MultiVersionModCandidate candidate = entry.getKey();
            if(!entry.getValue().isEmpty()) infoMap.put(candidate,new ArrayList<>());
            for(Class<? extends CoreEntryPoint> clazz : entry.getValue()) {
                MultiVersionCoreModInfo info = loadCoreMod(clazz);
                if(Objects.nonNull(info)) {
                    infoMap.get(candidate).add(info);
                    TILDev.logInfo("Successfully loaded coremod `{}` using class `{}`",info.getName(),info.getEntryClass());
                }
            }
        }
    }

    /**
     * Assumes canBeLoaded has already passed for the input class by this point
     */
    private @Nullable MultiVersionCoreModInfo loadCoreMod(Class<? extends CoreEntryPoint> clazz) {
        return loadCoreMod(clazz,clazz.getAnnotation(MultiVersionCoreMod.class));
    }

    private @Nullable MultiVersionCoreModInfo loadCoreMod(Class<? extends CoreEntryPoint> clazz, MultiVersionCoreMod mod) {
        return isValidContext(mod) ? MultiVersionCoreModInfo.get(clazz,mod) : null;
    }

    public void loadMods(Map<MultiVersionModCandidate,Collection<MultiVersionModInfo>> infoMap, ClassLoader loader,
            boolean loadSources) {
        File root = findModRoot();
        TILDev.logInfo("Finding multiversion mods from root `{}`",root);
        Map<MultiVersionModCandidate,Collection<Class<? extends CommonEntryPoint>>> classes = new HashMap<>();
        this.candidates = MultiVersionModFinder.discover(this,root,false);
        for(MultiVersionModCandidate candidate : this.candidates)
            candidate.findModClasses(classes,candidate,loader,loadSources);
        TILRef.logDebug("{} mods will attempt to be preloaded",classes.size());
        for(Entry<MultiVersionModCandidate,Collection<Class<? extends CommonEntryPoint>>> entry : classes.entrySet()) {
            MultiVersionModCandidate candidate = entry.getKey();
            if(!entry.getValue().isEmpty()) infoMap.put(candidate,new ArrayList<>());
            for(Class<? extends CommonEntryPoint> clazz : entry.getValue()) {
                MultiVersionModInfo info = loadMod(loader,candidate,clazz);
                if(Objects.nonNull(info)) {
                    infoMap.get(candidate).add(info);
                    TILDev.logInfo("Successfully preloaded mod `{}` using class `{}`",info.getName(),info.getEntryClass());
                }
            }
        }
    }

    /**
     * Assumes canBeLoaded has already passed for the input class by this point
     */
    private @Nullable MultiVersionModInfo loadMod(
            ClassLoader classLoader, MultiVersionModCandidate candidate, Class<? extends CommonEntryPoint> clazz) {
        return loadMod(classLoader,candidate,clazz,clazz.getAnnotation(MultiVersionMod.class));
    }

    private @Nullable MultiVersionModInfo loadMod(ClassLoader classLoader, MultiVersionModCandidate candidate, Class<? extends CommonEntryPoint> clazz, MultiVersionMod mod) {
        return isValidContext(mod) ? loadModInfo(classLoader,candidate,MultiVersionModInfo.get(clazz,mod)) : null;
    }

    protected abstract MultiVersionModInfo loadModInfo(
            ClassLoader classLoader, MultiVersionModCandidate candidate, MultiVersionModInfo info);
}