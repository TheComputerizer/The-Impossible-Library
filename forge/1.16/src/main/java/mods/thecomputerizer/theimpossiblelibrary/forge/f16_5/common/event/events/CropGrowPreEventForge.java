package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CropGrowPreEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent.Pre;

public class CropGrowPreEventForge extends CropGrowPreEventWrapper<Pre> {

    @Override
    protected EventFieldWrapper<Pre,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(Pre::getPos);
    }

    @Override
    protected EventFieldWrapper<Pre,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(Pre::getState);
    }

    @Override
    protected EventFieldWrapper<Pre,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Pre::getWorld);
    }
}