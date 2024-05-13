package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity.BlockEntity1_12_2;

import javax.annotation.Nullable;
import java.util.Objects;

public class BlockEntityBuilder1_12_2 extends BlockEntityBuilderAPI {
    
    public BlockEntityBuilder1_12_2(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    @Override public BlockEntity1_12_2 build() {
        BlockEntity1_12_2 entity = new BlockEntity1_12_2(
                Objects.nonNull(this.onTick) ? TILTickableBlockEntity1_12_2.class : TILBasicBlockEntity1_12_2.class);
        entity.setCreator(clazz -> new BlockEntity1_12_2(Objects.nonNull(this.onTick) ?
                        new TILTickableBlockEntity1_12_2(this.onTick) : new TILBasicBlockEntity1_12_2()));
        entity.setRegistryName(this.registryName);
        return entity;
    }
}