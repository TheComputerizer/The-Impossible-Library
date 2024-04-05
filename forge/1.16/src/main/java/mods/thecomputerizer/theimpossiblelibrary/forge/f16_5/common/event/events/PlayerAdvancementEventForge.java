package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerAdvancementEventWrapper;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.AdvancementEvent;

import java.util.function.Function;

public class PlayerAdvancementEventForge extends PlayerAdvancementEventWrapper<AdvancementEvent> {

    @Override
    protected Function<AdvancementEvent,Advancement> getAdvancementFunc() {
        return AdvancementEvent::getAdvancement;
    }

    @Override
    protected Function<AdvancementEvent,PlayerEntity> getPlayerFunc() {
        return AdvancementEvent::getPlayer;
    }
}
