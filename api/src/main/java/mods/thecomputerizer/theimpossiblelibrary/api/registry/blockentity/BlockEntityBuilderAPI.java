package mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;

public abstract class BlockEntityBuilderAPI extends RegistryEntryBuilder<BlockEntityAPI<?,?>> {
    
    protected BlockEntityBuilderAPI(@Nullable BlockEntityBuilderAPI parent) {}
    
    @Override
    public BlockEntityBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
}