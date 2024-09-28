package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPushOutOfBlocksEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;

public class PlayerPushOutOfBlocksEventForge extends PlayerPushOutOfBlocksEventWrapper<Object> {

    @Override protected Box wrapEntityBB() {
        return Box.ZERO;
    }

    @Override protected EventFieldWrapper<Object,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }
}