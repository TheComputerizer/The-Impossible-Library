package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.HarvestBlockDropsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.ArrayList;
import java.util.List;

public class HarvestBlockDropsEvent1_16_5 extends HarvestBlockDropsEventWrapper<Object> { //TODO

    @Override
    protected EventFieldWrapper<Object,List<ItemStackAPI<?>>> wrapDropsField() {
        return wrapGenericGetter(event -> new ArrayList<>(),new ArrayList<>());
    }

    @Override
    protected EventFieldWrapper<Object,Float> wrapDropChanceField() {
        return wrapGenericBoth(event -> 0f,(event,chance) -> {},0f);
    }

    @Override
    protected EventFieldWrapper<Object,Integer> wrapFortuneLevelField() {
        return wrapGenericGetter(event -> 0,0);
    }

    @Override
    protected EventFieldWrapper<Object,Boolean> wrapSilkTouchingField() {
        return wrapGenericGetter(event -> false,false);
    }

    @Override
    protected EventFieldWrapper<Object,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }

    @Override
    protected EventFieldWrapper<Object,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(event -> null);
    }

    @Override
    protected EventFieldWrapper<Object,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }

    @Override
    protected EventFieldWrapper<Object,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(event -> null);
    }
}