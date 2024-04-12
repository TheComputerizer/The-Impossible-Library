package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityStruckByLightningEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_SMITTEN;

public class EntityStruckByLightningEvent1_12_2 extends EntityStruckByLightningEventWrapper<EntityStruckByLightningEvent> {

    @SubscribeEvent
    public static void onEvent(EntityJoinWorldEvent event) {
        ENTITY_SMITTEN.invoke(event);
    }

    @Override
    protected EventFieldWrapper<EntityStruckByLightningEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityStruckByLightningEvent::getEntity);
    }

    @Override
    protected EventFieldWrapper<EntityStruckByLightningEvent,EntityAPI<?,?>> wrapLightningEntityField() {
        return wrapEntityGetter(EntityStruckByLightningEvent::getLightning);
    }
}