package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.TextString1_16_5;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;

public class PlayerNameFormatEvent1_16_5 extends PlayerNameFormatEventWrapper<NameFormat> {

    @Override
    protected EventFieldWrapper<NameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> event.getDisplayname().getString(),
                (event,name) -> event.setDisplayname(new TextString1_16_5(null,name).getComponent()),"");
    }

    @Override
    protected EventFieldWrapper<NameFormat,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(NameFormat::getPlayer);
    }

    @Override
    protected EventFieldWrapper<NameFormat,String> wrapUsernameField() {
        return wrapGenericGetter(event -> event.getDisplayname().getString(),"");
    }
}