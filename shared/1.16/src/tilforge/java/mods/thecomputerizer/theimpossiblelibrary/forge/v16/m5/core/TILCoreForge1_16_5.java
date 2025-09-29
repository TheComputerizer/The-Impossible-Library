package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import cpw.mods.modlauncher.TransformingClassLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreEntryPointForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.asm.ModWriterForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.ClientForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.CommonForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader.MultiVersionLoaderForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.core.TILCore1_16_5;
import net.minecraftforge.fml.loading.FMLLoader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

@IndirectCallers
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
            URLClassLoader urlLoader = Hacks.getField(loader,"delegatedClassLoader");
            if(Objects.nonNull(urlLoader)) {
                if(ClassHelper.loadURL(urlLoader,url)) {
                    this.logger.debug("Successfully loaded URL to mod class loader {}",url);
                    return true;
                } else this.logger.error("Failed to load URL to mod class loader {}",url);
            } else this.logger.error("Failed to get delegatedClassLoader field as instance of URLClassLoader");
        }
        this.logger.error("Cannot add URL to unknown ClassLoader type {} (URL = {})",loader,url);
        return false;
    }
    
    @Override public CoreEntryPoint getCoreVersionHandler() {
        return new TILCoreEntryPointForge();
    }

    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterForge(this,info);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_16_5() : new CommonForge1_16_5());
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
    
    @Override public String unmapClass(String className) {
        return className;
    }
}