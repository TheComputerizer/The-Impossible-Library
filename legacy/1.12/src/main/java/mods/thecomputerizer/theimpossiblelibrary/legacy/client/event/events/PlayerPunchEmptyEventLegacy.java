package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPunchEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.PlayerHandedEventWrapper.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.PlayerHandedEventWrapper.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.PlayerHandedEventWrapper.Hand.OFFHAND;
import static net.minecraft.util.EnumHand.MAIN_HAND;

public class PlayerPunchEmptyEventLegacy extends PlayerPunchEmptyEventWrapper<LeftClickEmpty> {

    @Override
    protected EventFieldWrapper<LeftClickEmpty,PlayerAPI<?>> wrapPlayerField() {
        return getFieldWrapperGetter(event -> wrapPlayer(LeftClickEmpty::getEntityPlayer),null);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return getFieldWrapperGetter(event -> wrapPos(LeftClickEmpty::getPos),null);
    }

    @Override
    public Hand getHand() {
        return Objects.isNull(this.event) || this.event.getHand()==MAIN_HAND ? MAINHAND : OFFHAND;
    }
}