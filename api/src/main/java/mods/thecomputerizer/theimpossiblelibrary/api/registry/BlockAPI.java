package mods.thecomputerizer.theimpossiblelibrary.api.registry;

public interface BlockAPI<BLOCK> extends RegistryEntryAPI<BLOCK> {

    BLOCK getBlock();
}