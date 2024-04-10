package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerBreakSpeedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class PlayerBreakSpeedEventForge extends PlayerBreakSpeedEventWrapper<BreakSpeed> {

    @Override
    protected EventFieldWrapper<BreakSpeed,Float> wrapOriginalSpeedField() {
        return wrapGenericGetter(BreakSpeed::getOriginalSpeed,0f);
    }

    @Override
    protected EventFieldWrapper<BreakSpeed,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(BreakSpeed::getPlayer);
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