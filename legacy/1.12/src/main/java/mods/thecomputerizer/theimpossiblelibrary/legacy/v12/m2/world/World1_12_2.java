package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldSettingsAPI;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

import java.util.Collections;
import java.util.List;

public class World1_12_2 implements WorldAPI<World> {

    private final World world;

    public World1_12_2(World world) {
        this.world = world;
    }

    @Override
    public int getDayNumber() {
        return (int)((double)getTimeTotal()/24000d);
    }

    @Override
    public List<EntityAPI<?>> getEntitiesInBox(Box box) { //TODO
        return Collections.emptyList();
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
    public List<LivingEntityAPI<?>> getLivingInBox(Box box) { //TODO
        return Collections.emptyList();
    }

    @Override
    public int getMoonPhase() {
        return this.world.getMoonPhase();
    }

    @Override
    public WorldSettingsAPI<WorldSettings> getSettings() { //TODO
        return null;
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
    public World getWorld() {
        return this.world;
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
    public boolean isDifficultyEasy() {
        return this.world.getDifficulty()==EnumDifficulty.EASY;
    }

    @Override
    public boolean isDifficultyHard() {
        return this.world.getDifficulty()==EnumDifficulty.HARD;
    }

    @Override
    public boolean isDifficultyHardcore() {
        return this.world.getWorldInfo().isHardcoreModeEnabled();
    }

    @Override
    public boolean isDifficultyNormal() {
        return this.world.getDifficulty()==EnumDifficulty.NORMAL;
    }

    @Override
    public boolean isDifficultyPeaceful() {
        return this.world.getDifficulty()==EnumDifficulty.PEACEFUL;
    }

    @Override
    public boolean isNighttime() {
        return !isDaytime();
    }

    @Override
    public boolean isRaining() {
        return this.world.isRaining();
    }

    @Override
    public boolean isServer() {
        return !this.world.isRemote;
    }

    @Override
    public boolean isSkyVisible(BlockPosAPI<?> pos) {
        return this.world.canBlockSeeSky(((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public boolean isSnowing(BlockPosAPI<?> pos) {
        return isRaining() && this.world.canSnowAt(((BlockPos1_12_2)pos).getPos(),false);
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

    @Override
    public boolean isUnderwater(BlockPosAPI<?> api) {
        BlockPos pos = ((BlockPos1_12_2)api).getPos();
        return this.world.getBlockState(pos).getMaterial()==Material.WATER &&
                this.world.getBlockState(pos.up()).getMaterial()==Material.WATER;
    }
}
