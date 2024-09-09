package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.blockentity.BlockEntity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.tag.CompoundTag1_16_5;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class BlockEntityForge1_16_5 extends BlockEntity1_16_5<TileEntity,TileEntityType<?>> {
    
    public BlockEntityForge1_16_5(TileEntity tile) {
        super(tile,tile.getType());
    }
    
    public BlockEntityForge1_16_5(TileEntityType<?> type) {
        super(null,type);
    }
    
    @Override public BlockPosAPI<?> getPos() {
        return Objects.nonNull(this.entity) ? PosHelper.getPos(this.entity.getBlockPos()) : null;
    }
    
    @Override public WorldAPI<?> getWorld() {
        return Objects.nonNull(this.entity) ? WrapperHelper.wrapWorld(this.entity.getLevel()) : null;
    }
    
    @Override public CompoundTagAPI<?> readTagFrom() {
        return new CompoundTag1_16_5(Objects.nonNull(this.entity) ? this.entity.serializeNBT() : new CompoundNBT());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
    
    @Override public void writeTagTo(CompoundTagAPI<?> tag) {
        if(Objects.nonNull(this.entity)) this.entity.save(tag.unwrap());
    }
}