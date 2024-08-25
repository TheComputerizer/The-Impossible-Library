package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import cpw.mods.modlauncher.TransformingClassLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Client1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.Common1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.asm.ModWriter1_16_5;
import net.minecraftforge.fml.loading.FMLLoader;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16_5;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public class TILCore1_16_5 extends CoreAPI {

    public static final Reference FORGE_REF = TILRef.instance(FMLLoader.getDist()::isClient,"");
    private final MultiVersionLoader1_16_5 loader;

    public TILCore1_16_5() {
        super(V16_5, FORGE, FORGE_REF.isClient() ? DEDICATED_CLIENT : DEDICATED_SERVER);
        this.loader = new MultiVersionLoader1_16_5(this);
    }
    
    @Override public boolean addURLToClassLoader(ClassLoader loader, URL url) {
        if(loader instanceof URLClassLoader) return ClassHelper.loadURL((URLClassLoader)loader,url);
        if(loader instanceof TransformingClassLoader) {
            Field field = ReflectionHelper.getField(TransformingClassLoader.class, "delegatedClassLoader");
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

    @Override
    public CommonEntryPoint getClientVersionHandler() {
        return null;
    }

    @Override
    public CommonEntryPoint getCommonVersionHandler() {
        return null;
    }

    @Override
    public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriter1_16_5(this,info);
    }
    
    @Override
    public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new Client1_16_5() : new Common1_16_5());
    }

    @Override
    public void injectWrittenMod(Class<?> containerClass, String modid) {

    }

    @Override
    protected boolean modConstructed(String modid, Class<?> clazz) {
        TILRef.logInfo("Successfully constructed mod class for {} as {}",modid,clazz);
        return true;
    }
}
