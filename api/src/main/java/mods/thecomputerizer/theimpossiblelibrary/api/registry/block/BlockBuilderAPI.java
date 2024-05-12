package mods.thecomputerizer.theimpossiblelibrary.api.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;

public abstract class BlockBuilderAPI extends RegistryEntryBuilder<BlockAPI<?>> {
    
    protected BlockBuilderAPI(@Nullable BlockBuilderAPI parent) {}
    
    @Override
    public BlockBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
}