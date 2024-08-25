package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameTabFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.minecraftforge.event.entity.player.PlayerEvent.TabListNameFormat;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_TAB_FORMAT;

public class PlayerNameTabFormatEventForge extends PlayerNameTabFormatEventWrapper<TabListNameFormat> {
    
    @SubscribeEvent
    public static void onEvent(TabListNameFormat event) {
        PLAYER_TAB_FORMAT.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(TabListNameFormat event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<TabListNameFormat,String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> Objects.nonNull(event.getDisplayName()) ? event.getDisplayName().getString() : null,
                               (event,name) -> event.setDisplayName(Objects.nonNull(name) ?
                                               TextHelper.getLiteral(name).getAsComponent() : null),null);
    }

    @Override
    protected EventFieldWrapper<TabListNameFormat,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(TabListNameFormat::getPlayer);
    }
}