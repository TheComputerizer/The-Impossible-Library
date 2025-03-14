package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.CommonEventsForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.event.events.RegisterBlockEntitiesEventForge1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.event.events.RegisterBlocksEventForge1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.event.events.RegisterEntitiesEventForge1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.event.events.RegisterItemsEventForge1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.event.events.RegisterSoundsEventForge1_19_4;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_SOUNDS;

public class CommonEventsForge1_19_4 extends CommonEventsForge1_19 {
    
    @Override protected void defineRegistryEvents() {
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEventForge1_19_4());
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEventForge1_19_4());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEventForge1_19_4());
        REGISTER_ITEMS.setConnector(new RegisterItemsEventForge1_19_4());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEventForge1_19_4());
    }
}