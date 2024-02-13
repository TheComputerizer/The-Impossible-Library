package mods.thecomputerizer.theimpossiblelibrary.legacy.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import net.minecraft.block.Block;

public class BlockLegacy extends RegistryEntryLegacy<Block> implements BlockAPI<Block> {

    private final Block block;

    protected BlockLegacy(Block entry) {
        super(entry);
        this.block = entry;
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryLegacy<Block> getRegistry() {
        RegistryAPI<?> api = RegistryHelper.getBlockRegistry();
        return (RegistryLegacy<Block>)api;
    }
}