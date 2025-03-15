package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlockEntitiesEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.RegistryEventFabric;
import net.fabricmc.fabric.api.event.Event;

import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomFabricEvents.REGISTER_BLOCK_ENTITIES;

public abstract class RegisterBlockEntitiesEventFabric extends RegisterBlockEntitiesEventWrapper<Object[]> implements RegistryEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return REGISTER_BLOCK_ENTITIES;
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        TILRef.logInfo("REGISTERING BLOCK ENTITY ENTRY");
        registerEntry(entry);
    }
}