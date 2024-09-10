package mods.thecomputerizer.theimpossiblelibrary.api.util;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@SuppressWarnings("unused") @Getter
public class CustomTick {

    private static final List<CustomTick> registeredTickEvents = new ArrayList<>();

    private static void addCustomTick(final CustomTick ticker) {
        CommonEventsAPI api = EventHelper.getEventsAPI(false);
        if(Objects.isNull(api) || isRegistered(ticker)) return;
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            try {
                api.postCustomTick(ticker);
            } catch(Throwable t) {
                TILRef.logError("Failed to post custom tick",t);
            }
        },0,ticker.millis,MILLISECONDS);
        registeredTickEvents.add(ticker);
    }

    public static void addCustomTickMillis(long millis) {
        if(millis<=0) millis = 1;
        if(millis>1000) millis = 1000;
        addCustomTick(new CustomTick(millis));
    }

    public static void addCustomTickTPS(int tps) {
        if(tps<=0) tps = 1;
        if(tps>1000) tps = 1000;
        addCustomTick(new CustomTick(tps));
    }

    public static boolean isRegistered(CustomTick ticker) {
        return registeredTickEvents.contains(ticker);
    }

    public static boolean isRegistered(long millis) {
        return isRegistered(new CustomTick(millis));
    }

    public static boolean isRegistered(int ticks) {
        return isRegistered(new CustomTick(ticks));
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CustomTick) return this.millis==((CustomTick)obj).millis;
        return obj instanceof Number && (obj instanceof Long ? this.millis == (Long) obj :
                this.tps==((Number)obj).intValue());
    }

    public boolean isEquivalentMillis(long millis) {
        return this.millis==millis;
    }

    public boolean isEquivalentTPS(int tps) {
        return this.tps==tps;
    }
}
