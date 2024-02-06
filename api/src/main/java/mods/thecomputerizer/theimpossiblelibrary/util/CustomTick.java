package mods.thecomputerizer.theimpossiblelibrary.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public abstract class CustomTick {

    private static final List<Long> registeredTickEvents = new ArrayList<>();

    private static void addCustomTickMillis(long millis, Function<Long,CustomTick> tickSupplier) {
        CustomTick ticker = tickSupplier.apply(millis);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(ticker::tick,0,millis,TimeUnit.MILLISECONDS);
        registeredTickEvents.add(millis);
    }

    public static void addCustomTickTPS(int ticksPerSecond, Function<Long,CustomTick> tickSupplier) {
        addCustomTickMillis((long)(1000f/ticksPerSecond),tickSupplier);
    }

    public static boolean isRegistered(long millis) {
        return registeredTickEvents.contains(millis);
    }

    public static boolean isRegistered(int ticks) {
        return registeredTickEvents.contains((long)(1000f/ticks));
    }

    private final long millis;
    protected CustomTick(long millis) {
        this.millis = millis;
    }

    public boolean checkTickRateMillis(long tickRate) {
        return this.millis==tickRate;
    }

    public boolean checkTickRateTPS(int tickRate) {
        return this.millis ==(long)(1000f/tickRate);
    }

    protected abstract void tick();
}
