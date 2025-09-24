package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.tag.CompoundTag1_16_5;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BlockEntity1_16_5 extends BlockEntityAPI<BlockEntity,BlockEntityType<?>> {
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_16_5 entity(Object tile) {
        return new BlockEntity1_16_5(tile,((BlockEntity)tile).getType());
    }
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_16_5 get(Object obj) {
        return obj instanceof BlockEntity ? entity(obj) : type(obj);
    }
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_16_5 type(Object type) {
        return new BlockEntity1_16_5(null,type);
    }

    BlockEntity1_16_5(@Nullable Object tile, Object type) {
        super(tile,type);
    }
    
    @Override public BlockPosAPI<?> getPos() {
        return Objects.nonNull(this.entity) ? WrapperHelper.wrapPosition(this.entity.getBlockPos()) : null;
    }
    
    @Override public WorldAPI<?> getWorld() {
        return Objects.nonNull(this.entity) ? WrapperHelper.wrapWorld(this.entity.getLevel()) : null;
    }
    
    @Override public CompoundTagAPI<?> readTagFrom() {
        return new CompoundTag1_16_5(Objects.nonNull(this.entity) ? this.entity.getUpdateTag() : new CompoundTag());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        setForgeRegistryName(this,registryName);
    }
    
    @Override public void writeTagTo(CompoundTagAPI<?> tag) {
        if(Objects.nonNull(this.entity)) this.entity.save(tag.unwrap());
    }
}