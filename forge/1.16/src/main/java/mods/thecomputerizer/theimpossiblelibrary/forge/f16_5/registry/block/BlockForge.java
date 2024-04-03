package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryEntryForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryForge;
import net.minecraft.block.Block;

public class BlockForge extends RegistryEntryForge<Block> implements BlockAPI<Block> {

    private final Block block;

    public BlockForge(Block block) {
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
    protected RegistryForge<Block> getRegistry() {
        return (RegistryForge<Block>)(RegistryAPI<?>)RegistryHelper.getBlockRegistry();
    }

    @Override
    public Class<? extends Block> getValueClass() {
        return this.block.getClass();
    }
}