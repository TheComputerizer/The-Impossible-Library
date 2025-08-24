package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FOVUpdateEventForge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ViewportEvent.ComputeFov;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_UPDATE;

public class FOVUpdateEventForge1_19 extends FOVUpdateEventForge<ComputeFov> {
    
    @SubscribeEvent
    public static void onEvent(ComputeFov event) {
        FOV_UPDATE.invoke(event);
    }
    
    @Override protected EventFieldWrapper<ComputeFov,Float> wrapFOVField() {
        return wrapGenericGetter(getter("getFOV"),0f);
    }
    
    @Override protected EventFieldWrapper<ComputeFov,Float> wrapNewFOVField() {
        return wrapGenericBoth(getter("getFOV"),setter("setFOV"),0f);
    }
    
    @Override protected EventFieldWrapper<ComputeFov,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> {
            Entity entity = event.getCamera().getEntity();
            return entity instanceof Player ? entity : null;
        });
    }
}
