package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FOVModifierEventForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.FOVModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_MODIFIER;

public class FOVModifierEventForge1_16_5 extends FOVModifierEventForge<FOVModifier> {
    
    @SubscribeEvent
    public static void onEvent(FOVModifier event) {
        FOV_MODIFIER.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(FOVModifier event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getRenderPartialTicks()));
    }
    
    @Override protected EventFieldWrapper<FOVModifier,Float> wrapFOVField() {
        return wrapGenericBoth(event -> (float)event.getFOV(),(event,fov) -> event.setFOV(fov),0f);
    }
    
    @Override protected EventFieldWrapper<FOVModifier,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }
    
    @Override protected EventFieldWrapper<FOVModifier,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}