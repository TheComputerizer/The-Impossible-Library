package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingDamageEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.DamageLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.LivingLegacy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class LivingDamageEventLegacy extends LivingDamageEventWrapper<EntityLivingBase> {
    private final LivingDamageEvent event;

    public LivingDamageEventLegacy(LivingDamageEvent event) {
        super(new LivingLegacy(event.getEntityLiving()),new DamageLegacy(event.getSource(),event.getAmount()));
        this.event = event;
    }

    @Override
    protected void setAmountInner(float amount) {
        this.getDamage().setAmount(amount);
        this.event.setAmount(amount);
    }
}
