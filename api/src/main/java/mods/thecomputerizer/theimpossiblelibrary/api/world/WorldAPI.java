package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class WorldAPI<W> extends AbstractWrapped<W> {
    
    protected WorldAPI(W world) {
        super(world);
    }
    
    public abstract boolean canSnowAt(BlockPosAPI<?> pos);
    @IndirectCallers public abstract BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos);
    @IndirectCallers public abstract Collection<BlockEntityAPI<?,?>> getBlockEntitiesInBox(Box box);
    @IndirectCallers public abstract @Nullable BlockEntityAPI<?,?> getBlockEntityAt(BlockPosAPI<?> pos);
    @IndirectCallers public abstract int getDayNumber();
    public abstract int getDifficultyOrdinal();
    public abstract DimensionAPI<?> getDimension();
    @IndirectCallers public abstract List<EntityAPI<?,?>> getEntitiesInBox(Box box);
    @IndirectCallers public abstract int getLightBlock(BlockPosAPI<?> pos);
    @IndirectCallers public abstract int getLightSky(BlockPosAPI<?> pos);
    @IndirectCallers public abstract int getLightTotal(BlockPosAPI<?> pos);
    @IndirectCallers public abstract List<LivingEntityAPI<?,?>> getLivingInBox(Box box);

    public MaterialAPI<?> getMaterialAt(BlockPosAPI<?> pos) {
        return getStateAt(pos).getMaterial();
    }
    
    @IndirectCallers public abstract int getMoonPhase();
    public abstract BlockStateAPI<?> getStateAt(BlockPosAPI<?> pos);
    @IndirectCallers public abstract StructureAPI<?> getStructureAt(BlockPosAPI<?> pos);
    public abstract long getTimeDay();
    public abstract long getTimeTotal();
    public abstract boolean isClient();
    public abstract boolean isDaytime();
    
    @IndirectCallers
    public boolean isDifficultyEasy() {
        return getDifficultyOrdinal()==1;
    }
    
    @IndirectCallers
    public boolean isDifficultyHard() {
        return getDifficultyOrdinal()>=3;
    }
    
    @IndirectCallers
    public boolean isDifficultyHardcore() {
        return getDifficultyOrdinal()==4;
    }
    
    @IndirectCallers
    public boolean isDifficultyNormal() {
        return getDifficultyOrdinal()==2;
    }
    
    @IndirectCallers
    public boolean isDifficultyPeaceful() {
        return getDifficultyOrdinal()==0;
    }
    
    @IndirectCallers
    public boolean isNighttime() {
        return !isDaytime();
    }
    
    public abstract boolean isRaining();

    public boolean isServer() {
        return !isClient();
    }
    
    @IndirectCallers public abstract boolean isSkyVisible(BlockPosAPI<?> pos);
    
    @IndirectCallers
    public boolean isSnowingAt(BlockPosAPI<?> pos) {
        return isRaining() && canSnowAt(pos);
    }
    
    @IndirectCallers public abstract boolean isStorming();
    @IndirectCallers public abstract boolean isSunrise();
    @IndirectCallers public abstract boolean isSunset();
    
    @IndirectCallers
    public boolean isUnderwater(BlockPosAPI<?> pos) {
        MaterialAPI<?> material = getMaterialAt(pos);
        return Objects.nonNull(material) && material.isUnderwater();
    }
    
    @IndirectCallers public abstract void setState(BlockPosAPI<?> pos, BlockStateAPI<?> state);
    
    @IndirectCallers
    public void spawnEntity(EntityAPI<?,?> entity) {
        spawnEntity(entity,null);
    }
    
    public abstract void spawnEntity(EntityAPI<?,?> entity, @Nullable Consumer<EntityAPI<?,?>> onSpawn);
    
    @IndirectCallers
    public void spawnItem(ItemStackAPI<?> stack, Vector3d pos) {
        spawnItem(stack,pos,null);
    }
    
    public abstract void spawnItem(ItemStackAPI<?> stack, Vector3d pos, @Nullable Consumer<EntityAPI<?,?>> onSpawn);
    
    @IndirectCallers
    public void spawnItem(ItemAPI<?> item, Vector3d pos) {
        spawnItem(item,pos,null,null);
    }
    
    @IndirectCallers
    public void spawnItem(ItemAPI<?> item, Vector3d pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn) {
        spawnItem(item,pos,beforeSpawn,null);
    }
    
    public abstract void spawnItem(ItemAPI<?> stack, Vector3d pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn,
            @Nullable Consumer<EntityAPI<?,?>> onSpawn);
}