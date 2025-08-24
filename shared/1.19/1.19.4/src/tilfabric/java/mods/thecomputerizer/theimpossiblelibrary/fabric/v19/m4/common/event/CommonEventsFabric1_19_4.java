package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event;

import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.CommonEventsFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.events.RegisterBlockEntitiesEventFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.events.RegisterBlocksEventFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.events.RegisterEntitiesEventFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.events.RegisterItemsEventFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.events.RegisterSoundEventsFabric1_19_4;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;

public class CommonEventsFabric1_19_4 extends CommonEventsFabric1_19 {
    
    @Override protected void defineRegistryEvents() {
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEventFabric1_19_4());
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEventFabric1_19_4());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEventFabric1_19_4());
        REGISTER_ITEMS.setConnector(new RegisterItemsEventFabric1_19_4());
        REGISTER_SOUNDS.setConnector(new RegisterSoundEventsFabric1_19_4());
    }
}
