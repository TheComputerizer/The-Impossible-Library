package mods.thecomputerizer.theimpossiblelibrary.forge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.PortalSize;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.SpawnPortalEventForge;
import net.minecraftforge.event.level.BlockEvent.PortalSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_SPAWN_PORTAL;

public class SpawnPortalEventForge1_21 extends SpawnPortalEventForge<PortalSpawnEvent> {
    
    @SubscribeEvent
    public static void onEvent(PortalSpawnEvent event) {
        BLOCK_SPAWN_PORTAL.invoke(event);
    }
    
    @Override protected EventFieldWrapper<PortalSpawnEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PortalSpawnEvent::getPos);
    }

    @Override protected EventFieldWrapper<PortalSpawnEvent,PortalSize> wrapSizeField() { //TODO Implement this
        return wrapGenericGetter(event -> null,null);
    }

    @Override protected EventFieldWrapper<PortalSpawnEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(PortalSpawnEvent::getState);
    }

    @Override protected EventFieldWrapper<PortalSpawnEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(PortalSpawnEvent::getLevel);
    }
}