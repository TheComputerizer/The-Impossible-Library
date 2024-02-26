package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import net.minecraft.block.Block;

public class BlockForge extends RegistryEntryForge<Block> implements BlockAPI<Block> {

    private final Block block;

    protected BlockForge(Block entry) {
        super(entry);
        this.block = entry;
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryForge<Block> getRegistry() {
        RegistryAPI<?> api = RegistryHelper.getBlockRegistry();
        return (RegistryForge<Block>)api;
    }
}