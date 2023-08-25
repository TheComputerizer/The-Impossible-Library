package mods.thecomputerizer.theimpossiblelibrary.mixin.access;

import net.minecraft.client.resources.sounds.SoundInstance;

public interface IRedirectableSound {

    void theimpossibleLibrary$setRedirct(SoundInstance redirected);

    SoundInstance theimpossibleLibrary$getRedirect();
}