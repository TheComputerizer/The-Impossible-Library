package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedOutEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOGGED_OUT;

public class PlayerLoggedOutEvent1_12_2 extends PlayerLoggedOutEventWrapper<PlayerLoggedOutEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerLoggedOutEvent event) {
        PLAYER_LOGGED_OUT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayerLoggedOutEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<PlayerLoggedOutEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.player);
    }
}