package mods.thecomputerizer.theimpossiblelibrary.api.client;

import java.util.Collection;

public interface ClientEventsAPI {
    void defineEventClasses(Collection<Class<?>> classes);
    void initDefaultListeners();
}