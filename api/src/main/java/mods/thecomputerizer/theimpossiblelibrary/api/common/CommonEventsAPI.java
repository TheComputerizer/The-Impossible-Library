package mods.thecomputerizer.theimpossiblelibrary.api.common;

import java.util.Collection;

public interface CommonEventsAPI {

    void defineEventClasses(Collection<Class<?>> classes);
    void initDefaultListeners();
}