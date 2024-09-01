package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlocksEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.RegistryEventFabric1_16_5;
import net.fabricmc.fabric.api.event.Event;

public class RegisterBlocksEventFabric1_16_5 extends RegisterBlocksEventFabric implements RegistryEventFabric1_16_5 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(BlockAPI<?> entry) {
        register("block",entry);
    }
}
