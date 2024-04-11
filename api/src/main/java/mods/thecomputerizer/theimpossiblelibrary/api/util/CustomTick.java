package mods.thecomputerizer.theimpossiblelibrary.api.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Getter
public abstract class CustomTick {

    private static final List<Long> registeredTickEvents = new ArrayList<>();

    private static void addCustomTickMillis(long millis, Function<Long,CustomTick> tickSupplier) {
        CustomTick ticker = tickSupplier.apply(millis);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(ticker::tick,0,millis,TimeUnit.MILLISECONDS);
        registeredTickEvents.add(millis);
    }

    public static void addCustomTickTPS(int ticksPerSecond, Function<Long,CustomTick> tickSupplier) {
        if(ticksPerSecond<=0) ticksPerSecond = 1;
        if(ticksPerSecond>1000) ticksPerSecond = 1000;
        addCustomTickMillis((long)(1000f/ticksPerSecond),tickSupplier);
    }

    public static boolean isRegistered(long millis) {
        return registeredTickEvents.contains(millis);
    }

    public static boolean isRegistered(int ticks) {
        return registeredTickEvents.contains((long)(1000f/ticks));
    }

    private final long millis;
    private final int tps;

    protected CustomTick(long millis) {
        this(millis,(int)(1000d/(double)millis));
    }

    protected CustomTick(int tps) {
        this((long)(1000d/(double)tps),tps);
    }

    private CustomTick(long millis, int tps) {
        this.millis = millis;
        this.tps = tps;
    }

    public boolean isEquivalentMillis(long millis) {
        return this.millis==millis;
    }

    public boolean isEquivalentTPS(int tps) {
        return this.tps==tps;
    }

    protected abstract void tick();
}
