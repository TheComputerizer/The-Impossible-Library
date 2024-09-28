package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPushOutOfBlocksEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUSH_OUT_OF_BLOCKS;
import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box.ZERO;

public class PlayerPushOutOfBlocksEvent1_12_2 extends PlayerPushOutOfBlocksEventWrapper<PlayerSPPushOutOfBlocksEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerSPPushOutOfBlocksEvent event) {
        PLAYER_PUSH_OUT_OF_BLOCKS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayerSPPushOutOfBlocksEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected Box wrapEntityBB() {
        return Objects.nonNull(this.event) ? EventHelper.getAABB(this.event.getEntityBoundingBox()) : ZERO;
    }

    @Override protected EventFieldWrapper<PlayerSPPushOutOfBlocksEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerSPPushOutOfBlocksEvent::getEntityPlayer);
    }
}