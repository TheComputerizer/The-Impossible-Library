package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LootingLevelEventWrapper;
import net.neoforged.neoforge.event.entity.living.LootingLevelEvent;

public abstract class LootingLevelEventNeoForge extends LootingLevelEventWrapper<LootingLevelEvent> {
    
    @Override public void setEvent(LootingLevelEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<LootingLevelEvent,Integer> wrapLootingLevelField() {
        return wrapGenericBoth(LootingLevelEvent::getLootingLevel,LootingLevelEvent::setLootingLevel,1);
    }
    
    @Override protected EventFieldWrapper<LootingLevelEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LootingLevelEvent::getEntity);
    }
}