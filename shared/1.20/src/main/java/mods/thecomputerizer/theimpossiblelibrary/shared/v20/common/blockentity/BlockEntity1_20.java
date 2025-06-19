package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.CompoundTag1_20;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Objects;

public class BlockEntity1_20 extends BlockEntityAPI<BlockEntity,BlockEntityType<?>> {
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_20 entity(Object tile) {
        return new BlockEntity1_20((BlockEntity)tile);
    }
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_20 get(Object obj) {
        return obj instanceof BlockEntity ? entity(obj) : type(obj);
    }
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_20 type(Object type) {
        return new BlockEntity1_20((BlockEntityType<?>)type);
    }
    
    BlockEntity1_20(BlockEntity tile) {
        super(tile,tile.getType());
    }

    BlockEntity1_20(BlockEntityType<?> type) {
        super(null,type);
    }
    
    @Override public BlockPosAPI<?> getPos() {
        return Objects.nonNull(this.entity) ? WrapperHelper.wrapPosition(this.entity.getBlockPos()) : null;
    }
    
    @Override public WorldAPI<?> getWorld() {
        return Objects.nonNull(this.entity) ? WrapperHelper.wrapWorld(this.entity.getLevel()) : null;
    }
    
    @Override public CompoundTagAPI<?> readTagFrom() {
        return new CompoundTag1_20(Objects.nonNull(this.entity) ? this.entity.saveWithFullMetadata() : new CompoundTag());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
    
    @Override public void writeTagTo(CompoundTagAPI<?> tag) {
        if(Objects.nonNull(this.entity)) this.entity.load(tag.unwrap());
    }
}