package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;

import java.io.File;
import java.util.Collection;

public class MultiVersionLoader1_16_5 extends MultiVersionLoaderAPI {

    protected MultiVersionLoader1_16_5(CoreAPI parent, Collection<File> mods) {
        super(parent,mods);
    }

    @Override
    protected MultiVersionModInfo loadModInfo(MultiVersionModInfo info) {
        return info;
    }
}
