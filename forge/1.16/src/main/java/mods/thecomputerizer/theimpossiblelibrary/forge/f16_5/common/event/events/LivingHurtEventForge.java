package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingHurtEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.entity.DamageForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingHurtEventForge extends LivingHurtEventWrapper<LivingHurtEvent> {

    @Override
    public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.set(this.event,new DamageForge(this.event.getSource(),amount));
    }

    @Override
    protected EventFieldWrapper<LivingHurtEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new DamageForge(event.getSource(),this.event.getAmount()),null);
    }

    @Override
    protected EventFieldWrapper<LivingHurtEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingHurtEvent::getEntityLiving);
    }
}