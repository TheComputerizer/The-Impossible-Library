package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import net.minecraft.tileentity.TileEntityType;

public class BlockEntityForge extends RegistryEntryForge<TileEntityType<?>> implements BlockEntityAPI<TileEntityType<?>> {

    private final TileEntityType<?> tile;

    protected BlockEntityForge(TileEntityType<?> entry) {
        super(entry);
        this.tile = entry;
    }

    @Override
    public TileEntityType<?> getBlockEntity() {
        return this.tile;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryForge<TileEntityType<?>> getRegistry() {
        RegistryAPI<?> api = RegistryHelper.getBlockRegistry();
        return (RegistryForge<TileEntityType<?>>)api;
    }
}