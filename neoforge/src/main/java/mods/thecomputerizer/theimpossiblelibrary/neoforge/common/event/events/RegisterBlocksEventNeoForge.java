package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlocksEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCKS;
import static net.minecraft.core.registries.Registries.BLOCK;

public class RegisterBlocksEventNeoForge extends RegisterBlocksEventWrapper<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(BLOCK)) REGISTER_BLOCKS.invoke(event);
    }
    
    @Override public void setEvent(RegisterEvent event) {
        super.setEvent(event);
    }
    
    @Override public void register(BlockAPI<?> entry) {
        this.event.register(BLOCK,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}