package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabricTest;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.TILCoreEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.TILCoreFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.client.ClientFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.CommonFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.core.asm.ModWriterFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.core.TILCore1_18_2;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.loader.impl.util.UrlUtil;

import java.net.URL;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static net.fabricmc.api.EnvType.CLIENT;

@IndirectCallers
public class TILCoreFabric1_18_2 extends TILCore1_18_2 implements TILCoreFabric {

    public static final Reference FABRIC_REF = TILRef.instance(() -> FabricLoader.getInstance().getEnvironmentType()==CLIENT,"");
    private final MultiVersionLoaderFabric1_18_2 loader;

    public TILCoreFabric1_18_2() {
        super(FABRIC,FABRIC_REF.isClient());
        this.loader = new MultiVersionLoaderFabric1_18_2(this);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCoreFabric.class);
        ClassHelper.addSource(sources,TILCoreFabric1_18_2.class);
    }
    
    @Override public boolean addURLToClassLoader(ClassLoader loader, URL url) {
        FabricLauncherBase.getLauncher().addToClassPath(UrlUtil.asPath(url));
        return true;
    }
    
    @Override public CommonEntryPoint getClientVersionHandler() {
        return null;
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return new TILCommonEntryPointFabric();
    }
    
    @Override public CoreEntryPoint getCoreVersionHandler() {
        return new TILCoreEntryPointFabric();
    }

    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterFabric1_18_2(this,info);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientFabric1_18_2() : new CommonFabric1_18_2());
    }

    @Override public void injectWrittenMod(Class<?> containerClass, String modid) {}
    
    MappingResolver mapper() {
        return FabricLoader.getInstance().getMappingResolver();
    }
    
    @Override public String mapClassName(String unmapped) {
        return mapper().mapClassName("intermediary",unmapped);
    }
    
    @Override public String mapFieldName(String unmappedClass, String unmappedField, String desc) {
        return mapper().mapFieldName("intermediary",unmappedClass,unmappedField,desc);
    }
    
    @Override public String mapMethodName(String unmappedClass, String unmappedMethod, String desc) {
        return mapper().mapMethodName("intermediary",unmappedClass,unmappedMethod,desc);
    }
    
    @Override protected boolean modConstructed(String modid, Class<?> clazz) {
        TILRef.logInfo("Successfully constructed mod class for {} as {}",modid,clazz);
        return true;
    }
    
    @Override protected Class<?> verifyGeneratedClass(Package pkg, String name, String entryType) {
        return TILCommonEntryPointFabricTest.class;
    }
}