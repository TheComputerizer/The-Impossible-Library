package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVModifierEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_MODIFIER;

public class FOVModifierEventNeoForge extends FOVModifierEventWrapper<ComputeFovModifierEvent> {
    
    @SubscribeEvent
    public static void onEvent(ComputeFovModifierEvent event) {
        FOV_MODIFIER.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(ComputeFovModifierEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks(0f));
    }
    
    @Override public void setEvent(ComputeFovModifierEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<ComputeFovModifierEvent,Float> wrapFOVField() {
        return wrapGenericBoth(ComputeFovModifierEvent::getFovModifier,ComputeFovModifierEvent::setNewFovModifier,0f);
    }
    
    @Override protected EventFieldWrapper<ComputeFovModifierEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(ComputeFovModifierEvent::getPlayer);
    }
    
    @Override protected EventFieldWrapper<ComputeFovModifierEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}