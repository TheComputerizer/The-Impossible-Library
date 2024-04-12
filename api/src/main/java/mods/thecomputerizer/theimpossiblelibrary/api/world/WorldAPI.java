package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;

import java.util.List;

public interface WorldAPI<W> {

    int getDayNumber();
    List<EntityAPI<?>> getEntitiesInBox(Box box);
    int getLightBlock(BlockPosAPI<?> pos);
    int getLightSky(BlockPosAPI<?> pos);
    int getLightTotal(BlockPosAPI<?> pos);
    List<LivingEntityAPI<?>> getLivingInBox(Box box);
    int getMoonPhase();
    WorldSettingsAPI<?> getSettings();
    long getTimeDay();
    long getTimeTotal();
    W getWorld();
    boolean isClient();
    boolean isDaytime();
    boolean isDifficultyEasy();
    boolean isDifficultyHard();
    boolean isDifficultyHardcore();
    boolean isDifficultyNormal();
    boolean isDifficultyPeaceful();
    boolean isNighttime();
    boolean isRaining();
    boolean isServer();
    boolean isSkyVisible(BlockPosAPI<?> pos);
    boolean isSnowing(BlockPosAPI<?> pos);
    boolean isStorming();
    boolean isSunrise();
    boolean isSunset();
    boolean isUnderwater(BlockPosAPI<?> pos);
}