package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEventsHelper;

import java.util.Collection;
import java.util.Set;

public interface ClientEventsAPI {
    void defineEventClasses(Set<CommonEventsHelper.EventEntry<?,?,?>> entries);
    void initDefaultListeners();
}