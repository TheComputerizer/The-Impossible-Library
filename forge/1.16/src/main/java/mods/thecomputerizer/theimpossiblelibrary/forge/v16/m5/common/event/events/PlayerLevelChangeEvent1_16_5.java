package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLevelChangeEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerXpEvent.LevelChange;

public class PlayerLevelChangeEvent1_16_5 extends PlayerLevelChangeEventWrapper<LevelChange> {

    @Override
    protected EventFieldWrapper<LevelChange,Integer> wrapLevelsField() {
        return wrapGenericBoth(LevelChange::getLevels,LevelChange::setLevels,0);
    }

    @Override
    protected EventFieldWrapper<LevelChange,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LevelChange::getPlayer);
    }
}