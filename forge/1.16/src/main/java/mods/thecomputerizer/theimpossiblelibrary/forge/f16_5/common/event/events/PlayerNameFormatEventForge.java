package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text.TextStringForge;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;

public class PlayerNameFormatEventForge extends PlayerNameFormatEventWrapper<NameFormat> {

    @Override
    protected EventFieldWrapper<NameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> event.getDisplayname().getString(),
                (event,name) -> event.setDisplayname(new TextStringForge(name).get()),"");
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