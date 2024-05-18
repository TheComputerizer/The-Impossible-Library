package mods.thecomputerizer.theimpossiblelibrary.api.world;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("unused") @Getter
public abstract class WorldAPI<W> {

    protected final W world;
    
    protected WorldAPI(W world) {
        this.world = world;
    }
    
    public abstract boolean canSnowAt(BlockPosAPI<?> pos);
    public abstract BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos);
    public abstract Collection<BlockEntityAPI<?,?>> getBlockEntitiesInBox(Box box);
    public abstract @Nullable BlockEntityAPI<?,?> getBlockEntityAt(BlockPosAPI<?> pos);
    public abstract int getDayNumber();
    public abstract int getDifficultyOrdinal();
    public abstract DimensionAPI<?> getDimension();
    public abstract List<EntityAPI<?,?>> getEntitiesInBox(Box box);
    public abstract int getLightBlock(BlockPosAPI<?> pos);
    public abstract int getLightSky(BlockPosAPI<?> pos);
    public abstract int getLightTotal(BlockPosAPI<?> pos);
    public abstract List<LivingEntityAPI<?,?>> getLivingInBox(Box box);

    public MaterialAPI<?> getMaterialAt(BlockPosAPI<?> pos) {
        return getStateAt(pos).getMaterial();
    }

    public abstract int getMoonPhase();
    public abstract BlockStateAPI<?> getStateAt(BlockPosAPI<?> pos);
    public abstract StructureAPI<?> getStructureAt(BlockPosAPI<?> pos);
    public abstract long getTimeDay();
    public abstract long getTimeTotal();
    public abstract boolean isClient();
    public abstract boolean isDaytime();
    
    public boolean isDifficultyEasy() {
        return getDifficultyOrdinal()==1;
    }
    
    public boolean isDifficultyHard() {
        return getDifficultyOrdinal()>=3;
    }
    
    public boolean isDifficultyHardcore() {
        return getDifficultyOrdinal()==4;
    }
    
    public boolean isDifficultyNormal() {
        return getDifficultyOrdinal()==2;
    }
    
    public boolean isDifficultyPeaceful() {
        return getDifficultyOrdinal()==0;
    }
    
    public boolean isNighttime() {
        return !isDaytime();
    }
    
    public abstract boolean isRaining();

    public boolean isServer() {
        return !isClient();
    }

    public abstract boolean isSkyVisible(BlockPosAPI<?> pos);
    
    public boolean isSnowingAt(BlockPosAPI<?> pos) {
        return isRaining() && canSnowAt(pos);
    }
    
    public abstract boolean isStorming();
    public abstract boolean isSunrise();
    public abstract boolean isSunset();

    public boolean isUnderwater(BlockPosAPI<?> pos) {
        MaterialAPI<?> material = getMaterialAt(pos);
        return Objects.nonNull(material) && material.isUnderwater();
    }
    
    public abstract void setState(BlockPosAPI<?> pos, BlockStateAPI<?> state);
    
    public void spawnEntity(EntityAPI<?,?> entity) {
        spawnEntity(entity,null);
    }
    
    public abstract void spawnEntity(EntityAPI<?,?> entity, @Nullable Consumer<EntityAPI<?,?>> onSpawn);
    
    public void spawnItem(ItemStackAPI<?> stack, Vector3d pos) {
        spawnItem(stack,pos,null);
    }
    
    public abstract void spawnItem(ItemStackAPI<?> stack, Vector3d pos, @Nullable Consumer<EntityAPI<?,?>> onSpawn);
    
    public void spawnItem(ItemAPI<?> item, Vector3d pos) {
        spawnItem(item,pos,null,null);
    }
    
    public void spawnItem(ItemAPI<?> item, Vector3d pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn) {
        spawnItem(item,pos,beforeSpawn,null);
    }
    
    public abstract void spawnItem(ItemAPI<?> stack, Vector3d pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn,
            @Nullable Consumer<EntityAPI<?,?>> onSpawn);
}