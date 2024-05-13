package mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class BlockEntityBuilderAPI extends RegistryEntryBuilder<BlockEntityAPI<?,?>> {
    
    protected Consumer<BlockEntityAPI<?,?>> onTick;
    protected Supplier<Collection<BlockAPI<?>>> validBlocks;
    
    protected BlockEntityBuilderAPI(@Nullable BlockEntityBuilderAPI parent) {
        if(Objects.nonNull(parent)) {
            this.onTick = parent.onTick;
            this.validBlocks = parent.validBlocks;
        } else {
            this.onTick = null;
            this.validBlocks = null;
        }
    }
    
    @Override
    public BlockEntityBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
    
    public BlockEntityBuilderAPI setOnTick(Consumer<BlockEntityAPI<?,?>> consumer) {
        this.onTick = consumer;
        return this;
    }
    
    public BlockEntityBuilderAPI setValidBlocks(Supplier<Collection<BlockAPI<?>>> supplier) {
        this.validBlocks = supplier;
        return this;
    }
}