package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFov;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_UPDATE;

public class FOVUpdateEventNeoForge extends FOVUpdateEventWrapper<ComputeFov> {
    
    @SubscribeEvent
    public static void onEvent(ComputeFov event) {
        FOV_UPDATE.invoke(event);
    }
    
    @Override public void setEvent(ComputeFov event) {
        super.setEvent(event);
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
