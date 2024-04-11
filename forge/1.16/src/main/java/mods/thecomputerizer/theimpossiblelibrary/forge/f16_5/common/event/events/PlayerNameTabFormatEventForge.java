package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameTabFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text.TextStringForge;
import net.minecraftforge.event.entity.player.PlayerEvent.TabListNameFormat;

import java.util.Objects;

public class PlayerNameTabFormatEventForge extends PlayerNameTabFormatEventWrapper<TabListNameFormat> {

    @Override
    protected EventFieldWrapper<TabListNameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> Objects.nonNull(event.getDisplayName()) ? event.getDisplayName().getString() : null,
                (event,name) -> event.setDisplayName(Objects.nonNull(name) ? new TextStringForge(null,name).getComponent() : null),null);
    }

    @Override
    protected EventFieldWrapper<TabListNameFormat,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(TabListNameFormat::getPlayer);
    }
}