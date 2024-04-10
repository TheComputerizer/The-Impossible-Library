package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSleepInBedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public class PlayerSleepInBedEventForge extends PlayerSleepInBedEventWrapper<PlayerSleepInBedEvent> {

    @Override
    protected EventFieldWrapper<PlayerSleepInBedEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerSleepInBedEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<PlayerSleepInBedEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PlayerSleepInBedEvent::getPos);
    }
}