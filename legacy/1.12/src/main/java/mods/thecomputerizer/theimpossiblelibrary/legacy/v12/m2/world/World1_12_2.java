package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.biome.Biome1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Entity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Living1_12_2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class World1_12_2 extends WorldAPI<World> {


    public World1_12_2(World world) {
        super(world);
    }

    @Override
    public boolean canSnowAt(BlockPosAPI<?> pos) {
        return this.world.canSnowAt(((BlockPos1_12_2)pos).getPos(),false);
    }

    @Override
    public BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos) {
        return new Biome1_12_2(this.world.getBiome(((BlockPos1_12_2)pos).getPos()));
    }

    @Override
    public int getDayNumber() {
        return (int)((double)getTimeTotal()/24000d);
    }

    @Override
    public int getDifficultyOrdinal() {
        if(this.world.getWorldInfo().isHardcoreModeEnabled()) return 4;
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
        return new Dimension1_12_2(this);
    }

    @Override
    public List<EntityAPI<?,?>> getEntitiesInBox(Box box) {
        return getEntitiesInBox(new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }

    public List<EntityAPI<?,?>> getEntitiesInBox(AxisAlignedBB box) {
        List<EntityAPI<?,?>> entities = new ArrayList<>();
        for(Entity entity : this.world.getEntitiesWithinAABB(Entity.class,box)) entities.add(new Entity1_12_2(entity));
        return entities;
    }

    @Override
    public int getLightBlock(BlockPosAPI<?> pos) {
        return this.world.getLightFor(EnumSkyBlock.BLOCK,((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public int getLightSky(BlockPosAPI<?> pos) {
        return this.world.getLightFor(EnumSkyBlock.SKY,((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public int getLightTotal(BlockPosAPI<?> pos) {
        return this.world.getLight(((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public List<LivingEntityAPI<?,?>> getLivingInBox(Box box) {
        return getLivingInBox(new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }

    public List<LivingEntityAPI<?,?>> getLivingInBox(AxisAlignedBB box) {
        List<LivingEntityAPI<?,?>> entities = new ArrayList<>();
        for(EntityLivingBase entity : this.world.getEntitiesWithinAABB(EntityLivingBase.class,box))
            entities.add(new Living1_12_2(entity));
        return entities;
    }

    @Override
    public int getMoonPhase() {
        return this.world.getMoonPhase();
    }

    @Override
    public BlockStateAPI<?> getStateAt(BlockPosAPI<?> pos) {
        return new BlockState1_12_2(this.world.getBlockState(((BlockPos1_12_2)pos).getPos()));
    }

    @Override
    public long getTimeDay() {
        return getTimeTotal()%24000L;
    }

    @Override
    public long getTimeTotal() {
        return this.world.getWorldTime();
    }

    @Override
    public boolean isClient() {
        return this.world.isRemote;
    }

    @Override
    public boolean isDaytime() {
        return this.world.isDaytime();
    }

    @Override
    public boolean isRaining() {
        return this.world.isRaining();
    }

    @Override
    public boolean isSkyVisible(BlockPosAPI<?> pos) {
        return this.world.canBlockSeeSky(((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public boolean isStorming() {
        return this.world.isThundering();
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
}
