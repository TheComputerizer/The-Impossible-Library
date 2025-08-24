package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core;

import cpw.mods.modlauncher.Environment;
import cpw.mods.modlauncher.api.ILaunchHandlerService;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.TILCoreEntryPointNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.TILCoreNeoforge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.asm.ModWriterNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader.MultiVersionLoaderNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.core.TILCore1_20;

import java.util.Set;
import java.util.function.Function;

import static cpw.mods.modlauncher.api.IEnvironment.Keys.LAUNCHTARGET;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_4;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_6;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.NEOFORGE;

public abstract class TILCoreNeoForge1_20 extends TILCore1_20 implements TILCoreNeoforge {
    
    protected static ILaunchHandlerService findLaunchHandler(Environment environment) {
        final String launchTarget = environment.getProperty(LAUNCHTARGET.get()).orElse("MISSING");
        return environment.findLaunchHandler(launchTarget).orElse(null);
    }
    
    private final MultiVersionLoaderNeoForge1_20 loader;
    
    public TILCoreNeoForge1_20(Reference neoforgeRef, boolean four,
            Function<CoreAPI,MultiVersionLoaderNeoForge1_20> loaderMaker) {
        super(four ? V20_4 : V20_6,NEOFORGE,neoforgeRef.isClient());
        this.loader = loaderMaker.apply(this);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCoreNeoforge.class);
        ClassHelper.addSource(sources,TILCoreNeoForge1_20.class);
    }
    
    @Override public CoreEntryPoint getCoreVersionHandler() {
        return new TILCoreEntryPointNeoForge(this);
    }
    
    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterNeoForge1_20(this,info);
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
    
    @Override public String unmapClass(String className) {
        return className;
    }
    
    public void writeModContainers(ClassLoader loader) {
        super.writeModContainers(loader);
    }
}