package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.CommonEventsForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.events.RegisterBlockEntitiesEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.events.RegisterBlocksEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.events.RegisterCommandsEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.events.RegisterEntitiesEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.events.RegisterItemsEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.events.RegisterSoundsEventForge1_20_6;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_SOUNDS;

public class CommonEventsForge1_20_6 extends CommonEventsForge1_20 {
    
    @Override public void defineVersionedEvents() {
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEventForge1_20_6());
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEventForge1_20_6());
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventForge1_20_6());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEventForge1_20_6());
        REGISTER_ITEMS.setConnector(new RegisterItemsEventForge1_20_6());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEventForge1_20_6());
    }
}