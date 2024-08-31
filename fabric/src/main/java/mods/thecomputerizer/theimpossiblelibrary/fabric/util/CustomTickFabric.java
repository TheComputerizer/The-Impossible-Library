package mods.thecomputerizer.theimpossiblelibrary.fabric.util;

import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface CustomTickFabric {
    
    Event<CustomTickFabric> CUSTOM_TICK = EventFactory.createArrayBacked(
            CustomTickFabric.class,listeners -> ticker -> {
                for(CustomTickFabric listener : listeners) listener.onTick(ticker);
            });
    
    void onTick(CustomTick ticker);
}