package mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.integration;

import dev.corgitaco.enhancedcelestials.EnhancedCelestials;
import dev.corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
import dev.corgitaco.enhancedcelestials.lunarevent.EnhancedCelestialsLunarForecastWorldData;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.EnhancedCelestialsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class EnhancedCelestialsForge1_21_1 extends EnhancedCelestialsAPI {

    public @Nullable EnhancedCelestialsLunarForecastWorldData getContext(WorldAPI<?> api) {
        return orElseNull(EnhancedCelestials.lunarForecastWorldData(api.unwrap()));
    }

    @Override public boolean isBloodMoon(WorldAPI<?> world) {
        return isEvent(world,"bloodmoon");
    }

    @Override public boolean isBlueMoon(WorldAPI<?> world) {
        return isEvent(world,"bluemoon");
    }

    public boolean isEvent(WorldAPI<?> world, String type) {
        EnhancedCelestialsLunarForecastWorldData context = getContext(world);
        if(Objects.nonNull(context)) {
            Holder<LunarEvent> event = context.currentLunarEventHolder();
            Optional<ResourceKey<LunarEvent>> optional = event.unwrapKey();
            if(optional.isEmpty()) return false;
            ResourceKey<LunarEvent> key = optional.get();
            return key.registry().toString().contains(type);
        }
        return false;
    }

    @Override public boolean isHarvestMoon(WorldAPI<?> world) {
        return isEvent(world,"harvestmoon");
    }

    @Override public boolean isMoon(WorldAPI<?> world) {
        return isEvent(world,":moon");
    }
}