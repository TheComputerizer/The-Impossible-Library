package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingAttackedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.DamageForge;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.function.Function;

public class LivingAttackedEventForge extends LivingAttackedEventWrapper<LivingAttackEvent> {

    @Override
    protected Function<LivingAttackEvent,LivingEntity> getLivingFunc() {
        return LivingAttackEvent::getEntityLiving;
    }

    @Override
    public void populate() {
        super.populate();
        this.damage = new DamageForge(this.event.getSource(),this.event.getAmount());
    }
}
