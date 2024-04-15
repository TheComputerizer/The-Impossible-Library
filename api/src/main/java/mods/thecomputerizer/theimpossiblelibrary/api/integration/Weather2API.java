package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import org.joml.Vector3d;

import javax.annotation.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V12;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.integration.Weather2API.WeatherType.*;

public abstract class Weather2API implements ModAPI {

    public static final String MODID = "weather2remaster";
    public static final String NAME = "Weather 2 - Remastered";

    protected Weather2API() {}

    public abstract @Nullable WeatherData getClosestBlizzard(WorldAPI<?> world, BlockPosAPI<?> pos, double distance);
    public abstract @Nullable WeatherData getClosestCloud(WorldAPI<?> world, BlockPosAPI<?> pos, double distance);
    public abstract @Nullable WeatherData getClosestHurricane(WorldAPI<?> world, BlockPosAPI<?> pos, double distance);
    public abstract @Nullable WeatherData getClosestSandStorm(WorldAPI<?> world, BlockPosAPI<?> pos, double distance);
    public abstract @Nullable WeatherData getClosestStorm(WorldAPI<?> world, BlockPosAPI<?> pos, double distance);
    public abstract @Nullable WeatherData getClosestTornado(WorldAPI<?> world, BlockPosAPI<?> pos, double distance);

    @Override
    public String getID() {
        return MODID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        return version==V12 && loader==LEGACY;
    }

    @Getter
    public static class WeatherData {

        private final WeatherType type;
        private final Vector3d pos;
        @Setter private int level;

        public WeatherData(@Nullable WeatherType type, Vector3d pos, int level) {
            this.type = type;
            this.pos = pos;
        }

        public boolean isBlizzard() {
            return this.type==BLIZZARD;
        }

        public boolean isHurricane() {
            return this.type==HURRICANE;
        }

        public boolean isSandstorm() {
            return this.type==SANDSTORM;
        }

        public boolean isTornado() {
            return this.type==TORNADO;
        }

        public boolean isTropical() {
            return this.type==TROPICAL_DEPRESSION || this.type==TROPICAL_DISTURBANCE || this.type==TROPICAL_STORM;
        }
    }

    public enum WeatherType {
        BLIZZARD, CLOUD, HURRICANE, RAIN, SANDSTORM,
        SUPERCELL, THUNDER, TORNADO, TROPICAL_DEPRESSION, TROPICAL_DISTURBANCE,
        TROPICAL_STORM
    }
}