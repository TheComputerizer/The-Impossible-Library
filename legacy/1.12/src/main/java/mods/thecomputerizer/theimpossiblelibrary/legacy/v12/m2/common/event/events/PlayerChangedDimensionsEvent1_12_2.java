package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangedDimensionsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHANGED_DIMENSIONS;

public class PlayerChangedDimensionsEvent1_12_2 extends PlayerChangedDimensionsEventWrapper<PlayerChangedDimensionEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerChangedDimensionEvent event) {
        PLAYER_CHANGED_DIMENSIONS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayerChangedDimensionEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<PlayerChangedDimensionEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.player);
    }
}