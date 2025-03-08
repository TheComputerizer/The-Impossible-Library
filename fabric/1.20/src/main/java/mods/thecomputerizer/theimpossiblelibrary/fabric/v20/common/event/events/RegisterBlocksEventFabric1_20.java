package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlocksEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.RegistryEventFabric1_20;
import net.fabricmc.fabric.api.event.Event;

public class RegisterBlocksEventFabric1_20 extends RegisterBlocksEventFabric implements RegistryEventFabric1_20 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(BlockAPI<?> entry) {
        register("block",entry);
    }
}