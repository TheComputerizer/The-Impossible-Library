package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerBreakSpeedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_BREAK_SPEED;

public class PlayerBreakSpeedEvent1_12_2 extends PlayerBreakSpeedEventWrapper<BreakSpeed> {

    @SubscribeEvent
    public static void onEvent(BreakSpeed event) {
        PLAYER_BREAK_SPEED.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(BreakSpeed event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<BreakSpeed,Float> wrapOriginalSpeedField() {
        return wrapGenericGetter(BreakSpeed::getOriginalSpeed,0f);
    }

    @Override protected EventFieldWrapper<BreakSpeed,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(BreakSpeed::getEntityPlayer);
    }

    @Override protected EventFieldWrapper<BreakSpeed,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(BreakSpeed::getPos);
    }

    @Override protected EventFieldWrapper<BreakSpeed,Float> wrapSpeedField() {
        return wrapGenericBoth(BreakSpeed::getNewSpeed,BreakSpeed::setNewSpeed,0f);
    }

    @Override protected EventFieldWrapper<BreakSpeed,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(BreakSpeed::getState);
    }
}