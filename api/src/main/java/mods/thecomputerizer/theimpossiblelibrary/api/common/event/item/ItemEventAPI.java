package mods.thecomputerizer.theimpossiblelibrary.api.common.event.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.RegistryObjectEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemAPI;

public interface ItemEventAPI extends RegistryObjectEventAPI {

    <I> I getItem();
    ItemAPI<?> getItemAPI();
}