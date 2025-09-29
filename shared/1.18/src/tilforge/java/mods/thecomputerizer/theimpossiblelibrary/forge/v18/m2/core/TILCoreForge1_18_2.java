package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core;

import cpw.mods.modlauncher.Environment;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.ILaunchHandlerService;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreEntryPointForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.asm.ModWriterForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client.ClientForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.CommonForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core.loader.MultiVersionLoaderForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.core.TILCore1_18_2;
import net.minecraftforge.fml.loading.targets.CommonLaunchHandler;

import java.util.Objects;
import java.util.Set;

import static cpw.mods.modlauncher.api.IEnvironment.Keys.LAUNCHTARGET;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

@IndirectCallers
public class TILCoreForge1_18_2 extends TILCore1_18_2 implements TILCoreForge {

    public static final Reference FORGE_REF = TILRef.instance(() -> isClient(Launcher.INSTANCE),"");
    
    static ILaunchHandlerService findLaunchHandler(Environment environment) {
        final String launchTarget = environment.getProperty(LAUNCHTARGET.get()).orElse("MISSING");
        return environment.findLaunchHandler(launchTarget).orElse(null);
    }
    
    static boolean isClient(Launcher launcher) {
        final CommonLaunchHandler launch = (CommonLaunchHandler)findLaunchHandler(launcher.environment());
        return Objects.isNull(launch) || launch.getDist().isClient();
    }
    
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
    
    @Override public String unmapClass(String className) {
        return className;
    }
    
    public void writeModContainers(ClassLoader loader) {
        super.writeModContainers(loader);
    }
}