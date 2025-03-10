package mods.thecomputerizer.theimpossiblelibrary.neoforge.util;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import net.neoforged.bus.api.Event;

@Getter
public class CustomTickNeoForge extends Event {
    
    private final CustomTick ticker;
    
    public CustomTickNeoForge(CustomTick ticker) {
        this.ticker = ticker;
    }
}