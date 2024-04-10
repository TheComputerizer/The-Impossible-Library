package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCloneEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;

public class PlayerCloneEventForge extends PlayerCloneEventWrapper<Clone> {

    @Override
    protected EventFieldWrapper<Clone,Boolean> wrapDeathField() {
        return wrapGenericGetter(Clone::isWasDeath,true);
    }

    @Override
    protected EventFieldWrapper<Clone,PlayerAPI<?>> wrapOriginalField() {
        return wrapPlayerGetter(Clone::getOriginal);
    }

    @Override
    protected EventFieldWrapper<Clone,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(Clone::getPlayer);
    }
}