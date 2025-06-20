package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LootingLevelEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.entity.Damage1_20;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LootingLevelEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_LOOTING_LEVEL;

public class LootingLevelEventNeoForge1_20_4 extends LootingLevelEventNeoForge<LootingLevelEvent> {
    
    @SubscribeEvent
    public static void onEvent(LootingLevelEvent event) {
        LIVING_LOOTING_LEVEL.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LootingLevelEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_20(event.getDamageSource(),1f),null);
    }
    
    @Override protected EventFieldWrapper<LootingLevelEvent,Integer> wrapLootingLevelField() {
        return wrapGenericBoth(LootingLevelEvent::getLootingLevel,LootingLevelEvent::setLootingLevel,1);
    }
}