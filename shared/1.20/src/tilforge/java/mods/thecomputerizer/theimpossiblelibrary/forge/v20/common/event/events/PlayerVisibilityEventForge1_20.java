package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PlayerVisibilityEventForge;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent.LivingVisibilityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_VISIBILITY;

public class PlayerVisibilityEventForge1_20 extends PlayerVisibilityEventForge<LivingVisibilityEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingVisibilityEvent event) {
        PLAYER_VISIBILITY.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingVisibilityEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.getEntity() instanceof Player ? event.getEntity() : null);
    }
    
    @Override protected EventFieldWrapper<LivingVisibilityEvent,Double> wrapVisibilityModifierField() {
        return wrapGenericBoth(LivingVisibilityEvent::getVisibilityModifier,LivingVisibilityEvent::modifyVisibility,1d);
    }
}