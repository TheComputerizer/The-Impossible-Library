package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.SereneSeasonsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import java.util.Objects;

import static sereneseasons.api.season.Season.AUTUMN;
import static sereneseasons.api.season.Season.SPRING;
import static sereneseasons.api.season.Season.SUMMER;
import static sereneseasons.api.season.Season.WINTER;

public class SereneSeasonsFabric1_20_1 extends SereneSeasonsAPI {

    public ISeasonState getSeasonState(WorldAPI<?> world) {
        return Hacks.invokeStatic(SeasonHelper.class, "getSeasonState", world.unwrap());
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