package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingKnockbackEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class LivingKnockbackEventFabric extends LivingKnockbackEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapAttackerField() {
        return wrapEntityBoth(event -> null,(event,attacker) -> {});
    }

    @Override protected EventFieldWrapper<Object[],LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingKnockBackEvent::getEntityLiving);
    }

    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapOriginalAttackerField() {
        return wrapEntityGetter(event -> null);
    }

    @Override protected EventFieldWrapper<Object[],Double> wrapOriginalRatioXField() {
        return wrapGenericGetter(LivingKnockBackEvent::getOriginalRatioX,0d);
    }

    @Override protected EventFieldWrapper<Object[],Double> wrapOriginalRatioZField() {
        return wrapGenericGetter(LivingKnockBackEvent::getOriginalRatioZ,0d);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapOriginalStrengthField() {
        return wrapGenericGetter(LivingKnockBackEvent::getStrength,0f);
    }

    @Override protected EventFieldWrapper<Object[],Double> wrapRatioXField() {
        return wrapGenericBoth(LivingKnockBackEvent::getRatioX,LivingKnockBackEvent::setRatioX,0d);
    }

    @Override protected EventFieldWrapper<Object[],Double> wrapRatioZField() {
        return wrapGenericBoth(LivingKnockBackEvent::getRatioZ,LivingKnockBackEvent::setRatioZ,0d);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapStrengthField() {
        return wrapGenericBoth(LivingKnockBackEvent::getStrength,LivingKnockBackEvent::setStrength,0f);
    }
}