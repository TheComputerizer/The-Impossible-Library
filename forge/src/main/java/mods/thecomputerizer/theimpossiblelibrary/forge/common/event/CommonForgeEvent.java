package mods.thecomputerizer.theimpossiblelibrary.forge.common.event;

import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.function.Function;

public interface CommonForgeEvent {
    
    default Function<?,Object> entityGetter() {
        return event -> event instanceof EntityEvent ? ((EntityEvent)event).getEntity() : null;
    }
    
    default Function<?,Object> livingGetter() {
        return event -> event instanceof LivingEvent ? ((EntityEvent)event).getEntity() : null;
    }

    default Function<?,Object> playerGetter() {
        return event -> event instanceof PlayerEvent ? ((EntityEvent)event).getEntity() : null;
    }
}