package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface CustomFabricEvents {
    
    Event<CustomTickFabric> CUSTOM_TICK = EventFactory.createArrayBacked(
            CustomTickFabric.class,listeners -> ticker -> {
                for(CustomTickFabric listener : listeners) listener.onTick(ticker);
            });
    
    Event<KeyPressed> KEY_PRESSED = EventFactory.createArrayBacked(
            KeyPressed.class,listeners -> (key,scanCode,action,modifiers) -> {
                for(KeyPressed listener : listeners) listener.onKeyPressed(key,scanCode,action,modifiers);
            }
    );
    
    interface CustomTickFabric { void onTick(CustomTick ticker); }
    
    interface KeyPressed { void onKeyPressed(int key, int scanCode, int action, int modifiers); }
}