package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.fml.common.ModMetadata;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MultiVersionLoader1_12_2 extends MultiVersionLoaderAPI {

    private final Map<MultiVersionModInfo,ModMetadata> metaMap;

    public MultiVersionLoader1_12_2(CoreAPI parent, Collection<File> mods) {
        super(parent,mods);
        this.metaMap = new HashMap<>();
    }

    @Override
    protected MultiVersionModInfo loadModInfo(MultiVersionModInfo info) {
        ModMetadata meta = new ModMetadata();
        meta.modId = info.getModID();
        meta.name = info.getName();
        meta.version = info.getVersion();
        this.metaMap.put(info,meta);
        return info;
    }
}