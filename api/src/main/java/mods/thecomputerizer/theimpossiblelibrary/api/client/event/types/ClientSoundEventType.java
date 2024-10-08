package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;

@Getter
public abstract class ClientSoundEventType<E> extends ClientEventWrapper<E> {

    protected SoundHelperAPI soundHelper;

    protected ClientSoundEventType(ClientType<?> type) {
        super(type);
    }

    @Override public void populate() {
        this.soundHelper = TILRef.getClientSubAPI(ClientAPI::getSoundHelper);
    }
}