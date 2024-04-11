package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryEntry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.Registry1_16_5;
import net.minecraft.tileentity.TileEntityType;

public class BlockEntity1_16_5 extends RegistryEntry1_16_5<TileEntityType<?>> implements BlockEntityAPI<TileEntityType<?>> {

    private final TileEntityType<?> tile;

    public BlockEntity1_16_5(TileEntityType<?> tile) {
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
    protected Registry1_16_5<TileEntityType<?>> getRegistry() {
        return (Registry1_16_5<TileEntityType<?>>)(RegistryAPI<?>)RegistryHelper.getBlockRegistry();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends TileEntityType<?>> getValueClass() {
        return (Class<? extends TileEntityType<?>>)this.tile.getClass();
    }
}