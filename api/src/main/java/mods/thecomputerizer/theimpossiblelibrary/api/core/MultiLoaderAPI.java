package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class MultiLoaderAPI {

    protected final GameVersion version;
    protected final ModLoader loader;
    protected final Side side;

    protected MultiLoaderAPI(GameVersion version, ModLoader loader, Side side) {
        this.version = version;
        this.loader = loader;
        this.side = side;
    }

    public boolean canBeLoaded(@Nullable Class<?> clazz) {
        return Objects.nonNull(clazz) && CommonEntryPoint.class.isAssignableFrom(clazz) &&
                clazz.isAnnotationPresent(MultiversionMod.class);
    }

    private boolean isValidContext(MultiversionMod mod) {
        return isValidSide(mod) && isValidLoader(mod) && isValidVersion(mod);
    }

    private boolean isValidLoader(MultiversionMod mod) {
        switch(this.loader) {
            case FABRIC: return mod.fabric();
            case FORGE: return mod.forge();
            case LEGACY: return mod.legacy();
            case NEOFORGE: return mod.neoforge();
            default: return false;
        }
    }

    private boolean isValidSide(MultiversionMod mod) {
        return this.side.isClient()==mod.client() || this.side.isServer()==mod.server();
    }

    private boolean isValidVersion(MultiversionMod mod) {
        switch(this.version) {
            case V12: return mod.version12();
            case V16: return mod.version16();
            case V18: return mod.version18();
            case V19: return mod.version19();
            case V20: return mod.version20();
            case V21: return mod.version21();
            default: return false;
        }
    }

    /**
     * Assumes canBeLoaded has already passed for the input class by this point
     */
    public void load(Iterable<Class<?>> classes) {
        for(Class<?> clazz : classes) load(clazz);
    }

    /**
     * Assumes canBeLoaded has already passed for the input class by this point
     */
    @SuppressWarnings("unchecked")
    public void load(Class<?> clazz) {
        load((Class<? extends CommonEntryPoint>)clazz,clazz.getAnnotation(MultiversionMod.class));
    }

    protected void load(Class<? extends CommonEntryPoint> clazz, MultiversionMod mod) {
        if(isValidContext(mod)) load(MultiVersionModInfo.get(clazz,mod));
    }

    protected abstract void load(MultiVersionModInfo info);
}