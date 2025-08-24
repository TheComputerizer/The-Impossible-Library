package mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FOVModifierEventForge;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_MODIFIER;

public class FOVModifierEventForge1_20 extends FOVModifierEventForge<ComputeFovModifierEvent> {
    
    @SubscribeEvent
    public static void onEvent(ComputeFovModifierEvent event) {
        FOV_MODIFIER.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(ComputeFovModifierEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks(0f));
    }
    
    @Override protected EventFieldWrapper<ComputeFovModifierEvent,Float> wrapFOVField() {
        return wrapGenericBoth(getter("getFovModifier"),setter("setNewFovModifier"),0f);
    }
    
    @Override protected EventFieldWrapper<ComputeFovModifierEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(ComputeFovModifierEvent::getPlayer);
    }
    
    @Override protected EventFieldWrapper<ComputeFovModifierEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}