package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingAttackedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Damage1_12_2;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

public class LivingAttackedEvent1_12_2 extends LivingAttackedEventWrapper<LivingAttackEvent> {

    @SubscribeEvent
    public static void onEvent(LivingAttackEvent event) {
        LIVING_ATTACKED.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingAttackEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<LivingAttackEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_12_2(event.getSource(),1f),null);
    }

    @Override protected EventFieldWrapper<LivingAttackEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingAttackEvent::getEntityLiving);
    }
}