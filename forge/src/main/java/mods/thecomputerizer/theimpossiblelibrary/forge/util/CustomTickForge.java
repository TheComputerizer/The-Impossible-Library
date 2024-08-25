package mods.thecomputerizer.theimpossiblelibrary.forge.util;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import net.minecraftforge.eventbus.api.Event;

@Getter
public class CustomTickForge extends Event {
    
    private final CustomTick ticker;
    
    public CustomTickForge(CustomTick ticker) {
        this.ticker = ticker;
    }
}