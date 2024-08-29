package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration;

import corgitaco.betterweather.api.weather.WeatherEvent;
import corgitaco.betterweather.helpers.BetterWeatherWorldData;
import corgitaco.betterweather.weather.BWWeatherEventContext;
import corgitaco.betterweather.weather.event.AcidRain;
import corgitaco.betterweather.weather.event.Blizzard;
import corgitaco.betterweather.weather.event.Cloudy;
import corgitaco.betterweather.weather.event.Rain;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.BetterWeatherAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;

public class BetterWeatherForge1_16_5 extends BetterWeatherAPI {

    public @Nullable BWWeatherEventContext getContext(WorldAPI<?> api) {
        Object world = api.getWorld();
        return world instanceof World ? ((BetterWeatherWorldData)world).getWeatherEventContext() : null;
    }

    @Override
    public boolean isAcidRaining(WorldAPI<?> world) {
        return isEvent(world,AcidRain.class);
    }

    @Override
    public boolean isBlizzard(WorldAPI<?> world) {
        return isEvent(world, Blizzard.class);
    }

    @Override
    public boolean isCloudy(WorldAPI<?> world) {
        return isEvent(world,Cloudy.class);
    }

    public boolean isEvent(WorldAPI<?> world, Class<? extends WeatherEvent> eventClass) {
        BWWeatherEventContext context = getContext(world);
        if(Objects.nonNull(context)) {
            WeatherEvent event = context.getCurrentEvent();
            return Objects.nonNull(event) && event.getClass()==eventClass;
        }
        return false;
    }

    @Override
    public boolean isRaining(WorldAPI<?> world) {
        return isEvent(world,Rain.class);
    }
}