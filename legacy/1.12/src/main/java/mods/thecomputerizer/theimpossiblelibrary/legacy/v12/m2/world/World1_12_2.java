package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.structure.StructureRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

import static net.minecraft.world.EnumSkyBlock.BLOCK;
import static net.minecraft.world.EnumSkyBlock.SKY;

public class World1_12_2 extends WorldAPI<World> {

    public World1_12_2(World world) {
        super(world);
    }

    @Override public boolean canSnowAt(BlockPosAPI<?> pos) {
        return this.wrapped.canSnowAt(pos.unwrap(),false);
    }

    @Override public BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos) {
        return WrapperHelper.wrapBiome(this.wrapped.getBiome(pos.unwrap()));
    }

    @Override public Collection<BlockEntityAPI<?,?>> getBlockEntitiesInBox(Box box) {
        List<BlockEntityAPI<?,?>> entities = new ArrayList<>();
        if(this.wrapped instanceof World) {
            synchronized(this.wrapped) {
                for(TileEntity tile : this.wrapped.loadedTileEntityList) {
                    BlockPos pos = tile.getPos();
                    if(box.isInside(pos.getX(),pos.getY(),pos.getZ()))
                        entities.add(WrapperHelper.wrapBlockEntity(tile));
                }
            }
        }
        return entities;
    }

    @Override public @Nullable BlockEntityAPI<?,?> getBlockEntityAt(BlockPosAPI<?> pos) {
        TileEntity tile = this.wrapped.getTileEntity(pos.unwrap());
        return Objects.nonNull(tile) ? WrapperHelper.wrapBlockEntity(tile) : null;
    }

    @Override public int getDayNumber() {
        return (int)((double)getTimeTotal()/24000d);
    }

    @Override public int getDifficultyOrdinal() {
        if(this.wrapped.getWorldInfo().isHardcoreModeEnabled()) return 4;
        switch(this.wrapped.getDifficulty()) {
            case PEACEFUL: return 0;
            case EASY: return 1;
            case NORMAL: return 2;
            case HARD: return 3;
            default: return -1; //unreachable
        }
    }

    @Override public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(this,this.wrapped.provider.getDimensionType());
    }

    @Override public List<EntityAPI<?,?>> getEntitiesInBox(Box box) {
        return getEntitiesInBox(new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }

    public List<EntityAPI<?,?>> getEntitiesInBox(AxisAlignedBB box) {
        List<EntityAPI<?,?>> entities = new ArrayList<>();
        for(Entity entity : this.wrapped.getEntitiesWithinAABB(Entity.class,box))
            entities.add(WrapperHelper.wrapEntity(entity));
        return entities;
    }

    @Override public int getLightBlock(BlockPosAPI<?> pos) {
        return this.wrapped.getLightFor(BLOCK,pos.unwrap());
    }

    @Override public int getLightSky(BlockPosAPI<?> pos) {
        return this.wrapped.getLightFor(SKY,pos.unwrap());
    }

    @Override public int getLightTotal(BlockPosAPI<?> pos) {
        return this.wrapped.getLight(pos.unwrap());
    }

    @Override public List<LivingEntityAPI<?,?>> getLivingInBox(Box box) {
        return getLivingInBox(new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }

    public List<LivingEntityAPI<?,?>> getLivingInBox(AxisAlignedBB box) {
        List<LivingEntityAPI<?,?>> entities = new ArrayList<>();
        for(EntityLivingBase entity : this.wrapped.getEntitiesWithinAABB(EntityLivingBase.class,box))
            entities.add(WrapperHelper.wrapLivingEntity(entity));
        return entities;
    }

    @Override public int getMoonPhase() {
        return this.wrapped.getMoonPhase();
    }

    @Override public BlockStateAPI<?> getStateAt(BlockPosAPI<?> pos) {
        return WrapperHelper.wrapState(this.wrapped.getBlockState(pos.unwrap()));
    }

    @Override public StructureAPI<?> getStructureAt(BlockPosAPI<?> pos) {
        if(this.wrapped instanceof WorldServer) {
            StructureRef ref = StructureRef.getStructureAt((WorldServer)this.wrapped,pos.unwrap());
            return Objects.nonNull(ref) ? WrapperHelper.wrapStructure(ref) : null;
        }
        return null;
    }

    @Override public long getTimeDay() {
        return getTimeTotal()%24000L;
    }

    @Override public long getTimeTotal() {
        return this.wrapped.getWorldTime();
    }

    @Override public boolean isClient() {
        return this.wrapped.isRemote;
    }

    @Override public boolean isDaytime() {
        return getTimeDay()<13000L;
    }

    @Override public boolean isRaining() {
        return this.wrapped.isRaining();
    }

    @Override public boolean isSkyVisible(BlockPosAPI<?> pos) {
        return this.wrapped.canBlockSeeSky(pos.unwrap());
    }

    @Override public boolean isStorming() {
        return this.wrapped.isThundering();
    }

    @Override public boolean isSunrise() {
        return getTimeDay()>=23000L;
    }

    @Override public boolean isSunset() {
        long time = getTimeDay();
        return time>=12000L && time<13000L;
    }
    
    @Override public void setState(BlockPosAPI<?> pos, BlockStateAPI<?> state) {
        this.wrapped.setBlockState(pos.unwrap(),state.unwrap());
    }
    
    @Override public void spawnEntity(EntityAPI<?,?> entity, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.wrapped.isRemote) {
            this.wrapped.spawnEntity(entity.unwrap());
            if(Objects.nonNull(onSpawn)) onSpawn.accept(entity);
        }
    }
    
    @Override public void spawnItem(ItemStackAPI<?> stack, Vector3d pos, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.wrapped.isRemote) {
            EntityItem item = new EntityItem(this.wrapped,pos.x,pos.y,pos.z,stack.unwrap());
            item.setDefaultPickupDelay();
            spawnEntity(WrapperHelper.wrapEntity(item),onSpawn);
        }
    }
    
    @Override public void spawnItem(ItemAPI<?> api, Vector3d pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn,
            @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.wrapped.isRemote) {
            ItemStackAPI<?> stack = WrapperHelper.wrapItemStack(new ItemStack((Item)api.unwrap()));
            if(Objects.nonNull(beforeSpawn)) beforeSpawn.accept(stack);
            spawnItem(stack,pos,onSpawn);
        }
    }
}