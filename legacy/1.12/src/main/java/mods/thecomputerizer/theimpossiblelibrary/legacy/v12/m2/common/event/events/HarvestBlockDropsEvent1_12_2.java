package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.HarvestBlockDropsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_HARVEST;

public class HarvestBlockDropsEvent1_12_2 extends HarvestBlockDropsEventWrapper<HarvestDropsEvent> {

    @SubscribeEvent
    public static void onEvent(HarvestDropsEvent event) {
        BLOCK_HARVEST.invoke(event);
    }

    @Override
    protected EventFieldWrapper<HarvestDropsEvent,List<ItemStackAPI<?>>> wrapDropsField() {
        return wrapGenericGetter(event -> event.getDrops().stream()
                .map(WrapperHelper::wrapItemStack)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),new ArrayList<>());
    }

    @Override
    protected EventFieldWrapper<HarvestDropsEvent,Float> wrapDropChanceField() {
        return wrapGenericBoth(HarvestDropsEvent::getDropChance,HarvestDropsEvent::setDropChance,0f);
    }

    @Override
    protected EventFieldWrapper<HarvestDropsEvent,Integer> wrapFortuneLevelField() {
        return wrapGenericGetter(HarvestDropsEvent::getFortuneLevel,0);
    }

    @Override
    protected EventFieldWrapper<HarvestDropsEvent,Boolean> wrapSilkTouchingField() {
        return wrapGenericGetter(HarvestDropsEvent::isSilkTouching,false);
    }

    @Override
    protected EventFieldWrapper<HarvestDropsEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(HarvestDropsEvent::getHarvester);
    }

    @Override
    protected EventFieldWrapper<HarvestDropsEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(HarvestDropsEvent::getPos);
    }

    @Override
    protected EventFieldWrapper<HarvestDropsEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(HarvestDropsEvent::getState);
    }

    @Override
    protected EventFieldWrapper<HarvestDropsEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(HarvestDropsEvent::getWorld);
    }
}