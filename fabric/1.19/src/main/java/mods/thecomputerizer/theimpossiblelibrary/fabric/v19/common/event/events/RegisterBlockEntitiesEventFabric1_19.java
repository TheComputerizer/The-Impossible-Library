package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlockEntitiesEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.RegistryEventFabric1_19;
import net.fabricmc.fabric.api.event.Event;

public class RegisterBlockEntitiesEventFabric1_19 extends RegisterBlockEntitiesEventFabric implements RegistryEventFabric1_19 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        register("block_entity",entry);
    }
}