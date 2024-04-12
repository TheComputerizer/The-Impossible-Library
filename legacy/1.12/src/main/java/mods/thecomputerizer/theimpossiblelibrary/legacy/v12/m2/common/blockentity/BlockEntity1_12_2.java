package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.tileentity.TileEntity;

/**
 * Tile Entities aren't actually stored in registries in 1.12.2 so this is just a placeholder class
 */
public class BlockEntity1_12_2 extends BlockEntityAPI<TileEntity,TileEntity> {

    public BlockEntity1_12_2(TileEntity tile) {
        super(tile,tile);
    }

    @Override
    public RegistryAPI<?> getRegistry() {
        return null;
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return null;
    }
}
