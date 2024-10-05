package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;

import javax.annotation.Nullable;
import java.util.Objects;

public class BlockEntityBuilder1_12_2 extends BlockEntityBuilderAPI {
    
    public BlockEntityBuilder1_12_2(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    @Override public BlockEntityAPI<?,?> build() {
        BlockEntityAPI<?,?> entity = WrapperHelper.wrapBlockEntity(
                Objects.nonNull(this.onTick) ? TILTickableBlockEntity1_12_2.class : TILBasicBlockEntity1_12_2.class);
        entity.setCreator(clazz -> WrapperHelper.wrapBlockEntity(Objects.nonNull(this.onTick) ?
                        new TILTickableBlockEntity1_12_2(this.registryName,this.onTick) : new TILBasicBlockEntity1_12_2()));
        entity.setRegistryName(this.registryName);
        return entity;
    }
}