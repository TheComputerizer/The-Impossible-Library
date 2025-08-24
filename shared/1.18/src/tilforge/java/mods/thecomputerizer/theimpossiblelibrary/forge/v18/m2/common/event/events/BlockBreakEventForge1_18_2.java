package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.BlockBreakEventForge;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_BREAK;

public class BlockBreakEventForge1_18_2 extends BlockBreakEventForge<BreakEvent> {
    
    @SubscribeEvent
    public static void onEvent(BreakEvent event) {
        BLOCK_BREAK.invoke(event);
    }
}
