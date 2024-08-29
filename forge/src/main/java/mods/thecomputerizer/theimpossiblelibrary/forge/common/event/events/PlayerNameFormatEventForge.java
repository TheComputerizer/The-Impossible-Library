package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_NAME_FORMAT;

public class PlayerNameFormatEventForge extends PlayerNameFormatEventWrapper<NameFormat> {
    
    @SubscribeEvent
    public static void onEvent(NameFormat event) {
        PLAYER_NAME_FORMAT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(NameFormat event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<NameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> event.getDisplayname().getString(),
                (event,name) -> event.setDisplayname(TextHelper.getLiteral(name).getAsComponent()),"");
    }

    @Override protected EventFieldWrapper<NameFormat,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(NameFormat::getPlayer);
    }

    @Override protected EventFieldWrapper<NameFormat,String> wrapUsernameField() {
        return wrapGenericGetter(event -> event.getDisplayname().getString(),"");
    }
}