package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.integration.Weather2API;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.mrbt0907.weather2.api.WeatherAPI;
import net.mrbt0907.weather2.api.weather.WeatherEnum.Type;
import net.mrbt0907.weather2.util.Maths.Vec3;
import net.mrbt0907.weather2.weather.WeatherManager;
import net.mrbt0907.weather2.weather.storm.SandstormObject;
import net.mrbt0907.weather2.weather.storm.WeatherObject;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.integration.Weather2API.WeatherType.*;

public class Weather21_12_2 extends Weather2API {

    public @Nullable WeatherType convertType(Type type) {
        switch(type) {
            case BLIZZARD: return BLIZZARD;
            case CLOUD: return CLOUD;
            case HURRICANE: return HURRICANE;
            case RAIN: return RAIN;
            case SANDSTORM: return SANDSTORM;
            case SUPERCELL: return SUPERCELL;
            case THUNDER: return THUNDER;
            case TORNADO: return TORNADO;
            case TROPICAL_DEPRESSION: return TROPICAL_DEPRESSION;
            case TROPICAL_DISTURBANCE: return TROPICAL_DISTURBANCE;
            case TROPICAL_STORM: return TROPICAL_STORM;
            default: return null;
        }
    }

    @Override public @Nullable WeatherData getClosestBlizzard(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        return getClosestData(world,pos,distance,BLIZZARD);
    }

    @Override public @Nullable WeatherData getClosestCloud(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        return getClosestData(world,pos,distance,CLOUD);
    }

    public @Nullable WeatherData getClosestData(WorldAPI<?> world, BlockPosAPI<?> pos, double distance, WeatherType type) {
        WeatherObject weather = getClosestWeatherType(world,pos,distance,type);
        return Objects.nonNull(weather) ? new WeatherData(convertType(weather.type),toJomlVec(weather.pos),weather.getStage()) : null;
    }

    @Override public @Nullable WeatherData getClosestHurricane(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        return getClosestData(world,pos,distance,HURRICANE);
    }

    @Override public @Nullable WeatherData getClosestSandStorm(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        WeatherManager manager = getManager(world);
        if(Objects.nonNull(manager)) {
            SandstormObject storm = manager.getClosestSandstorm(toVec(pos),distance);
            return Objects.nonNull(storm) ? new WeatherData(convertType(storm.type),toJomlVec(storm.pos),storm.getStage()) : null;
        }
        return null;
    }

    @Override public @Nullable WeatherData getClosestStorm(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        WeatherManager manager = getManager(world);
        if(Objects.nonNull(manager)) {
            WeatherObject weather = manager.getClosestWeather(toVec(pos),distance);
            return Objects.nonNull(weather) ? new WeatherData(convertType(weather.type),toJomlVec(weather.pos),weather.getStage()) : null;
        }
        return null;
    }

    @Override public @Nullable WeatherData getClosestTornado(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        return getClosestData(world,pos,distance,TORNADO);
    }

    public @Nullable WeatherObject getClosestWeatherType(WorldAPI<?> world, BlockPosAPI<?> pos, double distance, WeatherType type) {
        Map<WeatherObject,Integer> systems = getWeatherTypeAround(world,pos,distance,type);
        Vec3 vec = toVec(pos);
        WeatherObject ret = null;
        double minDist = distance+1d;
        for(WeatherObject weather : systems.keySet()) {
            double dist = weather.pos.distanceSq(vec)-(double)weather.size;
            if(dist<minDist) {
                minDist = dist;
                ret = weather;
            }
        }
        return ret;
    }

    public @Nullable WeatherManager getManager(WorldAPI<?> world) {
        return WeatherAPI.getManager(world.unwrap());
    }

    public Map<WeatherObject,Integer> getWeatherTypeAround(WorldAPI<?> world, BlockPosAPI<?> pos, double distance, WeatherType type) {
        WeatherManager manager = getManager(world);
        Map<WeatherObject,Integer> map = Objects.nonNull(manager) ?
                manager.getWeatherSystems(toVec(pos),distance) : Collections.emptyMap();
        map.entrySet().removeIf(entry -> convertType(entry.getKey().type)!=type);
        return map;
    }

    public Vector3d toJomlVec(Vec3 vec) {
        return new Vector3d(vec.posX,vec.posY,vec.posZ);
    }

    public Vec3 toVec(BlockPosAPI<?> pos) {
        return new Vec3(pos.x(),pos.y(),pos.z());
    }
}