package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabricTest;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.TILCoreEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.TILCoreFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.TILCommonEntryPointFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.core.asm.ModWriterFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.core.loader.MultiVersionLoaderFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.core.TILCore1_19;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.loader.impl.util.UrlUtil;

import java.net.URL;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static net.fabricmc.api.EnvType.CLIENT;

public abstract class TILCoreFabric1_19 extends TILCore1_19 implements TILCoreFabric {

    public static final Reference FABRIC_REF = TILRef.instance(() -> FabricLoader.getInstance().getEnvironmentType()==CLIENT,"");
    private final MultiVersionLoaderFabric1_19 loader;

    public TILCoreFabric1_19(boolean two) {
        super(two,FABRIC,FABRIC_REF.isClient());
        this.loader = new MultiVersionLoaderFabric1_19(this);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCoreFabric.class);
        ClassHelper.addSource(sources,TILCoreFabric1_19.class);
    }
    
    @Override public boolean addURLToClassLoader(ClassLoader loader, URL url) {
        FabricLauncherBase.getLauncher().addToClassPath(UrlUtil.asPath(url));
        return true;
    }
    
    @Override public CommonEntryPoint getClientVersionHandler() {
        return null;
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPointFabric1_19.getInstance();
    }
    
    @Override public CoreEntryPoint getCoreVersionHandler() {
        return new TILCoreEntryPointFabric();
    }

    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterFabric1_19(this,info);
    }

    @Override public void injectWrittenMod(Class<?> containerClass, String modid) {}
    
    MappingResolver mapper() {
        return FabricLoader.getInstance().getMappingResolver();
    }
    
    @Override public String mapClassName(String unmapped) {
        return mapper().mapClassName("intermediary",unmapped.replace('/','.'));
    }
    
    @Override public String mapFieldName(String unmappedClass, String unmappedField, String desc) {
        unmappedClass = unmappedClass.replace('/','.');
        return mapper().mapFieldName("intermediary",unmappedClass,unmappedField,desc);
    }
    
    @Override public String mapMethodName(String unmappedClass, String unmappedMethod, String desc) {
        unmappedClass = unmappedClass.replace('/','.');
        return mapper().mapMethodName("intermediary",unmappedClass,unmappedMethod,desc);
    }
    
    @Override protected boolean modConstructed(String modid, Class<?> clazz) {
        TILRef.logInfo("Successfully constructed mod class for {} as {}",modid,clazz);
        return true;
    }
    
    @Override public String unmapClass(String className) {
        return mapper().unmapClassName("intermediary",className);
    }
    
    @Override protected Class<?> verifyGeneratedClass(Package pkg, String name, String entryType) {
        return TILCommonEntryPointFabricTest.class;
    }
}