package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BlockEntity1_16_5 extends BlockEntityAPI<TileEntity,TileEntityType<?>> {

    public BlockEntity1_16_5(TileEntity tile) {
        super(tile,tile.getType());
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_16_5(this.type.getRegistryName());
    }
}