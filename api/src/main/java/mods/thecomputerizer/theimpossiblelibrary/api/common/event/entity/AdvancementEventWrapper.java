package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

@Getter
public abstract class AdvancementEventWrapper<E,A> extends PlayerEventWrapper<E> {

    protected AdvancementAPI<A> advancement;

    protected AdvancementEventWrapper() {
        super(PLAYER_ADVANCEMENT);
    }
}