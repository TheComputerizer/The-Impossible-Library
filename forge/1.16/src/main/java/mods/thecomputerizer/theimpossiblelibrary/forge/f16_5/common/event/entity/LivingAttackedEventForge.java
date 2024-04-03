package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingAttackedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.DamageForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.LivingForge;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class LivingAttackedEventForge extends LivingAttackedEventWrapper<LivingEntity> {

    private final LivingAttackEvent event;

    public LivingAttackedEventForge(LivingAttackEvent event) {
        super(new LivingForge(event.getEntityLiving()),new DamageForge(event.getSource(),event.getAmount()));
        this.event = event;
    }
}
