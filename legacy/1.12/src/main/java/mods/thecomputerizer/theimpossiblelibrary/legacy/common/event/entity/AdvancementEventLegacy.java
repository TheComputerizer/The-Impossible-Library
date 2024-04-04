package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.AdvancementEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.advancement.AdvancementLegacy;
import net.minecraft.advancements.Advancement;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

public class AdvancementEventLegacy extends AdvancementEventWrapper<AdvancementEvent,Advancement> {

    @SubscribeEvent
    public static void onEvent(AdvancementEvent event) {
        PLAYER_ADVANCEMENT.invoke(event);
    }

    public AdvancementEventLegacy() {}

    public void setEvent(AdvancementEvent event) {
        this.advancement = new AdvancementLegacy(event.getAdvancement());
    }
}
