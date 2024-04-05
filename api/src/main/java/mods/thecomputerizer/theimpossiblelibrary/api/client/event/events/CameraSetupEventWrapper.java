package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CAMERA_SETUP;

public abstract class CameraSetupEventWrapper<E> extends ClientEventWrapper<E> {

    protected CameraSetupEventWrapper() {
        super(CAMERA_SETUP);
    }

    @Override
    protected void populate() {

    }
}