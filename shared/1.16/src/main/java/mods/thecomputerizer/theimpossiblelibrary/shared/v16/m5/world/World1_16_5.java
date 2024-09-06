package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public abstract class World1_16_5<W> extends WorldAPI<W> {

    protected World1_16_5(W world) {
        super(world);
    }

    @Override public int getDayNumber() {
        return (int)((double)getTimeTotal()/24000d);
    }

    @Override public long getTimeDay() {
        return getTimeTotal()%24000L;
    }

    @Override public boolean isSunrise() {
        return getTimeDay()>=23000L;
    }

    @Override public boolean isSunset() {
        long time = getTimeDay();
        return time>=12000L && time<13000L;
    }
}
