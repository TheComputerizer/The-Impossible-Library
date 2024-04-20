package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;

public class MultiVersionLoader1_16_5 extends MultiVersionLoaderAPI {

    protected MultiVersionLoader1_16_5(CoreAPI parent) {
        super(parent);
    }

    @Override
    protected File findCoreModRoot() {
        return null;
    }

    @Override
    protected File findModRoot() {
        return null;
    }

    @Override
    protected List<File> gatherCandidateModFiles(File root) {
        return Collections.emptyList();
    }

    @Override
    protected @Nullable Attributes getFileAttributes(File file) {
        return null;
    }

    @Override
    protected MultiVersionModInfo loadModInfo(ClassLoader classLoader, File root, MultiVersionModInfo info) {
        return info;
    }
}
