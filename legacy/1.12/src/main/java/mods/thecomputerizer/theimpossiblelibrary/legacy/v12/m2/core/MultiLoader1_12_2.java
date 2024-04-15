package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.core.MultiLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.MultiVersionModInfo;
import net.minecraftforge.fml.common.ModMetadata;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V12;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;

public class MultiLoader1_12_2 extends MultiLoaderAPI {

    public MultiLoader1_12_2(Side side) {
        super(V12,LEGACY,side);
    }

    @Override
    protected void load(MultiVersionModInfo info) {
        ModMetadata meta = new ModMetadata();
        meta.modId = info.getModid();
        meta.name = info.getName();
        meta.version = info.getVersion();
        TILCore1_12_2.registerMultiversionMod(meta,info.getModClass(),info.isClient(),info.isServer());
    }
}