package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogColorsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;

import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import org.jetbrains.annotations.NotNull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_COLORS;

public class FogColorsEventFabric extends FogColorsEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @SuppressWarnings("NullableProblems")
    @Override protected RenderContext initRenderer(@NotNull Object[] event) {
        return EventHelper.initRenderer(ctx -> {});
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapBlue() {
        return wrapGenericBoth(FogColors::getBlue,FogColors::setBlue,0f);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapGreen() {
        return wrapGenericBoth(FogColors::getGreen,FogColors::setGreen,0f);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapRed() {
        return wrapGenericBoth(FogColors::getRed,FogColors::setRed,0f);
    }

    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}