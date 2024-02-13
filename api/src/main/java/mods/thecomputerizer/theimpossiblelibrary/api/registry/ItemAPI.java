package mods.thecomputerizer.theimpossiblelibrary.api.registry;

public interface ItemAPI<ITEM> extends RegistryEntryAPI<ITEM> {

    ITEM getItem();
}