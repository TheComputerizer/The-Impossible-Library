package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterBlocksEventForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCKS;
import static net.minecraft.core.registries.Registries.BLOCK;

public class RegisterBlocksEventForge1_19_4 extends RegisterBlocksEventForge<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(BLOCK)) REGISTER_BLOCKS.invoke(event);
    }
    
    @Override public void register(BlockAPI<?> entry) {
        this.event.register(BLOCK,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}