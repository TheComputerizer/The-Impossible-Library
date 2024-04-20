package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Client1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.TILClientEntry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.Common1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.TILCommonEntry1_12_2;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ModDiscoverer;
import net.minecraftforge.fml.common.discovery.asm.ASMModParser;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.libraries.ModList;

import java.io.ByteArrayInputStream;
import java.io.File;

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
        return this.side.isClient() ? new TILClientEntry1_12_2() : null;
    }

    @Override
    public CommonEntryPoint getCommonVersionHandler() {
        return new TILCommonEntry1_12_2();
    }

    @Override
    public void initAPI() {
        TILRef.setAPI(getSide().isClient() ? new Client1_12_2() : new Common1_12_2());
    }

    @Override
    public void injectWrittenMod(Class<?> containerClass, String modid) {

    }

    @Override
    protected void modConstructed(String modid, Class<?> clazz) { //TODO Finish this
        ModDiscoverer discoverer = (ModDiscoverer) ReflectionHelper.getFieldInstance(Loader.instance(),Loader.class,"discoverer");
    }
}