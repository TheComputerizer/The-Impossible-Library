package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class PlayerNameFormatEventFabric extends PlayerNameFormatEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> event.getDisplayname().getString(),
                (event,name) -> event.setDisplayname(TextHelper.getLiteral(name).getAsComponent()),"");
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(NameFormat::getPlayer);
    }

    @Override protected EventFieldWrapper<Object[],String> wrapUsernameField() {
        return wrapGenericGetter(event -> event.getDisplayname().getString(),"");
    }
}