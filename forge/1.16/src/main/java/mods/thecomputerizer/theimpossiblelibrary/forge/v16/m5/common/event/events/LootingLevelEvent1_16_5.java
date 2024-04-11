package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LootingLevelEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.entity.Damage1_16_5;
import net.minecraftforge.event.entity.living.LootingLevelEvent;

public class LootingLevelEvent1_16_5 extends LootingLevelEventWrapper<LootingLevelEvent> {

    @Override
    protected EventFieldWrapper<LootingLevelEvent,Integer> wrapLootingLevelField() {
        return wrapGenericBoth(LootingLevelEvent::getLootingLevel,LootingLevelEvent::setLootingLevel,1);
    }

    @Override
    protected EventFieldWrapper<LootingLevelEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_16_5(event.getDamageSource(),1f),null);
    }

    @Override
    protected EventFieldWrapper<LootingLevelEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LootingLevelEvent::getEntityLiving);
    }
}