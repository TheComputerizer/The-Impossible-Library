package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.integration;

import corgitaco.enhancedcelestials.EnhancedCelestialsWorldData;
import corgitaco.enhancedcelestials.LunarContext;
import corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
import corgitaco.enhancedcelestials.lunarevent.BloodMoon;
import corgitaco.enhancedcelestials.lunarevent.BlueMoon;
import corgitaco.enhancedcelestials.lunarevent.HarvestMoon;
import corgitaco.enhancedcelestials.lunarevent.Moon;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.EnhancedCelestialsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Objects;

public class EnhancedCelestialsForge1_18_2 extends EnhancedCelestialsAPI {

    public @Nullable LunarContext getContext(WorldAPI<?> api) {
        return ((EnhancedCelestialsWorldData)api.unwrap()).getLunarContext();
    }

    @Override public boolean isBloodMoon(WorldAPI<?> world) {
        return isEvent(world,BloodMoon.class);
    }

    @Override public boolean isBlueMoon(WorldAPI<?> world) {
        return isEvent(world,BlueMoon.class);
    }

    public boolean isEvent(WorldAPI<?> world, Class<? extends LunarEvent> eventClass) {
        LunarContext context = getContext(world);
        if(Objects.nonNull(context)) {
            LunarEvent event = context.getCurrentEvent();
            return Objects.nonNull(event) && event.getClass()==eventClass;
        }
        return false;
    }

    @Override public boolean isHarvestMoon(WorldAPI<?> world) {
        return isEvent(world,HarvestMoon.class);
    }

    @Override public boolean isMoon(WorldAPI<?> world) {
        return isEvent(world,Moon.class);
    }
}