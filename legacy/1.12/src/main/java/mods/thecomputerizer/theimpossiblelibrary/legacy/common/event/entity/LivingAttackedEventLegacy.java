package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingAttackedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.DamageLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.LivingLegacy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class LivingAttackedEventLegacy extends LivingAttackedEventWrapper<EntityLivingBase> {

    private final LivingAttackEvent event;

    public LivingAttackedEventLegacy(LivingAttackEvent event) {
        super(new LivingLegacy(event.getEntityLiving()),new DamageLegacy(event.getSource(),event.getAmount()));
        this.event = event;
    }
}
