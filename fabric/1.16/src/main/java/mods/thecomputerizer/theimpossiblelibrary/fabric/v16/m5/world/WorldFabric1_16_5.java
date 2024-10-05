package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
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
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.World1_16_5;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static net.minecraft.core.Registry.STRUCTURE_FEATURE;
import static net.minecraft.world.level.LightLayer.BLOCK;
import static net.minecraft.world.level.LightLayer.SKY;

public class WorldFabric1_16_5 extends World1_16_5<LevelAccessor> {
    
    public WorldFabric1_16_5(Object world) {
        super((LevelAccessor)world);
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
        if(this.wrapped instanceof Level) {
            synchronized(this.wrapped) {
                for(BlockEntity tile : ((Level)this.wrapped).blockEntityList) {
                    BlockPos pos = tile.getBlockPos();
                    if(box.isInside(pos.getX(),pos.getY(),pos.getZ())) 
                        entities.add(WrapperHelper.wrapBlockEntity(tile));
                }
            }
        }
        return entities;
    }
    
    @Override public @Nullable BlockEntityAPI<?,?> getBlockEntityAt(BlockPosAPI<?> pos) {
        BlockEntity tile = this.wrapped.getBlockEntity(pos.unwrap());
        return Objects.nonNull(tile) ? WrapperHelper.wrapBlockEntity(tile) : null;
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
        return getEntitiesInBox(new AABB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }
    
    private List<EntityAPI<?,?>> getEntitiesInBox(Object box) {
        List<EntityAPI<?,?>> entities = new ArrayList<>();
        for(Entity entity : this.wrapped.getEntitiesOfClass(Entity.class,(AABB)box))
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
        return getLivingInBox(new AABB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }
    
    private List<LivingEntityAPI<?,?>> getLivingInBox(Object box) {
        List<LivingEntityAPI<?,?>> entities = new ArrayList<>();
        for(LivingEntity entity : this.wrapped.getEntitiesOfClass(LivingEntity.class,(AABB)box))
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
        if(this.wrapped instanceof ServerLevel) {
            StructureFeatureManager manager = ((ServerLevel)this.wrapped).structureFeatureManager();
            BlockPos pos = api.unwrap();
            for(ResourceLocation key : STRUCTURE_FEATURE.keySet()) {
                StructureFeature<?> structure = STRUCTURE_FEATURE.get(key);
                if(manager.getStructureAt(pos,false,structure).isValid())
                    return WrapperHelper.wrapStructure(structure);
            }
        }
        return null;
    }
    
    @Override public long getTimeTotal() {
        return this.wrapped.dayTime();
    }
    
    @Override public boolean isClient() {
        return this.wrapped.isClientSide();
    }
    
    @Override public boolean isDaytime() {
        return this.wrapped instanceof Level && getTimeDay()<13000L;
    }
    
    @Override public boolean isNighttime() {
        return this.wrapped instanceof Level && getTimeDay()>=13000L;
    }
    
    @Override public boolean isRaining() {
        return this.wrapped instanceof Level && ((Level)this.wrapped).isRaining();
    }
    
    @Override public boolean isSkyVisible(BlockPosAPI<?> pos) {
        return this.wrapped.canSeeSky(pos.unwrap());
    }
    
    @Override public boolean isStorming() {
        return this.wrapped instanceof Level && ((Level)this.wrapped).isThundering();
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
        if(this.wrapped instanceof Level && !this.wrapped.isClientSide()) {
            ItemEntity item = new ItemEntity((Level)this.wrapped,pos.x,pos.y,pos.z,stack.unwrap());
            item.setDefaultPickUpDelay();
            spawnEntity(WrapperHelper.wrapEntity(item),onSpawn);
        }
    }
    
    @Override public void spawnItem(ItemAPI<?> api, Vector3d pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn,
            @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.wrapped.isClientSide()) {
            ItemStackAPI<?> stack = WrapperHelper.wrapItemStack(new ItemStack(api.unwrap()));
            if(Objects.nonNull(beforeSpawn)) beforeSpawn.accept(stack);
            spawnItem(stack,pos,onSpawn);
        }
    }
}