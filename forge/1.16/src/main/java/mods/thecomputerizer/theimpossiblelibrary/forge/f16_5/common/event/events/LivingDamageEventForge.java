package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDamageEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.entity.DamageForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class LivingDamageEventForge extends LivingDamageEventWrapper<LivingDamageEvent> {

    @Override
    public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.set(this.event,new DamageForge(this.event.getSource(),amount));
    }

    @Override
    protected EventFieldWrapper<LivingDamageEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new DamageForge(event.getSource(),this.event.getAmount()),null);
    }

    @Override
    protected EventFieldWrapper<LivingDamageEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingDamageEvent::getEntityLiving);
    }
}
