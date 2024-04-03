package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.AdvancementEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.advancement.AdvancementLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.PlayerLegacy;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AdvancementEvent;

public class AdvancementEventLegacy extends AdvancementEventWrapper<EntityPlayer,Advancement> {

    private final AdvancementEvent event;

    public AdvancementEventLegacy(AdvancementEvent event) {
        super(new PlayerLegacy(event.getEntityPlayer()),new AdvancementLegacy(event.getAdvancement()));
        this.event = event;
    }
}
