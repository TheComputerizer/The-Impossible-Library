package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.CompoundTag1_21;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BlockEntity1_21 extends BlockEntityAPI<BlockEntity,BlockEntityType<?>> {
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_21 entity(Object tile) {
        return new BlockEntity1_21(tile,((BlockEntity)tile).getType());
    }
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_21 get(Object obj) {
        return obj instanceof BlockEntity ? entity(obj) : type(obj);
    }
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_21 type(Object type) {
        return new BlockEntity1_21(null,type);
    }
    
    BlockEntity1_21(@Nullable Object tile, Object type) {
        super(tile,type);
    }
    
    @Override public BlockPosAPI<?> getPos() {
        return Objects.nonNull(this.entity) ? WrapperHelper.wrapPosition(this.entity.getBlockPos()) : null;
    }
    
    @Override public WorldAPI<?> getWorld() {
        return Objects.nonNull(this.entity) ? WrapperHelper.wrapWorld(this.entity.getLevel()) : null;
    }
    
    @Override public CompoundTagAPI<?> readTagFrom() {
        if(Objects.isNull(this.entity) || Objects.isNull(this.entity.getLevel()))
            return new CompoundTag1_21(new CompoundTag());
        return new CompoundTag1_21(this.entity.saveCustomOnly(this.entity.getLevel().registryAccess()));
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
    
    @Override public void writeTagTo(CompoundTagAPI<?> tag) {
        if(Objects.isNull(this.entity) || Objects.isNull(this.entity.getLevel())) return;
        this.entity.loadCustomOnly(tag.unwrap(),this.entity.getLevel().registryAccess());
    }
}