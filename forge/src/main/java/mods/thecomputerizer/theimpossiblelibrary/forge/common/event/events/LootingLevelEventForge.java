package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LootingLevelEventWrapper;
import net.minecraftforge.event.entity.living.LootingLevelEvent;

public abstract class LootingLevelEventForge extends LootingLevelEventWrapper<LootingLevelEvent> {
    
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
    protected EventFieldWrapper<LootingLevelEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LootingLevelEvent::getEntityLiving);
    }
}