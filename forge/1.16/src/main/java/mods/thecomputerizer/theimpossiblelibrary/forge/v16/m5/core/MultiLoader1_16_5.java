package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;

import java.io.File;

public class MultiLoader1_16_5 extends MultiLoaderAPI {

    protected MultiLoader1_16_5(CoreAPI parent, File root) {
        super(parent,root);
    }

    @Override
    protected MultiVersionModInfo loadModInfo(MultiVersionModInfo info) {
        return info;
    }
}
