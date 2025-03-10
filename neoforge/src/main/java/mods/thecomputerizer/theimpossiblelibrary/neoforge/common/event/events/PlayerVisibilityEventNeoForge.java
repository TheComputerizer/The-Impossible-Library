package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerVisibilityEventWrapper;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingVisibilityEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_VISIBILITY;

public class PlayerVisibilityEventNeoForge extends PlayerVisibilityEventWrapper<LivingVisibilityEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingVisibilityEvent event) {
        PLAYER_VISIBILITY.invoke(event);
    }
    
    @Override public void setEvent(LivingVisibilityEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<LivingVisibilityEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.getEntity() instanceof Player ? event.getEntity() : null);
    }
    
    @Override protected EventFieldWrapper<LivingVisibilityEvent,Double> wrapVisibilityModifierField() {
        return wrapGenericBoth(LivingVisibilityEvent::getVisibilityModifier,LivingVisibilityEvent::modifyVisibility,1d);
    }
}