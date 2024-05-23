package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Damage1_12_2;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DEATH;

public class LivingDeathEvent1_12_2 extends LivingDeathEventWrapper<LivingDeathEvent> {

    @SubscribeEvent
    public static void onEvent(LivingDeathEvent event) {
        LIVING_DEATH.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingDeathEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<LivingDeathEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_12_2(event.getSource(),1f),null);
    }

    @Override
    protected EventFieldWrapper<LivingDeathEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingDeathEvent::getEntityLiving);
    }
}
