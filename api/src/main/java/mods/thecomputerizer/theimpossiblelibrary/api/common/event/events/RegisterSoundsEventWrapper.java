package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonRegistryEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_SOUNDS;

public abstract class RegisterSoundsEventWrapper<E> extends CommonRegistryEventType<E,SoundEventAPI<?>> {

    protected RegisterSoundsEventWrapper() {
        super(REGISTER_SOUNDS);
    }
}
