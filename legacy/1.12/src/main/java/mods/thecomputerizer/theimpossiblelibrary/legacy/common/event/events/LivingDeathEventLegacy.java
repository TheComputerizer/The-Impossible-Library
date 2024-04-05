package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.DamageLegacy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DEATH;

public class LivingDeathEventLegacy extends LivingDeathEventWrapper<LivingDeathEvent> {

    @SubscribeEvent
    public static void onEvent(LivingDeathEvent event) {
        LIVING_DEATH.invoke(event);
    }

    @Override
    protected Function<LivingDeathEvent,EntityLivingBase> getLivingFunc() {
        return LivingDeathEvent::getEntityLiving;
    }

    @Override
    public void populate() {
        super.populate();
        this.damage = new DamageLegacy(this.event.getSource(),1f);
    }
}
