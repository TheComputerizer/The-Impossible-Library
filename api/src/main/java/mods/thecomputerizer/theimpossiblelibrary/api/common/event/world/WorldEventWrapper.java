package mods.thecomputerizer.theimpossiblelibrary.api.common.event.world;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;

@Getter
public abstract class WorldEventWrapper<E,W> extends CommonEventWrapper<E> {

    protected WorldAPI<W> worldAPI;

    protected WorldEventWrapper(CommonType<?> type) {
        super(type);
    }
}