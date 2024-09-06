package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.integration.SereneSeasonsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import java.util.Objects;

import static sereneseasons.api.season.Season.*;

public class SereneSeasons1_12_2 extends SereneSeasonsAPI {

    public ISeasonState getSeasonState(WorldAPI<?> world) {
        return SeasonHelper.getSeasonState(world.unwrap());
    }

    @Override public boolean isAutumn(WorldAPI<?> world) {
        return isSeason(world,AUTUMN);
    }

    public boolean isSeason(WorldAPI<?> world, Season season) {
        ISeasonState state = getSeasonState(world);
        return Objects.nonNull(state) && state.getSeason()==season;
    }

    @Override public boolean isSpring(WorldAPI<?> world) {
        return isSeason(world,SPRING);
    }

    @Override public boolean isSummer(WorldAPI<?> world) {
        return isSeason(world,SUMMER);
    }

    @Override public boolean isWinter(WorldAPI<?> world) {
        return isSeason(world,WINTER);
    }
}