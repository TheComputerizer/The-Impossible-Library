package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PlayerBreakSpeedEventForge;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_BREAK_SPEED;

public class PlayerBreakSpeedEventForge1_18_2 extends PlayerBreakSpeedEventForge {
    
    @SubscribeEvent
    public static void onEvent(BreakSpeed event) {
        PLAYER_BREAK_SPEED.invoke(event);
    }

    @Override protected EventFieldWrapper<BreakSpeed,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(BreakSpeed::getPos);
    }
}