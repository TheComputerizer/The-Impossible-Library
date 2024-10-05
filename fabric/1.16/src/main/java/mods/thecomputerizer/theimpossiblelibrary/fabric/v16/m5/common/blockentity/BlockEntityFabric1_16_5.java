package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.blockentity.BlockEntity1_16_5;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Objects;

public class BlockEntityFabric1_16_5 extends BlockEntity1_16_5<BlockEntity,BlockEntityType<?>> {
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntityFabric1_16_5 entity(Object tile) {
        return new BlockEntityFabric1_16_5((BlockEntity)tile);
    }
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntityFabric1_16_5 get(Object obj) {
        return obj instanceof BlockEntity ? entity(obj) : type(obj);
    }
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntityFabric1_16_5 type(Object type) {
        return new BlockEntityFabric1_16_5((BlockEntityType<?>)type);
    }
    
    public BlockEntityFabric1_16_5(BlockEntity tile) {
        super(tile,tile.getType());
    }
    
    public BlockEntityFabric1_16_5(BlockEntityType<?> type) {
        super(null,type);
    }
    
    @Override public BlockPosAPI<?> getPos() {
        return Objects.nonNull(this.entity) ? WrapperHelper.wrapPosition(entity.getBlockPos()) : null;
    }
    
    @Override public WorldAPI<?> getWorld() {
        return Objects.nonNull(this.entity) ? WrapperHelper.wrapWorld(this.entity.getLevel()) : null;
    }
    
    @Override public CompoundTagAPI<?> readTagFrom() {
        CompoundTag tag = Objects.nonNull(this.entity) ? this.entity.getUpdateTag() : new CompoundTag();
        return TagHelper.getTagAPI().getWrapped(tag).asCompoundTag();
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        TILRef.logError("Tried to set the registry name of a block entity to {} in Fabric 1.16.5! "+
                        "This hasn't implemented it yet :(",registryName);
    }
    
    @Override public void writeTagTo(CompoundTagAPI<?> tag) {
        if(Objects.nonNull(this.entity)) this.entity.save(tag.unwrap());
    }
}