package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PlayerAdvancementEventForge;
import net.minecraftforge.event.entity.player.AdvancementEvent.AdvancementEarnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

public class PlayerAdvancementEventForge1_19 extends PlayerAdvancementEventForge<AdvancementEarnEvent> {
    
    @SubscribeEvent
    public static void onEvent(AdvancementEarnEvent event) {
        PLAYER_ADVANCEMENT.invoke(event);
    }
    
    @Override protected EventFieldWrapper<AdvancementEarnEvent,AdvancementAPI<?>> wrapAdvancementField() {
        return wrapAdvancementGetter(AdvancementEarnEvent::getAdvancement);
    }
}