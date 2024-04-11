package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.PortalSize;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.SpawnPortalEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.PortalSpawnEvent;

public class SpawnPortalEvent1_16_5 extends SpawnPortalEventWrapper<PortalSpawnEvent> {

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