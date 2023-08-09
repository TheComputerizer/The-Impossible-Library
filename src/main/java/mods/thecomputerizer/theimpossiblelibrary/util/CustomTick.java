package mods.thecomputerizer.theimpossiblelibrary.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CustomTick {

    private static final HashMap<Long, CustomTick> registeredTickEvents = new HashMap<>();

    public static CustomTick getOrCreateCustomTickEvent(long millis) {
        if(isRegistered(millis)) return registeredTickEvents.get(millis);
        CustomTick tick = new CustomTick();
        Runnable tickTimer = tick::run;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(tickTimer, 0, millis, TimeUnit.MILLISECONDS);
        registeredTickEvents.put(millis,tick);
        return tick;
    }

    public static CustomTick getOrCreateCustomTickEvent(int ticksPerSecond) {
        long millis = (long)(1000f/ticksPerSecond);
        if(isRegistered(millis)) return registeredTickEvents.get(millis);
        CustomTick tick = new CustomTick();
        Runnable tickTimer = tick::run;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(tickTimer, 0, millis, TimeUnit.MILLISECONDS);
        registeredTickEvents.put(millis,tick);
        return tick;
    }

    public static boolean isRegistered(long millis) {
        return registeredTickEvents.containsKey(millis);
    }

    private final List<Supplier<Void>> runnable;
    private CustomTick(){
        this.runnable = new ArrayList<>();
    }

    public void addRunnable(Supplier<Void> supplierFunction) {
        this.runnable.add(supplierFunction);
    }

    private void run() {
        for(Supplier<Void> supplier : this.runnable)
            supplier.get();
    }
}
