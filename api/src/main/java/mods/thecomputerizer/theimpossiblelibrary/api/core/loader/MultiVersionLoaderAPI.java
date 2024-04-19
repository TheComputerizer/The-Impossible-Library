package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public abstract class MultiVersionLoaderAPI {

    protected final MultiVersionModFinder finder;
    protected final CoreAPI parent;

    protected MultiVersionLoaderAPI(CoreAPI parent, Collection<File> mods) {
        this.finder = new MultiVersionModFinder(mods);
        this.parent = parent;
    }

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
        switch(this.parent.getModLoader()) {
            case FABRIC: return fabric;
            case FORGE: return forge;
            case LEGACY: return legacy;
            case NEOFORGE: return neoforge;
            default: return false;
        }
    }

    private boolean isValidSide(boolean client, boolean server) {
        return this.parent.getSide().isClient()==client || this.parent.getSide().isServer()==server;
    }

    private boolean isValidVersion(boolean v12, boolean v16, boolean v18, boolean v19, boolean v20, boolean v21) {
        switch(this.parent.getVersion()) {
            case V12: return v12;
            case V16: return v16;
            case V18: return v18;
            case V19: return v19;
            case V20: return v20;
            case V21: return v21;
            default: return false;
        }
    }

    public void loadCoreMods(Set<MultiVersionCoreModInfo> infoSet, Consumer<URL> sourceConsumer) {
        TILRef.logInfo("Finding multiversion coremods");
        Set<Class<? extends CoreEntryPoint>> classes = new HashSet<>();
        for(MultiVersionModCandidate candidate : this.finder.getCoreCandidates())
            candidate.findCoreClasses(classes,sourceConsumer);
        TILRef.logInfo("{} coremods will attempt to be loaded",classes.size());
        for(Class<? extends CoreEntryPoint> clazz : classes) {
            MultiVersionCoreModInfo info = loadCoreMod(clazz);
            if(Objects.nonNull(info)) {
                infoSet.add(info);
                TILRef.logInfo("Successfully loaded coremod `{}` using class `{}`",info.getName(),info.getModClass());
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

    public void loadMods(Set<MultiVersionModInfo> infoSet, Consumer<URL> sourceConsumer) {
        TILRef.logInfo("Finding multiversion mods");
        Set<Class<? extends CommonEntryPoint>> classes = new HashSet<>();
        for(MultiVersionModCandidate candidate : this.finder.getModCandidates())
            candidate.findModClasses(classes,sourceConsumer);
        TILRef.logInfo("{} mods will attempt to be loaded",classes.size());
        for(Class<? extends CommonEntryPoint> clazz : classes) {
            MultiVersionModInfo info = loadMod(clazz);
            if(Objects.nonNull(info)) {
                infoSet.add(info);
                TILRef.logInfo("Successfully loaded mod `{}` using class `{}`",info.getName(),info.getModClass());
            }
        }
    }

    /**
     * Assumes canBeLoaded has already passed for the input class by this point
     */
    private @Nullable MultiVersionModInfo loadMod(Class<? extends CommonEntryPoint> clazz) {
        return loadMod(clazz,clazz.getAnnotation(MultiVersionMod.class));
    }

    private @Nullable MultiVersionModInfo loadMod(Class<? extends CommonEntryPoint> clazz, MultiVersionMod mod) {
        return isValidContext(mod) ? loadModInfo(MultiVersionModInfo.get(clazz,mod)) : null;
    }

    protected abstract MultiVersionModInfo loadModInfo(MultiVersionModInfo info);
}