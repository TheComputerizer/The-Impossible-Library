package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_NAME_FORMAT;

public class PlayerNameFormatEventLegacy extends PlayerNameFormatEventWrapper<NameFormat> {

    @SubscribeEvent
    public static void onEvent(NameFormat event) {
        PLAYER_NAME_FORMAT.invoke(event);
    }

    @Override
    protected EventFieldWrapper<NameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(NameFormat::getDisplayname,NameFormat::setDisplayname,"");
    }

    @Override
    protected EventFieldWrapper<NameFormat,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(NameFormat::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<NameFormat,String> wrapUsernameField() {
        return wrapGenericGetter(NameFormat::getUsername,"");
    }
}