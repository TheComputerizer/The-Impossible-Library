package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CropGrowPreEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent.CropGrowEvent.Pre;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_PRE;

public class CropGrowPreEventNeoForge extends CropGrowPreEventWrapper<Pre> {
    
    @SubscribeEvent
    public static void onEvent(Pre event) {
        CROP_GROW_PRE.invoke(event);
    }
    
    @Override public void setEvent(Pre event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<Pre,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(Pre::getPos);
    }

    @Override protected EventFieldWrapper<Pre,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(Pre::getState);
    }

    @Override protected EventFieldWrapper<Pre,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Pre::getLevel);
    }
}