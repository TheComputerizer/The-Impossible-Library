package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlocksEventWrapper;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent.Register;

public abstract class RegisterBlocksEventForge extends RegisterBlocksEventWrapper<Register<Block>> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<Block> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}
