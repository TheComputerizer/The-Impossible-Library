package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlocksEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.Block1_16_5;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent.Register;

public class RegisterBlocksEvent1_16_5 extends RegisterBlocksEventWrapper<Register<Block>> {
    
    @Override public void register(BlockAPI<?> entry) {
        this.event.getRegistry().register(((Block1_16_5)entry).getValue());
    }
}
