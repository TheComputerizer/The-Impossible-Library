package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.*;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Client1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.Common1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm.ModContainerWriter1_12_2;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.io.File;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V12;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public class TILCore1_12_2 extends CoreAPI {

    public static final Reference LEGACY_REF = TILRef.instance(FMLLaunchHandler.side()::isClient,"");

    public static ModContainer getFMLModContainer(String modid) {
        return InjectedModCandidate1_12_2.findModContainer(modid);
    }

    public static File getModSource(String modid) {
        return InjectedModCandidate1_12_2.findSource(modid);
    }

    private final MultiVersionLoader1_12_2 loader;

    public TILCore1_12_2() {
        super(V12,LEGACY,LEGACY_REF.isClient() ? DEDICATED_CLIENT : DEDICATED_SERVER);
        this.loader = new MultiVersionLoader1_12_2(this);
    }

    @Override
    public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }

    @Override
    public CommonEntryPoint getClientVersionHandler() {
        return null;
    }

    @Override
    public CommonEntryPoint getCommonVersionHandler() {
        return null;
    }

    @Override
    public void initAPI() {
        TILRef.setAPI(getSide().isClient() ? new Client1_12_2() : new Common1_12_2());
    }

    @Override
    public void injectWrittenMod(Class<?> containerClass, String modid) {

    }

    @Override
    protected boolean modConstructed(String modid, Class<?> clazz) {
        TILRef.logInfo("Attempting to inject `{}` for `{}` into the ASMDataTable",clazz,modid);
        ASMDataTable table = ModContainerWriter1_12_2.findASMTable(Loader.instance());
        if(Objects.nonNull(table)) {
            for(ModContainer container : Loader.instance().getActiveModList()) {
                if(container.getModId().equals(modid)) {
                    while(container instanceof InjectedModContainer) container = ((InjectedModContainer)container).wrappedContainer;
                    TILDev.logDebug("Found container `{}` for injecting",container.getClass());
                    return InjectedModCandidate1_12_2.injectIntoTable(container,clazz.getPackage().getName(),table);
                }
            }
            TILRef.logFatal("Unable to find ModContainer instance to inject! The game will likely crash very soon.");
        } else TILRef.logFatal("ASMDataTable instance was not found! The game will likely crash very soon.");
        return false;
    }
}