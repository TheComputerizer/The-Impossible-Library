package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerAdvancementEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

public class PlayerAdvancementEventNeoForge extends PlayerAdvancementEventWrapper<AdvancementEvent> {
    
    @SubscribeEvent
    public static void onEvent(AdvancementEvent event) {
        PLAYER_ADVANCEMENT.invoke(event);
    }
    
    @Override public void setEvent(AdvancementEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<AdvancementEvent,AdvancementAPI<?>> wrapAdvancementField() {
        return wrapAdvancementGetter(AdvancementEvent::getAdvancement);
    }

    @Override protected EventFieldWrapper<AdvancementEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(AdvancementEvent::getEntity);
    }
}