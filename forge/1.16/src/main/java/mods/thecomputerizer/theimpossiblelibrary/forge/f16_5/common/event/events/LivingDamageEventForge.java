package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDamageEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.DamageForge;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.function.Function;

public class LivingDamageEventForge extends LivingDamageEventWrapper<LivingDamageEvent> {

    @Override
    protected Function<LivingDamageEvent,LivingEntity> getLivingFunc() {
        return LivingDamageEvent::getEntityLiving;
    }

    @Override
    public void populate() {
        super.populate();
        this.damage = new DamageForge(this.event.getSource(),this.event.getAmount());
    }

    @Override
    public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.setAmount(amount);
    }
}
