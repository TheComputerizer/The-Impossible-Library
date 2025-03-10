package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSleepInBedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSleepInBedEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SLEEP_IN_BED;

public class PlayerSleepInBedEventNeoForge extends PlayerSleepInBedEventWrapper<PlayerSleepInBedEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlayerSleepInBedEvent event) {
        PLAYER_SLEEP_IN_BED.invoke(event);
    }
    
    @Override public void setEvent(PlayerSleepInBedEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<PlayerSleepInBedEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerSleepInBedEvent::getEntity);
    }

    @Override protected EventFieldWrapper<PlayerSleepInBedEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PlayerSleepInBedEvent::getPos);
    }
}