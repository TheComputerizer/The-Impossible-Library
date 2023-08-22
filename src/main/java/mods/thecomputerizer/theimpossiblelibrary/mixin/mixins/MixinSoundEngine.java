package mods.thecomputerizer.theimpossiblelibrary.mixin.mixins;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.events.PlaySoundEvent;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(SoundEngine.class)
public class MixinSoundEngine {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SoundInstance;getSound()Lnet/minecraft/client/resources/sounds/Sound;"), method = "play")
    private Sound theimpossiblelibrary$getSoundRedirect(SoundInstance sound) {
        Constants.testLog("REALLY FINAL PITCH IS {}",sound.getPitch());
        return sound.getSound();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SoundInstance;resolve(Lnet/minecraft/client/sounds/SoundManager;)Lnet/minecraft/client/sounds/WeighedSoundEvents;"), method = "play")
    private WeighedSoundEvents theimpossiblelibrary$getSoundRedirectEEE(SoundInstance sound, SoundManager manager) {
        WeighedSoundEvents resolved = sound.resolve(manager);
        SoundInstance modifiedInstance = PlaySoundEvent.EVENT.invoker().register(sound);
        if(modifiedInstance==sound) return resolved;
        if(Objects.isNull(modifiedInstance)) return null;
        Constants.testLog("FINAL PITCH IS {}",sound.getPitch());
        return modifiedInstance.resolve(manager);
    }
}