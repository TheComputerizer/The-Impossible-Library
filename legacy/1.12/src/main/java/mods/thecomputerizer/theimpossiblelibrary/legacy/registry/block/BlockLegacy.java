package mods.thecomputerizer.theimpossiblelibrary.legacy.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryEntryLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryLegacy;
import net.minecraft.block.Block;

public class BlockLegacy extends RegistryEntryLegacy<Block> implements BlockAPI<Block> {

    private final Block block;

    public BlockLegacy(Block block) {
        super(block);
        this.block = block;
    }

    @Override
    public RegistryEntryAPI<Block> getEntryAPI() {
        return this;
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryLegacy<Block> getRegistry() {
        return (RegistryLegacy<Block>)(RegistryAPI<?>)RegistryHelper.getBlockRegistry();
    }

    @Override
    public Class<? extends Block> getValueClass() {
        return this.block.getClass();
    }
}