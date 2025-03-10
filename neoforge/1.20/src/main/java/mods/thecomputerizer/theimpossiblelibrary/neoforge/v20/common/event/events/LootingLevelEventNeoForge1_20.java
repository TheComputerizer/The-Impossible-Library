package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LootingLevelEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.entity.Damage1_20;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LootingLevelEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_LOOTING_LEVEL;

public class LootingLevelEventNeoForge1_20 extends LootingLevelEventNeoForge {
    
    @SubscribeEvent
    public static void onEvent(LootingLevelEvent event) {
        LIVING_LOOTING_LEVEL.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LootingLevelEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_20(event.getDamageSource(),1f),null);
    }
}