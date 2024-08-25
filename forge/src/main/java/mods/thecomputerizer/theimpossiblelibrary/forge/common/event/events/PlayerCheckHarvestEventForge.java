package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCheckHarvestEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHECK_HARVEST;

public class PlayerCheckHarvestEventForge extends PlayerCheckHarvestEventWrapper<HarvestCheck> {
    
    @SubscribeEvent
    public static void onEvent(HarvestCheck event) {
        PLAYER_CHECK_HARVEST.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(HarvestCheck event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
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
    protected EventFieldWrapper<HarvestCheck,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(HarvestCheck::getPlayer);
    }
}