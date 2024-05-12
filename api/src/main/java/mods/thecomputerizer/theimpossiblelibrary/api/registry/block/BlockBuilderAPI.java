package mods.thecomputerizer.theimpossiblelibrary.api.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;

import javax.annotation.Nullable;

public abstract class BlockBuilderAPI extends RegistryEntryBuilder<BlockAPI<?>> {
    
    protected BlockBuilderAPI(@Nullable BlockBuilderAPI parent) {
    
    }
}