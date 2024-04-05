package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDamageEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.DamageLegacy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

public class LivingDamageEventLegacy extends LivingDamageEventWrapper<LivingDamageEvent> {

    @SubscribeEvent
    public static void onEvent(LivingDamageEvent event) {
        LIVING_DAMAGE.invoke(event);
    }

    @Override
    protected Function<LivingDamageEvent,EntityLivingBase> getLivingFunc() {
        return LivingDamageEvent::getEntityLiving;
    }

    @Override
    public void populate() {
        super.populate();
        this.damage = new DamageLegacy(this.event.getSource(),this.event.getAmount());
    }

    @Override
    public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.setAmount(amount);
    }
}
