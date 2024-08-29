package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterBlocksEventForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Block1_16_5;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCKS;

public class RegisterBlocksEventForge1_16_5 extends RegisterBlocksEventForge {
    
    @SubscribeEvent
    public static void onEvent(Register<Block> event) {
        REGISTER_BLOCKS.invoke(event);
    }
    
    @Override public void register(BlockAPI<?> entry) {
        this.event.getRegistry().register(((Block1_16_5)entry).getValue());
    }
}
