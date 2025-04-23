package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PlayerAdvancementEventForge;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

public class PlayerAdvancementEventForge1_18_2 extends PlayerAdvancementEventForge<AdvancementEvent> {
    
    @SubscribeEvent
    public static void onEvent(AdvancementEvent event) {
        PLAYER_ADVANCEMENT.invoke(event);
    }
    
    @Override protected EventFieldWrapper<AdvancementEvent,AdvancementAPI<?>> wrapAdvancementField() {
        return wrapAdvancementGetter(AdvancementEvent::getAdvancement);
    }
    
    @Override protected EventFieldWrapper<AdvancementEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(AdvancementEvent::getEntity);
    }
}