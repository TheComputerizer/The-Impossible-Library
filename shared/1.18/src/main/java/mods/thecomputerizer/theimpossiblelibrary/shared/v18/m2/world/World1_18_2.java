package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.world;

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
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.biome.Biome1_18_2;
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
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import static net.minecraft.core.Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY;
import static net.minecraft.world.level.LightLayer.BLOCK;
import static net.minecraft.world.level.LightLayer.SKY;

public class World1_18_2 extends WorldAPI<LevelAccessor> {
    
    public World1_18_2(Object world) {
        super((LevelAccessor)world);
    }
    
    @Override public boolean canSnowAt(BlockPosAPI<?> api) {
        BlockPos pos = api.unwrap();
        return this.wrapped.getBiome(pos).value().coldEnoughToSnow(pos);
    }
    
    @Override public BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos) {
        BiomeAPI<Biome> biome = WrapperHelper.wrapBiome(this.wrapped.getBiome(pos.unwrap()).value());
        ((Biome1_18_2)biome).setAccess(this.wrapped.registryAccess());
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
        BlockEntity tile = this.wrapped.getBlockEntity(pos.unwrap());
        return Objects.nonNull(tile) ? WrapperHelper.wrapBlockEntity(tile) : null;
    }
    
    Collection<ChunkAccess> getChunks(Box box) {
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
        if(this.wrapped.getLevelData().isHardcore()) return 4;
        return switch(this.wrapped.getDifficulty()) {
            case PEACEFUL -> 0;
            case EASY -> 1;
            case NORMAL -> 2;
            case HARD -> 3;
        };
    }
    
    @Override public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(this,this.wrapped.dimensionType());
    }
    
    @Override public List<EntityAPI<?,?>> getEntitiesInBox(Box box) {
        return getEntitiesInBox(new AABB(box.min.dX(),box.min.dY(),box.min.dZ(),box.max.dX(),box.max.dY(),box.max.dZ()));
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
        return getLivingInBox(new AABB(box.min.dX(),box.min.dY(),box.min.dZ(),box.max.dX(),box.max.dY(),box.max.dZ()));
    }
    
    private List<LivingEntityAPI<?,?>> getLivingInBox(Object box) {
        List<LivingEntityAPI<?,?>> entities = new ArrayList<>();
        for(LivingEntity entity : this.wrapped.getEntitiesOfClass(LivingEntity.class, (AABB)box))
            entities.add(WrapperHelper.wrapLivingEntity(entity));
        return entities;
    }
    
    @Override public int getMoonPhase() {
        return this.wrapped.getMoonPhase();
    }
    
    private Raid getRaid(BlockPosAPI<?> pos) {
        return this.wrapped.isClientSide() ? null : ((ServerLevel)this.wrapped).getRaidAt(pos.unwrap());
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
        return WrapperHelper.wrapState(this.wrapped.getBlockState(pos.unwrap()));
    }
    
    @Override public StructureAPI<?> getStructureAt(BlockPosAPI<?> api) {
        if(this.wrapped instanceof ServerLevel) {
            StructureFeatureManager manager = ((ServerLevel)this.wrapped).structureFeatureManager();
            BlockPos pos = api.unwrap();
            RegistryAccess access = this.wrapped.registryAccess();
            Registry<ConfiguredStructureFeature<?,?>>
                    registry = access.registry(CONFIGURED_STRUCTURE_FEATURE_REGISTRY).orElse(null);
            if(Objects.isNull(registry)) return null;
            for(Entry<ResourceKey<ConfiguredStructureFeature<?,?>>,ConfiguredStructureFeature<?,?>> entry : registry.entrySet()) {
                ConfiguredStructureFeature<?,?> structure = entry.getValue();
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
    
    @Override public void spawnItem(
            ItemAPI<?> api, Vector3 pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn,
            @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.wrapped.isClientSide()) {
            ItemStackAPI<?> stack = WrapperHelper.wrapItemStack(new ItemStack((Item)api.unwrap()));
            if(Objects.nonNull(beforeSpawn)) beforeSpawn.accept(stack);
            spawnItem(stack,pos,onSpawn);
        }
    }
}
