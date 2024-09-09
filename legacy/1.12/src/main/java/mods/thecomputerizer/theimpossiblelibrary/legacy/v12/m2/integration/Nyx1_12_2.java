package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration;

import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.lunarevents.*;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.NyxAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Objects;

public class Nyx1_12_2 extends NyxAPI {

    public @Nullable NyxWorld getWorld(WorldAPI<?> world) {
        return NyxWorld.get(world.unwrap());
    }

    @Override public boolean isBloodMoon(WorldAPI<?> world) {
        return isEvent(world,BloodMoon.class);
    }

    public boolean isEvent(WorldAPI<?> world, Class<? extends LunarEvent> eventClass) {
        NyxWorld nyxWorld = getWorld(world);
        if(Objects.nonNull(nyxWorld)) {
            LunarEvent event = nyxWorld.currentEvent;
            return Objects.nonNull(event) && event.getClass()==eventClass;
        }
        return false;
    }

    @Override public boolean isStarShower(WorldAPI<?> world) {
        return isEvent(world,StarShower.class);
    }

    @Override public boolean isFullMoon(WorldAPI<?> world) {
        return isEvent(world,FullMoon.class);
    }

    @Override public boolean isHarvestMoon(WorldAPI<?> world) {
        return isEvent(world,HarvestMoon.class);
    }
}