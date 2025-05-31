package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.integration;

import corgitaco.enhancedcelestials.EnhancedCelestialsWorldData;
import corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
import corgitaco.enhancedcelestials.core.EnhancedCelestialsContext;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.EnhancedCelestialsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class EnhancedCelestialsForge1_20_1 extends EnhancedCelestialsAPI {

    public @Nullable EnhancedCelestialsContext getContext(WorldAPI<?> api) {
        return ((EnhancedCelestialsWorldData)api.unwrap()).getLunarContext();
    }

    @Override public boolean isBloodMoon(WorldAPI<?> world) {
        return isEvent(world,"bloodmoon");
    }

    @Override public boolean isBlueMoon(WorldAPI<?> world) {
        return isEvent(world,"bluemoon");
    }

    public boolean isEvent(WorldAPI<?> world, String type) {
        EnhancedCelestialsContext context = getContext(world);
        if(Objects.nonNull(context)) {
            Holder<LunarEvent> event = context.getLunarForecast().getCurrentEventRaw();
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