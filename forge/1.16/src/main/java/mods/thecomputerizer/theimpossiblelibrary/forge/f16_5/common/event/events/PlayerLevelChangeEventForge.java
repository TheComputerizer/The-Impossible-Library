package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLevelChangeEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerXpEvent.LevelChange;

public class PlayerLevelChangeEventForge extends PlayerLevelChangeEventWrapper<LevelChange> {

    @Override
    protected EventFieldWrapper<LevelChange,Integer> wrapLevelsField() {
        return wrapGenericBoth(LevelChange::getLevels,LevelChange::setLevels,0);
    }

    @Override
    protected EventFieldWrapper<LevelChange,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(LevelChange::getPlayer);
    }
}