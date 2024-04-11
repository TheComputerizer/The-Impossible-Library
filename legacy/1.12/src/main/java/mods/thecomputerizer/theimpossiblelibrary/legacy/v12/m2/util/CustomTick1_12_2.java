package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.util;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import net.minecraftforge.fml.common.eventhandler.Event;

@Getter
public class CustomTick1_12_2 extends Event {

    private final CustomTick ticker;

    public CustomTick1_12_2(CustomTick ticker) {
        this.ticker = ticker;
    }
}