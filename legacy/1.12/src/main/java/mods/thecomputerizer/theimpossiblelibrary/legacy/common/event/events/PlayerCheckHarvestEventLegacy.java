package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCheckHarvestEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHECK_HARVEST;

public class PlayerCheckHarvestEventLegacy extends PlayerCheckHarvestEventWrapper<HarvestCheck> {

    @SubscribeEvent
    public static void onEvent(HarvestCheck event) {
        PLAYER_CHECK_HARVEST.invoke(event);
    }

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
        return wrapPlayerGetter(HarvestCheck::getEntityPlayer);
    }
}