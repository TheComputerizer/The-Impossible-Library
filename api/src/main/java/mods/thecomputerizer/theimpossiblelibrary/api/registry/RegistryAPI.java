package mods.thecomputerizer.theimpossiblelibrary.api.registry;

public interface RegistryAPI {

    BiomeAPI<?> getBiomeAPI();
    BlockAPI<?,?> getBlockAPI();
    BlockEntityAPI<?> getBlockEntityAPI();
    EntityAPI<?,?,?> getEntityAPI();
    ItemAPI<?,?> getItemAPI();
    PlayerAPI<?,?> getPlayerAPI();
    WorldAPI<?,?> getWorldAPI();
}
