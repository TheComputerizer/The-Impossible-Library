package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerAdvancementEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent.AdvancementEarnEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

public class PlayerAdvancementEventNeoForge extends PlayerAdvancementEventWrapper<AdvancementEarnEvent> {
    
    @SubscribeEvent
    public static void onEvent(AdvancementEarnEvent event) {
        PLAYER_ADVANCEMENT.invoke(event);
    }
    
    @Override public void setEvent(AdvancementEarnEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<AdvancementEarnEvent,AdvancementAPI<?>> wrapAdvancementField() {
        return wrapAdvancementGetter(AdvancementEarnEvent::getAdvancement);
    }

    @Override protected EventFieldWrapper<AdvancementEarnEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(AdvancementEarnEvent::getEntity);
    }
}