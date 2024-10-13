package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core;

import cpw.mods.modlauncher.TransformingClassLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreEntryPointForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client.ClientForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.CommonForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core.asm.ModWriterForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.core.TILCore1_18_2;
import net.minecraftforge.fml.loading.FMLLoader;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

@IndirectCallers
public class TILCoreForge1_18_2 extends TILCore1_18_2 implements TILCoreForge {

    public static final Reference FORGE_REF = TILRef.instance(FMLLoader.getDist()::isClient,"");
    private final MultiVersionLoaderForge1_18_2 loader;

    public TILCoreForge1_18_2() {
        super(FORGE,FORGE_REF.isClient());
        this.loader = new MultiVersionLoaderForge1_18_2(this);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCoreForge.class);
        ClassHelper.addSource(sources,TILCoreForge1_18_2.class);
    }
    
    @Override public boolean addURLToClassLoader(ClassLoader loader, URL url) {
        if(loader instanceof URLClassLoader) return ClassHelper.loadURL((URLClassLoader)loader,url);
        if(loader instanceof TransformingClassLoader) {
            Field field = ReflectionHelper.getField(TransformingClassLoader.class,"delegatedClassLoader");
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
        return new TILCoreEntryPointForge();
    }

    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterForge1_18_2(this, info);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_18_2() : new CommonForge1_18_2());
    }

    @Override public void injectWrittenMod(Class<?> containerClass, String modid) {}
    
    @Override public String mapClassName(String unmapped) {
        return unmapped;
    }
    
    @Override public String mapFieldName(String unmappedClass, String unmappedField, String desc) {
        return unmappedField;
    }
    
    @Override public String mapMethodName(String unmappedClass, String unmappedMethod, String desc) {
        return unmappedMethod;
    }
    
    @Override protected boolean modConstructed(String modid, Class<?> clazz) {
        TILRef.logInfo("Successfully constructed mod class for {} as {}",modid,clazz);
        return true;
    }
}