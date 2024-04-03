package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingDamageEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.DamageForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.LivingForge;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class LivingDamageEventForge extends LivingDamageEventWrapper<LivingEntity> {
    private final LivingDamageEvent event;

    public LivingDamageEventForge(LivingDamageEvent event) {
        super(new LivingForge(event.getEntityLiving()),new DamageForge(event.getSource(),event.getAmount()));
        this.event = event;
    }

    @Override
    protected void setAmountInner(float amount) {
        this.getDamage().setAmount(amount);
        this.event.setAmount(amount);
    }
}
