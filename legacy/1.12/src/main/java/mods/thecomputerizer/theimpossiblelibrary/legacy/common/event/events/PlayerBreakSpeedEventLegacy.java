package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerBreakSpeedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_BREAK_SPEED;

public class PlayerBreakSpeedEventLegacy extends PlayerBreakSpeedEventWrapper<BreakSpeed> {

    @SubscribeEvent
    public static void onEvent(BreakSpeed event) {
        PLAYER_BREAK_SPEED.invoke(event);
    }

    @Override
    protected EventFieldWrapper<BreakSpeed,Float> wrapOriginalSpeedField() {
        return wrapGenericGetter(BreakSpeed::getOriginalSpeed,0f);
    }

    @Override
    protected EventFieldWrapper<BreakSpeed,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(BreakSpeed::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<BreakSpeed,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(BreakSpeed::getPos);
    }

    @Override
    protected EventFieldWrapper<BreakSpeed,Float> wrapSpeedField() {
        return wrapGenericBoth(BreakSpeed::getNewSpeed,BreakSpeed::setNewSpeed,0f);
    }

    @Override
    protected EventFieldWrapper<BreakSpeed,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(BreakSpeed::getState);
    }
}