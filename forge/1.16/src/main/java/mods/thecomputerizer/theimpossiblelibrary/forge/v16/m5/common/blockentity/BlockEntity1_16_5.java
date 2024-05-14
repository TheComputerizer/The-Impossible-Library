package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag.CompoundTag1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.BlockPos1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.Objects;

public class BlockEntity1_16_5 extends BlockEntityAPI<TileEntity,TileEntityType<?>> {

    public BlockEntity1_16_5(TileEntity tile) {
        super(tile,tile.getType());
    }
    
    public BlockEntity1_16_5(TileEntityType<?> type) {
        super(null,type);
    }

    @Override
    public BlockPos1_16_5 getPos() {
        return Objects.nonNull(this.entity) ? new BlockPos1_16_5(this.entity.getBlockPos()) : null;
    }

    @Override
    public ResourceLocation1_16_5 getRegistryName() {
        return Objects.nonNull(this.entity) ? new ResourceLocation1_16_5(this.type.getRegistryName()) : null;
    }

    @Override
    public World1_16_5 getWorld() {
        return Objects.nonNull(this.entity) ? new World1_16_5(this.entity.getLevel()) : null;
    }
    
    @Override public CompoundTag1_16_5 readTagFrom() {
        return new CompoundTag1_16_5(Objects.nonNull(this.entity) ? this.entity.serializeNBT() : new CompoundNBT());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        this.type.setRegistryName(((ResourceLocation1_16_5)registryName).getInstance());
    }
    
    @Override public void writeTagTo(CompoundTagAPI tag) {
        if(Objects.nonNull(this.entity)) this.entity.save(((CompoundTag1_16_5)tag).getTag());
    }
}