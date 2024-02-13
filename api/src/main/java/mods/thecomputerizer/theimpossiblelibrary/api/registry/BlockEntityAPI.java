package mods.thecomputerizer.theimpossiblelibrary.api.registry;

public interface BlockEntityAPI<BLOCKENTITY> extends RegistryEntryAPI<BLOCKENTITY> {

    BLOCKENTITY getBlockEntity();
}