package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;

@Getter
public abstract class PlayerEventWrapper<E> extends LivingEventWrapper<E> {

    protected PlayerAPI<?> playerAPI;

    protected PlayerEventWrapper(CommonType<?> type) {
        super(type);
    }

    protected void setPlayer(PlayerAPI<?> api) {
        this.playerAPI = api;
        setLiving(api.getLivingAPI());
    }
}