package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.AdvancementEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.advancement.AdvancementForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.PlayerForge;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.AdvancementEvent;

public class AdvancementEventForge extends AdvancementEventWrapper<PlayerEntity,Advancement> {

    private final AdvancementEvent event;

    public AdvancementEventForge(AdvancementEvent event) {
        super(new PlayerForge(event.getPlayer()),new AdvancementForge(event.getAdvancement()));
        this.event = event;
    }
}
