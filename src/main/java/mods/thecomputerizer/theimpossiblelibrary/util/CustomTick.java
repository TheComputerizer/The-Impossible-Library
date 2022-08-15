package mods.thecomputerizer.theimpossiblelibrary.util;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomTick extends Event {

    public static void addCustomTickEvent(int ticksPerSecond) {
        Runnable tickTimer = () -> MinecraftForge.EVENT_BUS.post(new CustomTick(ticksPerSecond));
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(tickTimer, 0, (long)(1000f/ticksPerSecond), TimeUnit.MILLISECONDS);
    }

    private final int tickRate;
    private CustomTick(int tickRate){
        this.tickRate = tickRate;
    }

    public boolean checkTickRate(int tickRate) {
        return this.tickRate==tickRate;
    }
}
