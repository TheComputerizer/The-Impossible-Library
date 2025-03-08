package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlockEntitiesEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.RegistryEventFabric1_20;
import net.fabricmc.fabric.api.event.Event;

public class RegisterBlockEntitiesEventFabric1_20 extends RegisterBlockEntitiesEventFabric implements RegistryEventFabric1_20 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        register("block_entity",entry);
    }
}