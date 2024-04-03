package mods.thecomputerizer.theimpossiblelibrary.api.common;

import java.util.Set;

public interface CommonEventsAPI {

    void defineEventClasses(Set<CommonEventsHelper.EventEntry<?,?,?>> map);
    void initDefaultListeners();
}