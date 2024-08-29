package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSleepInBedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SLEEP_IN_BED;

public class PlayerSleepInBedEventForge extends PlayerSleepInBedEventWrapper<PlayerSleepInBedEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlayerSleepInBedEvent event) {
        PLAYER_SLEEP_IN_BED.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayerSleepInBedEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<PlayerSleepInBedEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerSleepInBedEvent::getPlayer);
    }

    @Override protected EventFieldWrapper<PlayerSleepInBedEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PlayerSleepInBedEvent::getPos);
    }
}