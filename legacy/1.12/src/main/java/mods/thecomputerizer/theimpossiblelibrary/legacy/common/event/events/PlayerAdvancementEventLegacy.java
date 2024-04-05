package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerAdvancementEventWrapper;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

public class PlayerAdvancementEventLegacy extends PlayerAdvancementEventWrapper<AdvancementEvent> {

    @SubscribeEvent
    public static void onEvent(AdvancementEvent event) {
        PLAYER_ADVANCEMENT.invoke(event);
    }

    @Override
    protected Function<AdvancementEvent,Advancement> getAdvancementFunc() {
        return AdvancementEvent::getAdvancement;
    }

    @Override
    protected Function<AdvancementEvent,EntityPlayer> getPlayerFunc() {
        return AdvancementEvent::getEntityPlayer;
    }
}