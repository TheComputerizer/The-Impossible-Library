package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameTabFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.TextString1_16_5;
import net.minecraftforge.event.entity.player.PlayerEvent.TabListNameFormat;

import java.util.Objects;

public class PlayerNameTabFormatEvent1_16_5 extends PlayerNameTabFormatEventWrapper<TabListNameFormat> {

    @Override
    protected EventFieldWrapper<TabListNameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> Objects.nonNull(event.getDisplayName()) ? event.getDisplayName().getString() : null,
                (event,name) -> event.setDisplayName(Objects.nonNull(name) ? new TextString1_16_5(null,name).getComponent() : null),null);
    }

    @Override
    protected EventFieldWrapper<TabListNameFormat,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(TabListNameFormat::getPlayer);
    }
}