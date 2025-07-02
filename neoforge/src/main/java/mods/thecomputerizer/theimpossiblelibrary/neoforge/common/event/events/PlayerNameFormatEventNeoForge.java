package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.NameFormat;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_NAME_FORMAT;

public class PlayerNameFormatEventNeoForge extends PlayerNameFormatEventWrapper<NameFormat> {
    
    @SubscribeEvent
    public static void onEvent(NameFormat event) {
        PLAYER_NAME_FORMAT.invoke(event);
    }
    
    @Override public void setEvent(NameFormat event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<NameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> event.getDisplayname().getString(),
                (event,name) -> event.setDisplayname(TextHelper.getLiteral((String)name).getAsComponent()),"");
    }

    @Override protected EventFieldWrapper<NameFormat,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(NameFormat::getEntity);
    }

    @Override protected EventFieldWrapper<NameFormat,String> wrapUsernameField() {
        return wrapGenericGetter(event -> event.getDisplayname().getString(),"");
    }
}