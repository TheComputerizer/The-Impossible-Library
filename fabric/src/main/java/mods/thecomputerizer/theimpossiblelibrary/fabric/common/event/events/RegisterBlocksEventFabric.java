package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlocksEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.RegistryEventFabric;
import net.fabricmc.fabric.api.event.Event;

import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomFabricEvents.REGISTER_BLOCKS;

public abstract class RegisterBlocksEventFabric extends RegisterBlocksEventWrapper<Object[]> implements RegistryEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return REGISTER_BLOCKS;
    }
    
    @Override public void register(BlockAPI<?> entry) {
        TILRef.logInfo("REGISTERING BLOCK ENTRY");
        registerEntry(entry);
    }
}
