package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

@Getter
public abstract class PlayerAdvancementEventWrapper<E> extends CommonEventWrapper<E> {

    protected AdvancementAPI<?> advancement;
    protected PlayerAPI<?> player;

    protected PlayerAdvancementEventWrapper() {
        super(PLAYER_ADVANCEMENT);
    }

    protected abstract Function<E,?> getAdvancementFunc();
    protected abstract Function<E,?> getPlayerFunc();

    @Override
    protected void populate() {
        this.advancement = WrapperHelper.wrapAdvancement(getAdvancementFunc().apply(this.event));
        this.player = WrapperHelper.wrapPlayer(getPlayerFunc().apply(this.event));
    }
}