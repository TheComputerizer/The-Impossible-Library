package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryEntry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.Registry1_12_2;
import net.minecraft.block.Block;

public class Block1_12_2 extends RegistryEntry1_12_2<Block> implements BlockAPI<Block> {

    private final Block block;

    public Block1_12_2(Block block) {
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
    protected Registry1_12_2<Block> getRegistry() {
        return (Registry1_12_2<Block>)(RegistryAPI<?>)RegistryHelper.getBlockRegistry();
    }

    @Override
    public Class<? extends Block> getValueClass() {
        return this.block.getClass();
    }
}