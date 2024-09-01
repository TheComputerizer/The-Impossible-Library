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
        return wrapLivingGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapOriginalAttackerField() {
        return wrapEntityGetter(event -> null);
    }

    @Override protected EventFieldWrapper<Object[],Double> wrapOriginalRatioXField() {
        return wrapGenericGetter(wrapArrayGetter(0),0d);
    }

    @Override protected EventFieldWrapper<Object[],Double> wrapOriginalRatioZField() {
        return wrapGenericGetter(wrapArrayGetter(0),0d);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapOriginalStrengthField() {
        return wrapGenericGetter(wrapArrayGetter(0),0f);
    }

    @Override protected EventFieldWrapper<Object[],Double> wrapRatioXField() {
        return wrapGenericBoth(wrapArrayGetter(0),(args,ratioX) -> {},0d);
    }

    @Override protected EventFieldWrapper<Object[],Double> wrapRatioZField() {
        return wrapGenericBoth(wrapArrayGetter(0),(args,ratioZ) -> {},0d);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapStrengthField() {
        return wrapGenericBoth(wrapArrayGetter(0),(args,strength) -> {},0f);
    }
}