package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryEntry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.Registry1_16_5;
import net.minecraft.block.Block;

public class Block1_16_5 extends RegistryEntry1_16_5<Block> implements BlockAPI<Block> {

    private final Block block;

    public Block1_16_5(Block block) {
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
    protected Registry1_16_5<Block> getRegistry() {
        return (Registry1_16_5<Block>)(RegistryAPI<?>)RegistryHelper.getBlockRegistry();
    }

    @Override
    public Class<? extends Block> getValueClass() {
        return this.block.getClass();
    }
}