package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPushOutOfBlocksEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class PlayerPushOutOfBlocksEventFabric extends PlayerPushOutOfBlocksEventWrapper<Object[]> implements ClientFabricEvent {

    @Override protected Box wrapEntityBB() {
        return Box.ZERO;
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
}