package mods.thecomputerizer.theimpossiblelibrary.legacy.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryEntryLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryLegacy;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/**
 * Tile Entities aren't actually stored in registries in 1.12.2 so this is just a placeholder class
 */
public class BlockEntityLegacy extends RegistryEntryLegacy<Block> implements BlockEntityAPI<TileEntity> {

    private final TileEntity tile;

    public BlockEntityLegacy(TileEntity tile) {
        super(tile.getBlockType());
        this.tile = tile;
    }

    @Override
    public RegistryEntryAPI<Block> getEntryAPI() {
        return this;
    }

    @Override
    public TileEntity getBlockEntity() {
        return this.tile;
    }

    @Override
    protected RegistryLegacy<Block> getRegistry() {
        return null;
    }

    @Override
    public Class<? extends Block> getValueClass() {
        return null;
    }
}
