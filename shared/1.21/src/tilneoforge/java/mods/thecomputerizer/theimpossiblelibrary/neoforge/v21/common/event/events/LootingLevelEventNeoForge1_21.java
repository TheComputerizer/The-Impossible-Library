package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LootingLevelEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Damage1_21;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_LOOTING_LEVEL;

public class LootingLevelEventNeoForge1_21 extends LootingLevelEventNeoForge<LivingDropsEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingDropsEvent event) {
        LIVING_LOOTING_LEVEL.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingDropsEvent,DamageAPI<?>> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_21(event.getSource(),1f),null);
    }
    
    @Override protected EventFieldWrapper<LivingDropsEvent,Integer> wrapLootingLevelField() {
        return wrapGenericBoth(event -> 0,(event,level) -> {},1);
    }
}