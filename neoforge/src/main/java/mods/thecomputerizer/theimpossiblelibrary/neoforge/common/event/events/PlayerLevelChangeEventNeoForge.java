package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLevelChangeEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent.LevelChange;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_LEVEL_CHANGE;

public class PlayerLevelChangeEventNeoForge extends PlayerLevelChangeEventWrapper<LevelChange> {
    
    @SubscribeEvent
    public static void onEvent(LevelChange event) {
        PLAYER_XP_LEVEL_CHANGE.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LevelChange event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<LevelChange,Integer> wrapLevelsField() {
        return wrapGenericBoth(LevelChange::getLevels,LevelChange::setLevels,0);
    }

    @Override protected EventFieldWrapper<LevelChange,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LevelChange::getEntity);
    }
}