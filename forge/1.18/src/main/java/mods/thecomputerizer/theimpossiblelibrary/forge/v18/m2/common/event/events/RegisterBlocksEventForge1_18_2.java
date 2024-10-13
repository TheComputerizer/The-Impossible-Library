package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterBlocksEventForge;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCKS;

public class RegisterBlocksEventForge1_18_2 extends RegisterBlocksEventForge<Block> {
    
    @SubscribeEvent
    public static void onEvent(Register<Block> event) {
        REGISTER_BLOCKS.invoke(event);
    }
    
    @Override public void register(BlockAPI<?> entry) {
        this.event.getRegistry().register(entry.unwrap());
    }
}