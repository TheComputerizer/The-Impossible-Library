package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FOVModifierEventForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.FieldOfView;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_MODIFIER;

public class FOVModifierEventForge1_18_2 extends FOVModifierEventForge<FieldOfView> {
    
    @SubscribeEvent
    public static void onEvent(FOVModifierEvent event) {
        FOV_MODIFIER.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(FieldOfView event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getPartialTicks()));
    }
    
    @Override protected EventFieldWrapper<FieldOfView,Float> wrapFOVField() {
        return wrapGenericBoth(event -> (float)event.getFOV(),(event,fov) -> event.setFOV(fov),0f);
    }
    
    @Override protected EventFieldWrapper<FieldOfView,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getCamera().getEntity());
    }
    
    @Override protected EventFieldWrapper<FieldOfView,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}