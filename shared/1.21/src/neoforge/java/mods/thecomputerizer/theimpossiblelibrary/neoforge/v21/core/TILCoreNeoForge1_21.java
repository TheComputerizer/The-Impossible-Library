package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core;

import cpw.mods.modlauncher.Environment;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.ILaunchHandlerService;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.TILCoreEntryPointNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.TILCoreNeoforge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client.TILClientEntryPointNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.TILCommonEntryPointNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.asm.ModWriterNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.loader.MultiVersionLoaderNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.core.TILCore1_21;
import net.neoforged.fml.loading.targets.CommonLaunchHandler;

import java.util.Objects;
import java.util.Set;

import static cpw.mods.modlauncher.api.IEnvironment.Keys.LAUNCHTARGET;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.NEOFORGE;

public abstract class TILCoreNeoForge1_21 extends TILCore1_21 implements TILCoreNeoforge {

    public static final Reference NEOFORGE_REF = TILRef.instance(() -> isClient(Launcher.INSTANCE),"");
    
    static ILaunchHandlerService findLaunchHandler(Environment environment) {
        final String launchTarget = environment.getProperty(LAUNCHTARGET.get()).orElse("MISSING");
        return environment.findLaunchHandler(launchTarget).orElse(null);
    }
    
    static boolean isClient(Launcher launcher) {
        final CommonLaunchHandler launch = (CommonLaunchHandler)findLaunchHandler(launcher.environment());
        return Objects.isNull(launch) || launch.getDist().isClient();
    }
    
    private final MultiVersionLoaderNeoForge1_21 loader;

    public TILCoreNeoForge1_21(GameVersion version) {
        super(version,NEOFORGE,NEOFORGE_REF.isClient());
        this.loader = new MultiVersionLoaderNeoForge1_21(this);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCoreNeoforge.class);
        ClassHelper.addSource(sources,TILCoreNeoForge1_21.class);
    }
    
    @Override public CommonEntryPoint getClientVersionHandler() {
        return TILClientEntryPointNeoForge1_21.getInstance();
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPointNeoForge1_21.getInstance();
    }
    
    @Override public CoreEntryPoint getCoreVersionHandler() {
        return new TILCoreEntryPointNeoForge(this);
    }

    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterNeoForge1_21(this,info);
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