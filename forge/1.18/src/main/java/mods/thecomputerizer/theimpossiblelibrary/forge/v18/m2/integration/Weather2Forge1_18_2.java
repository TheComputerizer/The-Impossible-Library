package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.integration.Weather2API;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import weather2.ClientTickHandler;
import weather2.ServerTickHandler;
import weather2.weathersystem.WeatherManager;
import weather2.weathersystem.storm.WeatherEntityConfig;
import weather2.weathersystem.storm.WeatherObject;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.integration.Weather2API.WeatherType.*;

@SuppressWarnings("unused")
public class Weather2Forge1_18_2 extends Weather2API { //TODO

    public @Nullable WeatherType convertType(WeatherEntityConfig type) {
        //switch(type) {
        //    case BLIZZARD: return BLIZZARD;
        //    case CLOUD: return CLOUD;
        //    case HURRICANE: return HURRICANE;
        //    case RAIN: return RAIN;
        //    case SANDSTORM: return SANDSTORM;
        //    case SUPERCELL: return SUPERCELL;
        //    case THUNDER: return THUNDER;
        //    case TORNADO: return TORNADO;
        //    case TROPICAL_DEPRESSION: return TROPICAL_DEPRESSION;
        //    case TROPICAL_DISTURBANCE: return TROPICAL_DISTURBANCE;
        //    case TROPICAL_STORM: return TROPICAL_STORM;
        //    default: return null;
        //}
        return null;
    }

    @Override public @Nullable WeatherData getClosestBlizzard(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        return getClosestData(world,pos,distance,BLIZZARD);
    }

    @Override public @Nullable WeatherData getClosestCloud(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        return getClosestData(world,pos,distance,CLOUD);
    }

    public @Nullable WeatherData getClosestData(WorldAPI<?> world, BlockPosAPI<?> pos, double distance, WeatherType type) {
        //WeatherObject weather = getClosestWeatherType(world,pos,distance, type);
        //return Objects.nonNull(weather) ? new WeatherData(convertType(weather.weatherObjectType),toJomlVec(weather.pos),weather.getStage()) : null;
        return null;
    }

    @Override public @Nullable WeatherData getClosestHurricane(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        return getClosestData(world,pos,distance,HURRICANE);
    }

    @Override public @Nullable WeatherData getClosestSandStorm(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        //WeatherManager manager = getManager(world);
        //if(Objects.nonNull(manager)) {
        //    WeatherObject storm = manager.getClosestSandstorm(toVec(pos),distance);
        //    return Objects.nonNull(storm) ? new WeatherData(convertType(storm.type),toJomlVec(storm.pos),storm.getStage()) : null;
        //}
        return null;
    }

    @Override public @Nullable WeatherData getClosestStorm(WorldAPI<?> world, BlockPosAPI<?> pos, double distance) {
        //WeatherManager manager = getManager(world);
        //if(Objects.nonNull(manager)) {
        //    WeatherObject weather = manager.getClosestWeather(toVec(pos),distance);
        //    return Objects.nonNull(weather) ? new WeatherData(convertType(weather.type),toJomlVec(weather.pos),weather.getStage()) : null;
        //}
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
            double dist = weather.pos.distanceToSqr(vec)-(double)weather.size;
            if(dist<minDist) {
                minDist = dist;
                ret = weather;
            }
        }
        return ret;
    }

    public @Nullable WeatherManager getManager(WorldAPI<?> world) {
        if(world.isClient()) return ClientTickHandler.getClientWeather();
        return ServerTickHandler.getWeatherManagerFor(((Level)world.getWrapped()).dimension());
    }

    public Map<WeatherObject,Integer> getWeatherTypeAround(WorldAPI<?> world, BlockPosAPI<?> pos, double distance, WeatherType type) {
        //WeatherManager manager = getManager(world);
        //Map<WeatherObject,Integer> map = Objects.nonNull(manager) ?
        //        manager.getWeatherSystems(toVec(pos),distance) : Collections.emptyMap();
        //map.entrySet().removeIf(entry -> convertType(entry.getKey().type)!=type);
        return Collections.emptyMap();
    }

    public Vector3d toJomlVec(Vec3 vec) {
        return new Vector3d(vec.x,vec.y,vec.z);
    }

    public Vec3 toVec(BlockPosAPI<?> pos) {
        return new Vec3(pos.x(),pos.y(),pos.z());
    }
}