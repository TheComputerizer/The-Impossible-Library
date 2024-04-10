package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerAdvancementEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

public class PlayerAdvancementEventLegacy extends PlayerAdvancementEventWrapper<AdvancementEvent> {

    @SubscribeEvent
    public static void onEvent(AdvancementEvent event) {
        PLAYER_ADVANCEMENT.invoke(event);
    }

    @Override
    protected EventFieldWrapper<AdvancementEvent,AdvancementAPI<?>> wrapAdvancementField() {
        return wrapAdvancementGetter(AdvancementEvent::getAdvancement);
    }

    @Override
    protected EventFieldWrapper<AdvancementEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(AdvancementEvent::getEntityPlayer);
    }
}