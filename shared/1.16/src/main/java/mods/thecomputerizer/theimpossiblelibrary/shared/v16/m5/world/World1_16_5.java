package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerWorld;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static net.minecraft.world.LightType.BLOCK;
import static net.minecraft.world.LightType.SKY;

public class World1_16_5 extends WorldAPI<IWorld> {
    
    public World1_16_5(Object world) {
        super((IWorld)world);
    }
    
    @Override public boolean canSnowAt(BlockPosAPI<?> api) {
        BlockPos pos = api.unwrap();
        return this.wrapped.getBiome(pos).shouldSnow(this.wrapped,pos);
    }
    
    @Override public BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos) {
        return WrapperHelper.wrapBiome(this.wrapped.getBiome(pos.unwrap()));
    }
    
    @Override public Collection<BlockEntityAPI<?,?>> getBlockEntitiesInBox(Box box) {
        List<BlockEntityAPI<?,?>> entities = new ArrayList<>();
        if(this.wrapped instanceof World) {
            synchronized(this.wrapped) {
                for(TileEntity tile : ((World)this.wrapped).blockEntityList) {
                    BlockPos pos = tile.getBlockPos();
                    if(box.isInside(pos.getX(),pos.getY(),pos.getZ()))
                        entities.add(WrapperHelper.wrapBlockEntity(tile));
                }
            }
        }
        return entities;
    }
    
    @Override public @Nullable BlockEntityAPI<?,?> getBlockEntityAt(BlockPosAPI<?> pos) {
        TileEntity tile = this.wrapped.getBlockEntity(pos.unwrap());
        return Objects.nonNull(tile) ? WrapperHelper.wrapBlockEntity(tile) : null;
    }

    @Override public int getDayNumber() {
        return (int)((double)getTimeTotal()/24000d);
    }
    
    @Override public int getDifficultyOrdinal() {
        if(this.wrapped.getLevelData().isHardcore()) return 4;
        switch(this.wrapped.getDifficulty()) {
            case PEACEFUL: return 0;
            case EASY: return 1;
            case NORMAL: return 2;
            case HARD: return 3;
            default: return -1; //unreachable
        }
    }
    
    @Override public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(this,this.wrapped.dimensionType());
    }
    
    @Override public List<EntityAPI<?,?>> getEntitiesInBox(Box box) {
        return getEntitiesInBox(new AxisAlignedBB(box.min.x, box.min.y, box.min.z, box.max.x, box.max.y, box.max.z));
    }
    
    private List<EntityAPI<?,?>> getEntitiesInBox(Object box) {
        List<EntityAPI<?,?>> entities = new ArrayList<>();
        for(Entity entity : this.wrapped.getEntitiesOfClass(Entity.class, (AxisAlignedBB)box))
            entities.add(WrapperHelper.wrapEntity(entity));
        return entities;
    }
    
    @Override public int getLightBlock(BlockPosAPI<?> pos) {
        return this.wrapped.getBrightness(BLOCK,pos.unwrap());
    }
    
    @Override public int getLightSky(BlockPosAPI<?> pos) {
        return this.wrapped.getBrightness(SKY,pos.unwrap());
    }
    
    @Override public int getLightTotal(BlockPosAPI<?> pos) {
        return this.wrapped.getLightEngine().getRawBrightness(pos.unwrap(),0);
    }
    
    @Override public List<LivingEntityAPI<?,?>> getLivingInBox(Box box) {
        return getLivingInBox(new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }
    
    private List<LivingEntityAPI<?,?>> getLivingInBox(Object box) {
        List<LivingEntityAPI<?,?>> entities = new ArrayList<>();
        for(LivingEntity entity : this.wrapped.getEntitiesOfClass(LivingEntity.class, (AxisAlignedBB)box))
            entities.add(WrapperHelper.wrapLivingEntity(entity));
        return entities;
    }
    
    @Override public int getMoonPhase() {
        return this.wrapped.getMoonPhase();
    }
    
    @Override public BlockStateAPI<?> getStateAt(BlockPosAPI<?> pos) {
        return WrapperHelper.wrapState(this.wrapped.getBlockState(pos.unwrap()));
    }
    
    @Override public StructureAPI<?> getStructureAt(BlockPosAPI<?> api) {
        if(this.wrapped instanceof ServerWorld) {
            StructureManager manager = ((ServerWorld)this.wrapped).structureFeatureManager();
            BlockPos pos = api.unwrap();
            for(Object structure : RegistryHelper.getStructureRegistry().getValues())
                if(manager.getStructureAt(pos,false,(Structure<?>)structure).isValid())
                    return WrapperHelper.wrapStructure(structure);
        }
        return null;
    }

    @Override public long getTimeDay() {
        return getTimeTotal()%24000L;
    }
    
    @Override public long getTimeTotal() {
        return this.wrapped.dayTime();
    }
    
    @Override public boolean isClient() {
        return this.wrapped.isClientSide();
    }
    
    @Override public boolean isDaytime() {
        return this.wrapped instanceof World && getTimeDay()<13000L;
    }
    
    @Override public boolean isNighttime() {
        return this.wrapped instanceof World && getTimeDay()>=13000L;
    }
    
    @Override public boolean isRaining() {
        return this.wrapped instanceof World && ((World)this.wrapped).isRaining();
    }
    
    @Override public boolean isSkyVisible(BlockPosAPI<?> pos) {
        return this.wrapped.canSeeSky(pos.unwrap());
    }
    
    @Override public boolean isStorming() {
        return this.wrapped instanceof World && ((World)this.wrapped).isThundering();
    }

    @Override public boolean isSunrise() {
        return getTimeDay()>=23000L;
    }

    @Override public boolean isSunset() {
        long time = getTimeDay();
        return time>=12000L && time<13000L;
    }
    
    @Override public void setState(BlockPosAPI<?> pos, BlockStateAPI<?> state) {
        this.wrapped.setBlock(pos.unwrap(),state.unwrap(),2);
    }
    
    @Override public void spawnEntity(EntityAPI<?,?> entity, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.wrapped.isClientSide()) {
            this.wrapped.addFreshEntity((Entity)entity.getEntity());
            if(Objects.nonNull(onSpawn)) onSpawn.accept(entity);
        }
    }
    
    @Override public void spawnItem(ItemStackAPI<?> stack, Vector3d pos, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(this.wrapped instanceof World && !this.wrapped.isClientSide()) {
            ItemEntity item = new ItemEntity((World)this.wrapped, pos.x, pos.y, pos.z, stack.unwrap());
            item.setDefaultPickUpDelay();
            spawnEntity(WrapperHelper.wrapEntity(item),onSpawn);
        }
    }
    
    @Override public void spawnItem(
            ItemAPI<?> api, Vector3d pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn,
            @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.wrapped.isClientSide()) {
            ItemStackAPI<?> stack = WrapperHelper.wrapItemStack(new ItemStack(api.unwrap()));
            if(Objects.nonNull(beforeSpawn)) beforeSpawn.accept(stack);
            spawnItem(stack,pos,onSpawn);
        }
    }
}
