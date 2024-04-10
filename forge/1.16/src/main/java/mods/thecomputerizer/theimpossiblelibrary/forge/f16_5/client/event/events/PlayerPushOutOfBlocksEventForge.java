package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPushOutOfBlocksEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;

public class PlayerPushOutOfBlocksEventForge extends PlayerPushOutOfBlocksEventWrapper<Object> { //TODO

    @Override
    protected Box wrapEntityBB() {
        return Box.ZERO;
    }

    @Override
    protected EventFieldWrapper<Object,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }
}