package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlockEntitiesEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.RegistryEventFabric1_16_5;
import net.fabricmc.fabric.api.event.Event;

public class RegisterBlockEntitiesEventFabric1_16_5 extends RegisterBlockEntitiesEventFabric implements RegistryEventFabric1_16_5 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        register("block_entity",entry);
    }
}