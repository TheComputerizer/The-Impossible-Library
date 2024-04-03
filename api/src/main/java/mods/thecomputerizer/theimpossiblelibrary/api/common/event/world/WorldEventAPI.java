package mods.thecomputerizer.theimpossiblelibrary.api.common.event.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;

public interface WorldEventAPI extends EventAPI {

    <W> W getWorld();
    WorldAPI<?> getWorldAPI();
}