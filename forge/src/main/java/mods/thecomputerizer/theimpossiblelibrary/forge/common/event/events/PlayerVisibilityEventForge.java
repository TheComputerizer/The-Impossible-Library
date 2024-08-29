package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerVisibilityEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.Visibility;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_VISIBILITY;

@SuppressWarnings("deprecation")
public class PlayerVisibilityEventForge extends PlayerVisibilityEventWrapper<Visibility> {
    
    @SubscribeEvent
    public static void onEvent(Visibility event) {
        PLAYER_VISIBILITY.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Visibility event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<Visibility,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(Visibility::getPlayer);
    }

    @Override protected EventFieldWrapper<Visibility,Double> wrapVisibilityModifierField() {
        return wrapGenericBoth(Visibility::getVisibilityModifier,Visibility::modifyVisibility,1d);
    }
}