package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.DamageLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.LivingLegacy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class LivingDeathEventLegacy extends LivingDeathEventWrapper<EntityLivingBase> {

    private final LivingDeathEvent event;

    public LivingDeathEventLegacy(LivingDeathEvent event) {
        super(new LivingLegacy(event.getEntityLiving()),new DamageLegacy(event.getSource(),1f));
        this.event = event;
    }
}
