package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.PortalSize;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.SpawnPortalEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.PortalSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_SPAWN_PORTAL;

public class SpawnPortalEvent1_12_2 extends SpawnPortalEventWrapper<PortalSpawnEvent> {

    @SubscribeEvent
    public static void onEvent(PortalSpawnEvent event) {
        BLOCK_SPAWN_PORTAL.invoke(event);
    }

    @Override
    protected EventFieldWrapper<PortalSpawnEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PortalSpawnEvent::getPos);
    }

    @Override
    protected EventFieldWrapper<PortalSpawnEvent,PortalSize> wrapSizeField() { //TODO Implement this
        return wrapGenericGetter(event -> null,null);
    }

    @Override
    protected EventFieldWrapper<PortalSpawnEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(PortalSpawnEvent::getState);
    }

    @Override
    protected EventFieldWrapper<PortalSpawnEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(PortalSpawnEvent::getWorld);
    }
}