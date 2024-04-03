package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.DamageForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.LivingForge;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class LivingDeathEventForge extends LivingDeathEventWrapper<LivingEntity> {

    private final LivingDeathEvent event;

    public LivingDeathEventForge(LivingDeathEvent event) {
        super(new LivingForge(event.getEntityLiving()),new DamageForge(event.getSource(),1f));
        this.event = event;
    }
}
