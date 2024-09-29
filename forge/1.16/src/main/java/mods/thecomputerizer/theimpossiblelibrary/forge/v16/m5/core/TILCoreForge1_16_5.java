package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import cpw.mods.modlauncher.TransformingClassLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.ClientForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.CommonForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.asm.ModWriterForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.core.TILCore1_16_5;
import net.minecraftforge.fml.loading.FMLLoader;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public class TILCoreForge1_16_5 extends TILCore1_16_5 implements TILCoreForge {

    public static final Reference FORGE_REF = TILRef.instance(FMLLoader.getDist()::isClient,"");
    private final MultiVersionLoaderForge1_16_5 loader;

    public TILCoreForge1_16_5() {
        super(FORGE,FORGE_REF.isClient());
        this.loader = new MultiVersionLoaderForge1_16_5(this);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCoreForge.class);
        ClassHelper.addSource(sources,TILCoreForge1_16_5.class);
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
    
    @Override public CommonEntryPoint getClientVersionHandler() {
        return null;
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return null;
    }
    
    @Override public CoreEntryPoint getCoreVersionHandler() {
        return new TILCoreEntryPointForge1_16_5();
    }

    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterForge1_16_5(this, info);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_16_5() : new CommonForge1_16_5());
    }

    @Override public void injectWrittenMod(Class<?> containerClass, String modid) {}
    
    @Override protected boolean modConstructed(String modid, Class<?> clazz) {
        TILRef.logInfo("Successfully constructed mod class for {} as {}",modid,clazz);
        return true;
    }
}
