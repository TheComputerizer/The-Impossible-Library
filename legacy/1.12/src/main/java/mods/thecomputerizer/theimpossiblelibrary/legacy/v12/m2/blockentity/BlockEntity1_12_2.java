package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryEntry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.Registry1_12_2;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/**
 * Tile Entities aren't actually stored in registries in 1.12.2 so this is just a placeholder class
 */
public class BlockEntity1_12_2 extends RegistryEntry1_12_2<Block> implements BlockEntityAPI<TileEntity> {

    private final TileEntity tile;

    public BlockEntity1_12_2(TileEntity tile) {
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
    protected Registry1_12_2<Block> getRegistry() {
        return null;
    }

    @Override
    public Class<? extends Block> getValueClass() {
        return null;
    }
}
