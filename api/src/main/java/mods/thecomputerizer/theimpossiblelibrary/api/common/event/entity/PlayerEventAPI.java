package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;

public interface PlayerEventAPI extends LivingEventAPI {

    PlayerAPI<?> getPlayerAPI();
}