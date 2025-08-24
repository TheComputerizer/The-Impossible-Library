package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.LootingLevelEventForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.entity.Damage1_19;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_LOOTING_LEVEL;

public class LootingLevelEventForge1_19 extends LootingLevelEventForge {
    
    @SubscribeEvent
    public static void onEvent(LootingLevelEvent event) {
        LIVING_LOOTING_LEVEL.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LootingLevelEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_19(event.getDamageSource(),1f),null);
    }
}