package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.ExplosionDetonateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExplosionDetonateEventFabric extends ExplosionDetonateEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],List<EntityAPI<?,?>>> wrapAffectedEntitiesField() {
        return wrapGenericGetter(wrapArrayGetter(0),//event -> event.getAffectedEntities().stream()
                //.map(WrapperHelper::wrapEntity)
                //.filter(Objects::nonNull)
                //.collect(Collectors.toList()),
            new ArrayList<>());
    }

    @Override protected EventFieldWrapper<Object[],ExplosionAPI<?>> wrapExplosionField() {
        return wrapExplosionGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(wrapArrayGetter(0));
    }
}