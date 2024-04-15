package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.fml.common.ModMetadata;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MultiLoader1_12_2 extends MultiLoaderAPI {

    private final Map<MultiVersionModInfo,ModMetadata> metaMap;

    public MultiLoader1_12_2(CoreAPI parent, File root) {
        super(parent,root);
        this.metaMap = new HashMap<>();
    }

    @Override
    protected MultiVersionModInfo loadModInfo(MultiVersionModInfo info) {
        ModMetadata meta = new ModMetadata();
        meta.modId = info.getModid();
        meta.name = info.getName();
        meta.version = info.getVersion();
        this.metaMap.put(info,meta);
        return info;
    }
}