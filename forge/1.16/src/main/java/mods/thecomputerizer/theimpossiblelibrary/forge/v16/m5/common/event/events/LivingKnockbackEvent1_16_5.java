package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingKnockbackEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class LivingKnockbackEvent1_16_5 extends LivingKnockbackEventWrapper<LivingKnockBackEvent> {

    @Override
    protected EventFieldWrapper<LivingKnockBackEvent,EntityAPI<?>> wrapAttackerField() {
        return wrapEntityBoth(event -> null,(event,attacker) -> {});
    }

    @Override
    protected EventFieldWrapper<LivingKnockBackEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingKnockBackEvent::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<LivingKnockBackEvent,EntityAPI<?>> wrapOriginalAttackerField() {
        return wrapEntityGetter(event -> null);
    }

    @Override
    protected EventFieldWrapper<LivingKnockBackEvent,Double> wrapOriginalRatioXField() {
        return wrapGenericGetter(LivingKnockBackEvent::getOriginalRatioX,0d);
    }

    @Override
    protected EventFieldWrapper<LivingKnockBackEvent,Double> wrapOriginalRatioZField() {
        return wrapGenericGetter(LivingKnockBackEvent::getOriginalRatioZ,0d);
    }

    @Override
    protected EventFieldWrapper<LivingKnockBackEvent,Float> wrapOriginalStrengthField() {
        return wrapGenericGetter(LivingKnockBackEvent::getStrength,0f);
    }

    @Override
    protected EventFieldWrapper<LivingKnockBackEvent,Double> wrapRatioXField() {
        return wrapGenericBoth(LivingKnockBackEvent::getRatioX,LivingKnockBackEvent::setRatioX,0d);
    }

    @Override
    protected EventFieldWrapper<LivingKnockBackEvent,Double> wrapRatioZField() {
        return wrapGenericBoth(LivingKnockBackEvent::getRatioZ,LivingKnockBackEvent::setRatioZ,0d);
    }

    @Override
    protected EventFieldWrapper<LivingKnockBackEvent,Float> wrapStrengthField() {
        return wrapGenericBoth(LivingKnockBackEvent::getStrength,LivingKnockBackEvent::setStrength,0f);
    }
}