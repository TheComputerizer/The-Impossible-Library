package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCheckHarvestEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;

public class PlayerCheckHarvestEventForge extends PlayerCheckHarvestEventWrapper<HarvestCheck> {

    @Override
    protected EventFieldWrapper<HarvestCheck,Boolean> wrapSuccessField() {
        return wrapGenericBoth(HarvestCheck::canHarvest,HarvestCheck::setCanHarvest,false);
    }

    @Override
    protected EventFieldWrapper<HarvestCheck,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(HarvestCheck::getTargetBlock);
    }

    @Override
    protected EventFieldWrapper<HarvestCheck,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(HarvestCheck::getPlayer);
    }
}