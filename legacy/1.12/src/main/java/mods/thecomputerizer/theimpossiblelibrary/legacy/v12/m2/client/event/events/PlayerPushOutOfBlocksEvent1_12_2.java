package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPushOutOfBlocksEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUSH_OUT_OF_BLOCKS;

public class PlayerPushOutOfBlocksEvent1_12_2 extends PlayerPushOutOfBlocksEventWrapper<PlayerSPPushOutOfBlocksEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerSPPushOutOfBlocksEvent event) {
        PLAYER_PUSH_OUT_OF_BLOCKS.invoke(event);
    }

    @Override
    protected Box wrapEntityBB() {
        return Objects.nonNull(this.event) ? Events1_12_2.getBox(this.event.getEntityBoundingBox()) : Box.ZERO;
    }

    @Override
    protected EventFieldWrapper<PlayerSPPushOutOfBlocksEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerSPPushOutOfBlocksEvent::getEntityPlayer);
    }
}