package mods.thecomputerizer.theimpossiblelibrary.api.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class BlockBuilderAPI extends RegistryEntryBuilder<BlockAPI<?>> {
    
    protected MaterialAPI<?> material;
    
    protected BlockBuilderAPI(@Nullable BlockBuilderAPI parent) {
        if(Objects.nonNull(parent)) {
            this.material = parent.material;
        } else {
            this.material = null;
        }
    }
    
    @Override
    public BlockBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
}