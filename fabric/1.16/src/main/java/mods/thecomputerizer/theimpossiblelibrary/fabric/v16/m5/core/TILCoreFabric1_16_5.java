package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.TILCoreFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.ClientFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.CommonFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.core.asm.ModWriterFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.core.TILCore1_16_5;
import net.fabricmc.loader.api.FabricLoader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static net.fabricmc.api.EnvType.CLIENT;

@IndirectCallers
public class TILCoreFabric1_16_5 extends TILCore1_16_5 implements TILCoreFabric {

    public static final Reference FABRIC_REF = TILRef.instance(() -> FabricLoader.getInstance().getEnvironmentType()==CLIENT,"");
    private final MultiVersionLoaderFabric1_16_5 loader;

    public TILCoreFabric1_16_5() {
        super(FABRIC,FABRIC_REF.isClient());
        this.loader = new MultiVersionLoaderFabric1_16_5(this);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCoreFabric.class);
        ClassHelper.addSource(sources,TILCoreFabric1_16_5.class);
    }
    
    @Override public boolean addURLToClassLoader(ClassLoader loader, URL url) {
        if(loader instanceof URLClassLoader) return ClassHelper.loadURL((URLClassLoader)loader,url);
        TILRef.logError("Loader doesn't seem to be URLClassLoader {} (url = {})",loader,url);
        return false;
    }
    
    @Override public CommonEntryPoint getClientVersionHandler() {
        return null;
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return null;
    }

    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterFabric1_16_5(this,info);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientFabric1_16_5() : new CommonFabric1_16_5());
    }

    @Override public void injectWrittenMod(Class<?> containerClass, String modid) {}
    
    @Override protected boolean modConstructed(String modid, Class<?> clazz) {
        TILRef.logInfo("Successfully constructed mod class for {} as {}",modid,clazz);
        return true;
    }
}
