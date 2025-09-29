package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.*;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.legacy.core.TILCoreEntryPointLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.core.TILCoreLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Client1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.TILClientEntryPoint1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.Common1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.TILCommonEntryPoint1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm.ModContainerWriter1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm.ModWriter1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.loader.MultiVersionLoader1_12_2;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V12_2;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public class TILCore1_12_2 extends CoreAPI implements TILCoreLegacy {

    public static final Reference LEGACY_REF = TILRef.instance(FMLLaunchHandler.side()::isClient,"");

    @IndirectCallers
    public static ModContainer getFMLModContainer(String modid) {
        return InjectedModCandidate1_12_2.findModContainer(modid);
    }
    
    @IndirectCallers
    public static File getModSource(String modid) {
        return InjectedModCandidate1_12_2.findSource(modid);
    }

    private final MultiVersionLoader1_12_2 loader;

    public TILCore1_12_2() {
        super(V12_2,LEGACY,LEGACY_REF.isClient() ? DEDICATED_CLIENT : DEDICATED_SERVER);
        this.loader = new MultiVersionLoader1_12_2(this);
    }
    
    @Override public boolean addURLToClassLoader(ClassLoader loader, URL url) {
        return ClassHelper.loadURL((URLClassLoader)loader,url);
    }
    
    @Override public CommonEntryPoint getClientVersionHandler() {
        return TILClientEntryPoint1_12_2.getInstance();
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPoint1_12_2.getInstance();
    }
    
    @Override public CoreEntryPoint getCoreVersionHandler() {
        return new TILCoreEntryPointLegacy();
    }

    @Override public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriter1_12_2(this,info);
    }

    @Override public void initAPI() {
        TILRef.setAPI(getSide().isClient() ? new Client1_12_2() : new Common1_12_2());
    }

    @Override public void injectWrittenMod(Class<?> containerClass, String modid) {}
    
    @Override public String mapClassName(String unmapped) {
        return FMLDeobfuscatingRemapper.INSTANCE.map(unmapped.replace('.','/'));
    }
    
    @Override public String mapFieldName(String unmappedClass, String unmappedField, String desc) {
        unmappedClass = unmappedClass.replace('.','/');
        desc = desc.replace('.','/');
        return FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(unmappedClass,unmappedField,desc);
    }
    
    @Override public String mapMethodName(String unmappedClass, String unmappedMethod, String desc) {
        unmappedClass = unmappedClass.replace('.','/');
        FMLDeobfuscatingRemapper remapper = FMLDeobfuscatingRemapper.INSTANCE;
        return remapper.mapMethodName(unmappedClass,unmappedMethod,desc.replace('.','/'));
    }
    
    @Override protected boolean modConstructed(String modid, Class<?> clazz) {
        ASMDataTable table = ModContainerWriter1_12_2.findASMTable(Loader.instance());
        if(Objects.nonNull(table)) {
            for(ModContainer container : Loader.instance().getActiveModList()) {
                if(container.getModId().equals(modid)) {
                    while(container instanceof InjectedModContainer) container = ((InjectedModContainer)container).wrappedContainer;
                    return InjectedModCandidate1_12_2.injectIntoTable(container,clazz.getPackage().getName(),table);
                }
            }
            this.logger.fatal("Unable to find ModContainer instance to inject! The game will likely crash very soon.");
        } else this.logger.fatal("ASMDataTable instance was not found! The game will likely crash very soon.");
        return false;
    }
    
    @Override public String unmapClass(String className) {
        return FMLDeobfuscatingRemapper.INSTANCE.unmap(className);
    }
}