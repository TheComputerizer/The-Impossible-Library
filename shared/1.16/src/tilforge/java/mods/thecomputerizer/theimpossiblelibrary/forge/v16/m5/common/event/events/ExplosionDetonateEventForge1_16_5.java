package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.ExplosionDetonateEventForge;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_DETONATE;

public class ExplosionDetonateEventForge1_16_5 extends ExplosionDetonateEventForge<Detonate> {
    
    @SubscribeEvent
    public static void onEvent(Detonate event) {
        EXPLOSION_DETONATE.invoke(event);
    }
    
    @Override protected EventFieldWrapper<Detonate,List<EntityAPI<?,?>>> wrapAffectedEntitiesField() {
        return wrapGenericGetter(event -> event.getAffectedEntities().stream()
                .map(WrapperHelper::wrapEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),new ArrayList<>());
    }

    @Override protected EventFieldWrapper<Detonate,ExplosionAPI<?>> wrapExplosionField() {
        return wrapExplosionGetter(Detonate::getExplosion);
    }

    @Override protected EventFieldWrapper<Detonate,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Detonate::getWorld);
    }
}