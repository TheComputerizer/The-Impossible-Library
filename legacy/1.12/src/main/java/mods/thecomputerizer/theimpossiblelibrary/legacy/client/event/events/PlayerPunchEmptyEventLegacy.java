package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPunchEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractionEventType.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractionEventType.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractionEventType.Hand.OFFHAND;
import static net.minecraft.util.EnumHand.MAIN_HAND;

public class PlayerPunchEmptyEventLegacy extends PlayerPunchEmptyEventWrapper<LeftClickEmpty> {

    @SubscribeEvent
    public static void onEvent(LeftClickEmpty event) {
        PLAYER_PUNCH_EMPTY.invoke(event);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickEmpty::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickEmpty::getPos);
    }

    @Override
    public Hand getHand() {
        return Objects.isNull(this.event) || this.event.getHand()==MAIN_HAND ? MAINHAND : OFFHAND;
    }
}