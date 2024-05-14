package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.biome.Biome1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity.BlockEntity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Entity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Living1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.Item1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.structure.Structure1_16_5;
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
import net.minecraftforge.registries.ForgeRegistries;
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

    public World1_16_5(IWorld world) {
        super(world);
    }

    @Override
    public boolean canSnowAt(BlockPosAPI<?> api) {
        BlockPos pos = ((BlockPos1_16_5)api).getPos();
        return this.world.getBiome(pos).shouldSnow(this.world,pos);
    }

    @Override
    public BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos) {
        return new Biome1_16_5(this.world.getBiome(((BlockPos1_16_5)pos).getPos()));
    }

    @Override
    public Collection<BlockEntityAPI<?,?>> getBlockEntitiesInBox(Box box) {
        List<BlockEntityAPI<?,?>> entities = new ArrayList<>();
        if(this.world instanceof World) {
            for(TileEntity tile : ((World)this.world).blockEntityList) {
                BlockPos pos = tile.getBlockPos();
                if(box.isInside(pos.getX(),pos.getY(),pos.getZ())) entities.add(new BlockEntity1_16_5(tile));
            }
        }
        return entities;
    }

    @Override
    public @Nullable BlockEntityAPI<?,?> getBlockEntityAt(BlockPosAPI<?> pos) {
        TileEntity tile = this.world.getBlockEntity(((BlockPos1_16_5)pos).getPos());
        return Objects.nonNull(tile) ? new BlockEntity1_16_5(tile) : null;
    }

    @Override
    public int getDayNumber() {
        return (int)((double)getTimeTotal()/24000d);
    }

    @Override
    public int getDifficultyOrdinal() {
        if(this.world.getLevelData().isHardcore()) return 4;
        switch(this.world.getDifficulty()) {
            case PEACEFUL: return 0;
            case EASY: return 1;
            case NORMAL: return 2;
            case HARD: return 3;
            default: return -1; //unreachable
        }
    }

    @Override
    public DimensionAPI<?> getDimension() {
        return new Dimension1_16_5(this);
    }

    @Override
    public List<EntityAPI<?,?>> getEntitiesInBox(Box box) {
        return getEntitiesInBox(new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }

    public List<EntityAPI<?,?>> getEntitiesInBox(AxisAlignedBB box) {
        List<EntityAPI<?,?>> entities = new ArrayList<>();
        for(Entity entity : this.world.getEntitiesOfClass(Entity.class,box)) entities.add(new Entity1_16_5(entity));
        return entities;
    }

    @Override
    public int getLightBlock(BlockPosAPI<?> pos) {
        return this.world.getBrightness(BLOCK,((BlockPos1_16_5)pos).getPos());
    }

    @Override
    public int getLightSky(BlockPosAPI<?> pos) {
        return this.world.getBrightness(SKY,((BlockPos1_16_5)pos).getPos());
    }

    @Override
    public int getLightTotal(BlockPosAPI<?> pos) {
        return this.world.getLightEngine().getRawBrightness(((BlockPos1_16_5)pos).getPos(),0);
    }

    @Override
    public List<LivingEntityAPI<?,?>> getLivingInBox(Box box) {
        return getLivingInBox(new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }

    public List<LivingEntityAPI<?,?>> getLivingInBox(AxisAlignedBB box) {
        List<LivingEntityAPI<?,?>> entities = new ArrayList<>();
        for(LivingEntity entity : this.world.getEntitiesOfClass(LivingEntity.class,box))
            entities.add(new Living1_16_5(entity));
        return entities;
    }

    @Override
    public int getMoonPhase() {
        return this.world.getMoonPhase();
    }

    @Override
    public BlockStateAPI<?> getStateAt(BlockPosAPI<?> pos) {
        return new BlockState1_16_5(this.world.getBlockState(((BlockPos1_16_5)pos).getPos()));
    }

    @Override
    public StructureAPI<?> getStructureAt(BlockPosAPI<?> api) {
        if(this.world instanceof ServerWorld) {
            StructureManager manager = ((ServerWorld)this.world).structureFeatureManager();
            BlockPos pos = ((BlockPos1_16_5)api).getPos();
            for(Structure<?> structure : ForgeRegistries.STRUCTURE_FEATURES.getValues())
                if(manager.getStructureAt(pos,false,structure).isValid())
                    return new Structure1_16_5(structure);
        }
        return null;
    }

    @Override
    public long getTimeDay() {
        return getTimeTotal()%24000L;
    }

    @Override
    public long getTimeTotal() {
        return this.world.dayTime();
    }

    @Override
    public boolean isClient() {
        return this.world.isClientSide();
    }

    @Override
    public boolean isDaytime() {
        return this.world instanceof World && ((World)this.world).isDay();
    }

    @Override
    public boolean isNighttime() {
        return this.world instanceof World && ((World)this.world).isNight();
    }

    @Override
    public boolean isRaining() {
        return this.world instanceof World && ((World)this.world).isRaining();
    }

    @Override
    public boolean isSkyVisible(BlockPosAPI<?> pos) {
        return this.world.canSeeSky(((BlockPos1_16_5)pos).getPos());
    }

    @Override
    public boolean isStorming() {
        return this.world instanceof World && ((World)this.world).isThundering();
    }

    @Override
    public boolean isSunrise() {
        return getTimeDay()>=23000L;
    }

    @Override
    public boolean isSunset() {
        long time = getTimeDay();
        return time>=12000L && time<13000L;
    }
    
    @Override public void setState(BlockPosAPI<?> pos, BlockStateAPI<?> state) {
        this.world.setBlock(((BlockPos1_16_5)pos).getPos(),((BlockState1_16_5)state).getState(),2);
    }
    
    @Override public void spawnEntity(EntityAPI<?,?> entity, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.world.isClientSide()) {
            this.world.addFreshEntity(((Entity1_16_5)entity).getEntity());
            if(Objects.nonNull(onSpawn)) onSpawn.accept(entity);
        }
    }
    
    @Override public void spawnItem(ItemStackAPI<?> api, Vector3d pos, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(this.world instanceof World && !this.world.isClientSide()) {
            ItemStack stack = ((ItemStack1_16_5)api).getStack();
            ItemEntity item = new ItemEntity((World)this.world,pos.x,pos.y,pos.z,stack);
            item.setDefaultPickUpDelay();
            spawnEntity(new Entity1_16_5(item),onSpawn);
        }
    }
    
    @Override public void spawnItem(ItemAPI<?> api, Vector3d pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn,
            @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.world.isClientSide()) {
            ItemStack stack = new ItemStack(((Item1_16_5)api).getValue());
            ItemStack1_16_5 stackAPI = new ItemStack1_16_5(stack);
            if(Objects.nonNull(beforeSpawn)) beforeSpawn.accept(stackAPI);
            spawnItem(stackAPI,pos,onSpawn);
        }
    }
}
