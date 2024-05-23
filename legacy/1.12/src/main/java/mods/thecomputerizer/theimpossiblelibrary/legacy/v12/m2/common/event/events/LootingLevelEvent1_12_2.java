package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LootingLevelEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Damage1_12_2;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_LOOTING_LEVEL;

public class LootingLevelEvent1_12_2 extends LootingLevelEventWrapper<LootingLevelEvent> {

    @SubscribeEvent
    public static void onEvent(LootingLevelEvent event) {
        LIVING_LOOTING_LEVEL.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LootingLevelEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<LootingLevelEvent,Integer> wrapLootingLevelField() {
        return wrapGenericBoth(LootingLevelEvent::getLootingLevel,LootingLevelEvent::setLootingLevel,1);
    }

    @Override
    protected EventFieldWrapper<LootingLevelEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_12_2(event.getDamageSource(),1f),null);
    }

    @Override
    protected EventFieldWrapper<LootingLevelEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LootingLevelEvent::getEntityLiving);
    }
}