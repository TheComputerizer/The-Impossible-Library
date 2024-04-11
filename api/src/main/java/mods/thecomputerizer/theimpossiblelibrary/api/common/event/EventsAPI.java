package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;

public interface EventsAPI {

    void defineEvents();
    boolean isDefined();
    void postCustomTick(CustomTick ticker);
    <E extends EventWrapper<?>> void register(E wrapper);
}