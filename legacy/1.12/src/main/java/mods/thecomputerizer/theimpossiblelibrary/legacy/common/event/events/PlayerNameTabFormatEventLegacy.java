package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameTabFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;

public class PlayerNameTabFormatEventLegacy extends PlayerNameTabFormatEventWrapper<NameFormat> { //TODO Figure out a replacement for this


    @Override
    protected EventFieldWrapper<NameFormat,PlayerAPI<?>> wrapPlayerField() {
        return null;
    }
}