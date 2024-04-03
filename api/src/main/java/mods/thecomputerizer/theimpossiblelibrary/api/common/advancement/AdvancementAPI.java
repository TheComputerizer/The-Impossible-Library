package mods.thecomputerizer.theimpossiblelibrary.api.common.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface AdvancementAPI<A> {

    A getAdvancement();
    AdvancementDisplayInfoAPI getDisplayInfo();
    ResourceLocationAPI<?> getID();
    A getParent();
}