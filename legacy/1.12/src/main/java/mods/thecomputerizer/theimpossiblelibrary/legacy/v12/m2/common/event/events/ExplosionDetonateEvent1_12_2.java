package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.ExplosionDetonateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_DETONATE;

public class ExplosionDetonateEvent1_12_2 extends ExplosionDetonateEventWrapper<Detonate> {

    @SubscribeEvent
    public static void onEvent(Detonate event) {
        EXPLOSION_DETONATE.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Detonate,List<EntityAPI<?>>> wrapAffectedEntitiesField() {
        return wrapGenericGetter(event -> event.getAffectedEntities().stream()
                .map(WrapperHelper::wrapEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),new ArrayList<>());
    }

    @Override
    protected EventFieldWrapper<Detonate,ExplosionAPI<?>> wrapExplosionField() {
        return wrapExplosionGetter(Detonate::getExplosion);
    }

    @Override
    protected EventFieldWrapper<Detonate,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Detonate::getWorld);
    }
}