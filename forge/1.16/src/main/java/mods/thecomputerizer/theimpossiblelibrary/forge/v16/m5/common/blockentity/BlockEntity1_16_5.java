package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.BlockPos1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BlockEntity1_16_5 extends BlockEntityAPI<TileEntity,TileEntityType<?>> {

    public BlockEntity1_16_5(TileEntity tile) {
        super(tile,tile.getType());
    }
    
    public BlockEntity1_16_5(TileEntityType<?> type) {
        super(null,type);
    }

    @Override
    public BlockPosAPI<?> getPos() {
        return new BlockPos1_16_5(this.entity.getBlockPos());
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_16_5(this.type.getRegistryName());
    }

    @Override
    public WorldAPI<?> getWorld() {
        return new World1_16_5(this.entity.getLevel());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        this.type.setRegistryName(((ResourceLocation1_16_5)registryName).getInstance());
    }
}