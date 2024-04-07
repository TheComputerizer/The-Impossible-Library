package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPunchEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;

public class PlayerPunchEmptyEventLegacy extends PlayerPunchEmptyEventWrapper<LeftClickEmpty> {

    @Override
    protected EventFieldWrapper<LeftClickEmpty,PlayerAPI<?>> wrapPlayerField() {
        return getFieldWrapperGetter(event -> wrapPlayer(LeftClickEmpty::getEntityPlayer),null);
    }
}