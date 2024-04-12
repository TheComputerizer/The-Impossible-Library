package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerVisibilityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.Visibility;

public class PlayerVisibilityEvent1_16_5 extends PlayerVisibilityEventWrapper<Visibility> {

    @Override
    protected EventFieldWrapper<Visibility,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(Visibility::getPlayer);
    }

    @Override
    protected EventFieldWrapper<Visibility,Double> wrapVisibilityModifierField() {
        return wrapGenericBoth(Visibility::getVisibilityModifier,Visibility::modifyVisibility,1d);
    }
}