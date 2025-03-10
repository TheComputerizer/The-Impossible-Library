package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameTabFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.TabListNameFormat;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_TAB_FORMAT;

public class PlayerNameTabFormatEventNeoForge extends PlayerNameTabFormatEventWrapper<TabListNameFormat> {
    
    @SubscribeEvent
    public static void onEvent(TabListNameFormat event) {
        PLAYER_TAB_FORMAT.invoke(event);
    }
    
    @Override public void setEvent(TabListNameFormat event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<TabListNameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> Objects.nonNull(event.getDisplayName()) ? event.getDisplayName().getString() : null,
                               (event,name) -> event.setDisplayName(Objects.nonNull(name) ?
                                               TextHelper.getLiteral(name).getAsComponent() : null),null);
    }

    @Override protected EventFieldWrapper<TabListNameFormat,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(TabListNameFormat::getEntity);
    }
}