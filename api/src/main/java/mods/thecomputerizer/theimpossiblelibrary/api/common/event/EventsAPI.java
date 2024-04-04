package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

public interface EventsAPI {

    void defineEvents();
    boolean isDefined();
    <E extends EventWrapper<?>> void register(E wrapper);
}