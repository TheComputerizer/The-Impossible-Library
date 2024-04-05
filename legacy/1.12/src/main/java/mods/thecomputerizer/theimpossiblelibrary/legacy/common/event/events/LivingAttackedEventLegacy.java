package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingAttackedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.DamageLegacy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

public class LivingAttackedEventLegacy extends LivingAttackedEventWrapper<LivingAttackEvent> {

    @SubscribeEvent
    public static void onEvent(LivingAttackEvent event) {
        LIVING_ATTACKED.invoke(event);
    }

    @Override
    protected Function<LivingAttackEvent,EntityLivingBase> getLivingFunc() {
        return LivingAttackEvent::getEntityLiving;
    }

    @Override
    public void populate() {
        super.populate();
        this.damage = new DamageLegacy(this.event.getSource(),this.event.getAmount());
    }
}
