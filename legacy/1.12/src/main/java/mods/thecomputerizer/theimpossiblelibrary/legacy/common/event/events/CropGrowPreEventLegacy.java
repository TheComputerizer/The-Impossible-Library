package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CropGrowPreEventWrapper;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_PRE;

public class CropGrowPreEventLegacy extends CropGrowPreEventWrapper<Pre> {

    @SubscribeEvent
    public static void onEvent(Pre event) {
        CROP_GROW_PRE.invoke(event);
    }
}