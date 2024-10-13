package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlockEntitiesEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.RegistryEventFabric1_18_2;
import net.fabricmc.fabric.api.event.Event;

public class RegisterBlockEntitiesEventFabric1_18_2 extends RegisterBlockEntitiesEventFabric implements RegistryEventFabric1_18_2 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        register("block_entity",entry);
    }
}