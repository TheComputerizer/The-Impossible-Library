package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.util;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import net.minecraftforge.eventbus.api.Event;

@Getter
public class CustomTick1_16_5 extends Event {

    private final CustomTick ticker;

    public CustomTick1_16_5(CustomTick ticker) {
        this.ticker = ticker;
    }
}