package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.ExplosionDetonateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExplosionDetonateEventForge extends ExplosionDetonateEventWrapper<Detonate> {

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