package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_NAME_FORMAT;

public class PlayerNameFormatEvent1_12_2 extends PlayerNameFormatEventWrapper<NameFormat> {

    @SubscribeEvent
    public static void onEvent(NameFormat event) {
        PLAYER_NAME_FORMAT.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(NameFormat event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<NameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(NameFormat::getDisplayname,NameFormat::setDisplayname,"");
    }

    @Override
    protected EventFieldWrapper<NameFormat,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(NameFormat::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<NameFormat,String> wrapUsernameField() {
        return wrapGenericGetter(NameFormat::getUsername,"");
    }
}