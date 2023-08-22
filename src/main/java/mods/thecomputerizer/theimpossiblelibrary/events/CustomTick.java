package mods.thecomputerizer.theimpossiblelibrary.events;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CustomTick {

    private static final Map<Long,CustomTick> REGISTERED_TICKERS = new HashMap<>();

    /**
     * Adds a CustomTickEvent that fires every input number of times per second. The default vanilla tick rate is 20.
     */
    public static void registerTickRate(int tickRate,Supplier<Long> eventSupplier) {
        registerMillis((long)(1000f/tickRate),eventSupplier);
    }

    /**
     * Adds a CustomTickEvent that fires every input millisecond inverval. The default vanilla tick speed is 50 ms.
     */
    public static void registerMillis(long millis,Supplier<Long> eventSupplier) {
        if(Objects.isNull(eventSupplier)) throw new RuntimeException("Cannot make a CustomTickEvent with a null supplier!");
        REGISTERED_TICKERS.getOrDefault(millis,makeNewTimer(millis)).addSupplier(eventSupplier);
    }

    private static CustomTick makeNewTimer(long millis) {
        if(millis>0) {
            CustomTick ticker = new CustomTick(millis);
            REGISTERED_TICKERS.put(millis,ticker);
            return ticker;
        } else throw new RuntimeException("CustomTickEvent interval cannot be less than or equal to 0 or greater " +
                "than 1000 ["+millis+"]!");
    }

    private final List<Supplier<Long>> registeredSuppliers = Collections.synchronizedList(new ArrayList<>());
    private CustomTick(long millis) {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this::run,0,millis,TimeUnit.MILLISECONDS);
    }

    private void addSupplier(Supplier<Long> eventSupplier) {
        synchronized (this.registeredSuppliers) {
            this.registeredSuppliers.add(eventSupplier);
        }
    }

    private void run() {
        synchronized (this.registeredSuppliers) {
            for(Supplier<Long> supplier : this.registeredSuppliers)
                supplier.get();
        }
    }
}
