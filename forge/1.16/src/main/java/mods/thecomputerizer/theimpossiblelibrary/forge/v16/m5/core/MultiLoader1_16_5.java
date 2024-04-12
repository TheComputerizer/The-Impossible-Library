package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.MultiLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.MultiVersionModInfo;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public class MultiLoader1_16_5 extends MultiLoaderAPI {

    protected MultiLoader1_16_5(boolean client) {
        super(V16,FORGE,client);
    }

    @Override
    protected void load(MultiVersionModInfo info) {

    }
}
