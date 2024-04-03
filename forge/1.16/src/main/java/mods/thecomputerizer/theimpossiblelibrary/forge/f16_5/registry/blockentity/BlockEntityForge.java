package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryEntryForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryForge;
import net.minecraft.tileentity.TileEntityType;

public class BlockEntityForge extends RegistryEntryForge<TileEntityType<?>> implements BlockEntityAPI<TileEntityType<?>> {

    private final TileEntityType<?> tile;

    public BlockEntityForge(TileEntityType<?> tile) {
        super(tile);
        this.tile = tile;
    }

    @Override
    public RegistryEntryAPI<TileEntityType<?>> getEntryAPI() {
        return this;
    }

    @Override
    public TileEntityType<?> getBlockEntity() {
        return this.tile;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryForge<TileEntityType<?>> getRegistry() {
        return (RegistryForge<TileEntityType<?>>)(RegistryAPI<?>)RegistryHelper.getBlockRegistry();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends TileEntityType<?>> getValueClass() {
        return (Class<? extends TileEntityType<?>>)this.tile.getClass();
    }
}