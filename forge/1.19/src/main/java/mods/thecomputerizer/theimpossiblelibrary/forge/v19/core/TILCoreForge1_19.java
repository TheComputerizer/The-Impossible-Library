package mods.thecomputerizer.theimpossiblelibrary.forge.v19.core;

import cpw.mods.modlauncher.Environment;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.ILaunchHandlerService;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreEntryPointForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.core.asm.ModWriterForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.core.loader.MultiVersionLoaderForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.core.TILCore1_19;
import net.minecraftforge.fml.loading.targets.CommonLaunchHandler;

import java.util.Objects;
import java.util.Set;

import static cpw.mods.modlauncher.api.IEnvironment.Keys.LAUNCHTARGET;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public abstract class TILCoreForge1_19 extends TILCore1_19 implements TILCoreForge {

    public static final Reference FORGE_REF = TILRef.instance(() -> isClient(Launcher.INSTANCE),"");
    
    static ILaunchHandlerService findLaunchHandler(Environment environment) {
        final String launchTarget = environment.getProperty(LAUNCHTARGET.get()).orElse("MISSING");
        return environment.findLaunchHandler(launchTarget).orElse(null);
    }
    
    static boolean isClient(Launcher launcher) {
        final CommonLaunchHandler launch = (CommonLaunchHandler)findLaunchHandler(launcher.environment());
        return Objects.isNull(launch) || launch.getDist().isClient();
    }
    
    private final MultiVersionLoaderForge1_19 loader;

    public TILCoreForge1_19(boolean two) {
        super(two,FORGE,FORGE_REF.isClient());
        this.loader = new MultiVersionLoaderForge1_19(this);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCoreForge.class);
        ClassHelper.addSource(sources,TILCoreForge1_19.class);
    }
    
    @Override public CommonEntryPoint getClientVersionHandler() {
        return null;
    }
    
    @Override public CoreEntryPoint getCoreVersionHandler() {
        return new TILCoreEntryPointForge();
    }

    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterForge1_19(this,info);
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