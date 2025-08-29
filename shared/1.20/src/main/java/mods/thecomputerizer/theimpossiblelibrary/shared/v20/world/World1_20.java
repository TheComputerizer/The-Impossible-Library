package mods.thecomputerizer.theimpossiblelibrary.shared.v20.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.biome.Biome1_20;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static net.minecraft.core.registries.Registries.STRUCTURE;
import static net.minecraft.world.level.LightLayer.BLOCK;
import static net.minecraft.world.level.LightLayer.SKY;

public class World1_20 extends WorldAPI<LevelAccessor> {
    
    public World1_20(Object world) {
        super(world);
    }
    
    @Override public boolean canSnowAt(BlockPosAPI<?> api) {
        if(Objects.isNull(this.wrapped)) return false;
        BlockPos pos = api.unwrap();
        return this.wrapped.getBiome(pos).value().coldEnoughToSnow(pos);
    }
    
    @Override public BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos) {
        if(Objects.isNull(this.wrapped)) return null;
        BiomeAPI<Biome> biome = WrapperHelper.wrapBiome(this.wrapped.getBiome(pos.unwrap()).value());
        ((Biome1_20)biome).setAccess(this.wrapped.registryAccess());
        return biome;
    }
    
    @Override public Collection<BlockEntityAPI<?,?>> getBlockEntitiesInBox(Box box) {
        List<BlockEntityAPI<?,?>> entities = new ArrayList<>();
        if(this.wrapped instanceof Level) {
            synchronized(this.wrapped) {
                for(ChunkAccess chunk : getChunks(box))
                    for(BlockPos pos : chunk.getBlockEntitiesPos())
                        if(box.isInside(pos.getX(),pos.getY(),pos.getZ()))
                            entities.add(WrapperHelper.wrapBlockEntity(chunk.getBlockEntity(pos)));
            }
        }
        return entities;
    }
    
    @Override public @Nullable BlockEntityAPI<?,?> getBlockEntityAt(BlockPosAPI<?> pos) {
        BlockEntity tile = getIfNotNull(w -> w.getBlockEntity(pos.unwrap()));
        return Objects.nonNull(tile) ? WrapperHelper.wrapBlockEntity(tile) : null;
    }
    
    Collection<ChunkAccess> getChunks(Box box) {
        if(Objects.isNull(this.wrapped)) return Collections.emptySet();
        Set<ChunkAccess> chunks = new HashSet<>();
        for(double x = box.minX();x<box.maxX();x+=16d) {
            for(double z = box.minZ();x<box.maxZ();x+=16d) {
                int chunkX = SectionPos.blockToSectionCoord((int)x);
                int chunkZ = SectionPos.blockToSectionCoord((int)z);
                chunks.add(this.wrapped.getChunk(chunkX,chunkZ));
            }
        }
        return chunks;
    }
    
    @Override public int getDayNumber() {
        return (int)((double)getTimeTotal()/24000d);
    }
    
    @Override public int getDifficultyOrdinal() {
        if(Objects.isNull(this.wrapped)) return -1;
        if(this.wrapped.getLevelData().isHardcore()) return 4;
        return switch(this.wrapped.getDifficulty()) {
            case PEACEFUL -> 0;
            case EASY -> 1;
            case NORMAL -> 2;
            case HARD -> 3;
        };
    }
    
    @Override public DimensionAPI<?> getDimension() {
        return getIfNotNull(w -> WrapperHelper.wrapDimension(this,w.dimensionType()));
    }
    
    @Override public List<EntityAPI<?,?>> getEntitiesInBox(Box box) {
        return getEntitiesInBox(new AABB(box.min.dX(),box.min.dY(),box.min.dZ(),box.max.dX(),box.max.dY(),box.max.dZ()));
    }
    
    private List<EntityAPI<?,?>> getEntitiesInBox(Object box) {
        if(Objects.isNull(this.wrapped)) return Collections.emptyList();
        List<EntityAPI<?,?>> entities = new ArrayList<>();
        for(Entity entity : this.wrapped.getEntitiesOfClass(Entity.class,(AABB)box))
            entities.add(WrapperHelper.wrapEntity(entity));
        return entities;
    }
    
    @Override public int getLightBlock(BlockPosAPI<?> pos) {
        return getIfNotNullOrDefault(w -> w.getBrightness(BLOCK,pos.unwrap()),0);
    }
    
    @Override public int getLightSky(BlockPosAPI<?> pos) {
        return getIfNotNullOrDefault(w -> w.getBrightness(SKY,pos.unwrap()),0);
    }
    
    @Override public int getLightTotal(BlockPosAPI<?> pos) {
        return getIfNotNullOrDefault(
                w -> w.getLightEngine().getRawBrightness(pos.unwrap(),0),0);
    }
    
    @Override public List<LivingEntityAPI<?,?>> getLivingInBox(Box box) {
        if(Objects.isNull(this.wrapped)) return Collections.emptyList();
        return getLivingInBox(new AABB(box.min.dX(),box.min.dY(),box.min.dZ(),box.max.dX(),box.max.dY(),box.max.dZ()));
    }
    
    private List<LivingEntityAPI<?,?>> getLivingInBox(Object box) {
        if(Objects.isNull(this.wrapped)) return Collections.emptyList();
        List<LivingEntityAPI<?,?>> entities = new ArrayList<>();
        for(LivingEntity entity : this.wrapped.getEntitiesOfClass(LivingEntity.class,(AABB)box))
            entities.add(WrapperHelper.wrapLivingEntity(entity));
        return entities;
    }
    
    @Override public int getMoonPhase() {
        return getIfNotNullOrDefault(LevelAccessor::getMoonPhase,0);
    }
    
    private Raid getRaid(BlockPosAPI<?> pos) {
        return Objects.isNull(this.wrapped) || this.wrapped.isClientSide() ?
                null : ((ServerLevel)this.wrapped).getRaidAt(pos.unwrap());
    }
    
    @Override public @Nullable String getRaidStatus(BlockPosAPI<?> pos) {
        Raid raid = getRaid(pos);
        if(Objects.isNull(raid)) return null;
        if(raid.isVictory()) return "VICTORY";
        if(raid.isLoss()) return "LOSS";
        if(raid.isStopped()) return "STOPPED";
        if(raid.isActive()) return "ONGOING";
        return null;
    }
    
    @Override public int getRaidWave(BlockPosAPI<?> pos) {
        Raid raid = getRaid(pos);
        return Objects.nonNull(raid) ? raid.getGroupsSpawned() : -1;
    }
    
    @Override public BlockStateAPI<?> getStateAt(BlockPosAPI<?> pos) {
        return getIfNotNull(w -> WrapperHelper.wrapState(w.getBlockState(pos.unwrap())));
    }
    
    @Override public StructureAPI<?> getStructureAt(BlockPosAPI<?> api) {
        if(this.wrapped instanceof ServerLevel) {
            StructureManager manager = ((ServerLevel)this.wrapped).structureManager();
            BlockPos pos = api.unwrap();
            RegistryAccess access = this.wrapped.registryAccess();
            Optional<Registry<Structure>> optional = access.registry(STRUCTURE);
            if(optional.isEmpty()) return null;
            for(Entry<ResourceKey<Structure>,Structure> entry : optional.get().entrySet()) {
                Structure structure = entry.getValue();
                if(manager.getStructureAt(pos,structure).isValid())
                    return WrapperHelper.wrapStructure(structure);
            }
        }
        return null;
    }
    
    @Override public long getTimeDay() {
        return getTimeTotal()%24000L;
    }
    
    @Override public long getTimeTotal() {
        return getIfNotNullOrDefault(LevelAccessor::dayTime,0L);
    }
    
    @Override public boolean isClient() {
        return getIfNotNullOrDefault(LevelAccessor::isClientSide,false);
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
        return getIfNotNullOrDefault(w -> w.canSeeSky(pos.unwrap()),false);
    }
    
    @Override public boolean isStorming() {
        return this.wrapped instanceof Level && ((Level)this.wrapped).isThundering();
    }
    
    @Override public boolean isSunrise() {
        return getTimeDay()>=23000L;
    }
    
    @Override public boolean isSunset() {
        long time = getTimeDay();
        return time>=12000L && time<13000L;
    }
    
    @Override public void setState(BlockPosAPI<?> pos, BlockStateAPI<?> state) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setBlock(pos.unwrap(),state.unwrap(),2);
    }
    
    @Override public void spawnEntity(EntityAPI<?,?> entity, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(Objects.nonNull(this.wrapped) && !this.wrapped.isClientSide()) {
            this.wrapped.addFreshEntity(entity.unwrapEntity());
            if(Objects.nonNull(onSpawn)) onSpawn.accept(entity);
        }
    }
    
    @Override public void spawnItem(ItemStackAPI<?> stack, Vector3 pos, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(this.wrapped instanceof Level && !this.wrapped.isClientSide()) {
            ItemEntity item = new ItemEntity((Level)this.wrapped,pos.dX(),pos.dY(),pos.dZ(),stack.unwrap());
            item.setDefaultPickUpDelay();
            spawnEntity(WrapperHelper.wrapEntity(item),onSpawn);
        }
    }
    
    @Override public void spawnItem(ItemAPI<?> api, Vector3 pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn,
            @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(Objects.nonNull(this.wrapped) && !this.wrapped.isClientSide()) {
            ItemStackAPI<?> stack = WrapperHelper.wrapItemStack(new ItemStack((Item)api.unwrap()));
            if(Objects.nonNull(beforeSpawn)) beforeSpawn.accept(stack);
            spawnItem(stack,pos,onSpawn);
        }
    }
}