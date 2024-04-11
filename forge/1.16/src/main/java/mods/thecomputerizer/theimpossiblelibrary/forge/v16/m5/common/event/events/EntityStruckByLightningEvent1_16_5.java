package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityStruckByLightningEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;

public class EntityStruckByLightningEvent1_16_5 extends EntityStruckByLightningEventWrapper<EntityStruckByLightningEvent> {

    @Override
    protected EventFieldWrapper<EntityStruckByLightningEvent,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(EntityStruckByLightningEvent::getEntity);
    }

    @Override
    protected EventFieldWrapper<EntityStruckByLightningEvent,EntityAPI<?>> wrapLightningEntityField() {
        return wrapEntityGetter(EntityStruckByLightningEvent::getLightning);
    }
}