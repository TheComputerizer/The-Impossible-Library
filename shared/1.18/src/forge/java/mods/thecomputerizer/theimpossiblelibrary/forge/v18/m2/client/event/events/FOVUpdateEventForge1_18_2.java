package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FOVUpdateEventForge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.EntityViewRenderEvent.FieldOfView;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_UPDATE;

public class FOVUpdateEventForge1_18_2 extends FOVUpdateEventForge<FieldOfView> {
    
    @SubscribeEvent
    public static void onEvent(FieldOfView event) {
        FOV_UPDATE.invoke(event);
    }
    
    @Override protected EventFieldWrapper<FieldOfView,Float> wrapFOVField() {
        return wrapGenericGetter(event -> (float)event.getFOV(),0f);
    }
    
    @Override protected EventFieldWrapper<FieldOfView,Float> wrapNewFOVField() {
        return wrapGenericBoth(event -> (float)event.getFOV(),(event,value) -> event.setFOV(value),0f);
    }
    
    @Override protected EventFieldWrapper<FieldOfView,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> {
            Entity entity = event.getCamera().getEntity();
            return entity instanceof Player ? (Player)entity : null;
        });
    }
}
