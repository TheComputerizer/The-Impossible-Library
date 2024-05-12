package mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;

import javax.annotation.Nullable;

public abstract class BlockEntityBuilderAPI extends RegistryEntryBuilder<BlockEntityAPI<?,?>> {
    
    protected BlockEntityBuilderAPI(@Nullable BlockEntityBuilderAPI parent) {
    }
}