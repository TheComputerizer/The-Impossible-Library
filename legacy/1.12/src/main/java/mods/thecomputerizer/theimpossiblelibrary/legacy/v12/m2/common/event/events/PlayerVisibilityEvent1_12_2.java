package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerVisibilityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.Visibility;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_VISIBILITY;

public class PlayerVisibilityEvent1_12_2 extends PlayerVisibilityEventWrapper<Visibility> {

    @SubscribeEvent
    public static void onEvent(Visibility event) {
        PLAYER_VISIBILITY.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Visibility,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(Visibility::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<Visibility,Double> wrapVisibilityModifierField() {
        return wrapGenericBoth(Visibility::getVisibilityModifier,Visibility::modifyVisibility,1d);
    }
}