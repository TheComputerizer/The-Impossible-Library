package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag.CompoundTag1_12_2;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Objects;

public class BlockEntity1_12_2 extends BlockEntityAPI<TileEntity,Class<? extends TileEntity>> {
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_12_2 entity(Object tile) {
        return new BlockEntity1_12_2((TileEntity)tile);
    }
    
    /**
     * Assumes the input object will never be null
     */
    public static BlockEntity1_12_2 get(Object obj) {
        return obj instanceof TileEntity ? entity(obj) : type(obj);
    }
    
    /**
     * Assumes the input object will never be null
     */
    @SuppressWarnings("unchecked")
    public static BlockEntity1_12_2 type(Object type) {
        return new BlockEntity1_12_2((Class<? extends TileEntity>)type);
    }
    
    private ResourceLocation registryName;
    
    private BlockEntity1_12_2(TileEntity tile) {
        super(tile,tile.getClass());
        this.registryName = TileEntity.getKey(this.wrapped);
    }
    
    private BlockEntity1_12_2(Class<? extends TileEntity> tileClass) {
        super(null,tileClass);
        this.registryName = TileEntity.getKey(this.wrapped);
    }

    @Override public RegistryAPI<?> getRegistry() {
        return RegistryHelper.getBlockEntityRegistry();
    }

    @Override public ResourceLocationAPI<?> getRegistryName() {
        return Objects.nonNull(this.registryName) ? WrapperHelper.wrapResourceLocation(this.registryName) : null;
    }
    
    @Override public BlockPosAPI<?> getPos() {
        return PosHelper.getPos(this.entity.getPos());
    }

    @Override public WorldAPI<World> getWorld() {
        return WrapperHelper.wrapWorld(this.entity.getWorld());
    }
    
    @Override public CompoundTagAPI<?> readTagFrom() {
        return new CompoundTag1_12_2(Objects.nonNull(this.entity) ? this.entity.serializeNBT() : new NBTTagCompound());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName.unwrap();
    }
    
    @Override public void writeTagTo(CompoundTagAPI<?> tag) {
        if(Objects.nonNull(this.entity)) this.entity.writeToNBT(tag.unwrap());
    }
}
