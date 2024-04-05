package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.DamageForge;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.function.Function;

public class LivingDeathEventForge extends LivingDeathEventWrapper<LivingDeathEvent> {

    @Override
    protected Function<LivingDeathEvent,LivingEntity> getLivingFunc() {
        return LivingDeathEvent::getEntityLiving;
    }

    @Override
    public void populate() {
        super.populate();
        this.damage = new DamageForge(this.event.getSource(),1f);
    }
}
