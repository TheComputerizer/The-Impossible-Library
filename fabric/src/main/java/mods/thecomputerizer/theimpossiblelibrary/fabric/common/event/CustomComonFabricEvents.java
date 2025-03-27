package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Registry;

public interface CustomComonFabricEvents {
    
    Event<CustomTickFabric> CUSTOM_TICK = EventFactory.createArrayBacked(
            CustomTickFabric.class,listeners -> ticker -> {
                for(CustomTickFabric listener : listeners) listener.onTick(ticker);
            });
    Event<RegistryEvent> REGISTER_BLOCK_ENTITIES = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    Event<RegistryEvent> REGISTER_BLOCKS = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    Event<RegistryEvent> REGISTER_ENTITIES = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    Event<RegistryEvent> REGISTER_ITEMS = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    Event<RegistryEvent> REGISTER_SOUND_EVENTS = EventFactory.createArrayBacked(
            RegistryEvent.class,listeners -> registry -> {
                for(RegistryEvent listener: listeners) listener.register(registry);
            }
    );
    
    interface CustomTickFabric { void onTick(CustomTick ticker); }
    
    //Fabric doesn't use registry events, but this is still needed to keep the event wrappers happy
    interface RegistryEvent { void register(Registry<?> registry); }
}
