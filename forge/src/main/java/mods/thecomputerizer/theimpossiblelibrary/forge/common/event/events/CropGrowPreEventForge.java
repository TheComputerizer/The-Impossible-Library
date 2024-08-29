package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CropGrowPreEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent.Pre;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_PRE;

public class CropGrowPreEventForge extends CropGrowPreEventWrapper<Pre> {
    
    @SubscribeEvent
    public static void onEvent(Pre event) {
        CROP_GROW_PRE.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Pre event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<Pre,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(Pre::getPos);
    }

    @Override protected EventFieldWrapper<Pre,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(Pre::getState);
    }

    @Override protected EventFieldWrapper<Pre,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Pre::getWorld);
    }
}