package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameTabFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.util.Objects;

public class PlayerNameTabFormatEventFabric extends PlayerNameTabFormatEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> Objects.nonNull(event.getDisplayName()) ? event.getDisplayName().getString() : null,
                               (event,name) -> event.setDisplayName(Objects.nonNull(name) ?
                                               TextHelper.getLiteral(name).getAsComponent() : null),null);
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(TabListNameFormat::getPlayer);
    }
}