package mods.thecomputerizer.theimpossiblelibrary.mixin.mixins;

import mods.thecomputerizer.theimpossiblelibrary.mixin.access.IRedirectableSound;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(AbstractSoundInstance.class)
public abstract class MixinAbstractSoundInstance implements IRedirectableSound {

    @Shadow @Final protected ResourceLocation location;
    @Shadow protected Sound sound;
    @Shadow @Final protected SoundSource source;
    @Shadow protected boolean looping;
    @Shadow protected int delay;
    @Shadow protected float volume;
    @Shadow protected float pitch;
    @Shadow protected double x;
    @Shadow protected double y;
    @Shadow protected double z;
    @Shadow protected SoundInstance.Attenuation attenuation;
    @Shadow protected boolean relative;
    @Unique
    private SoundInstance theimpossibleLibrary$redirected;

    @Override
    public void theimpossibleLibrary$setRedirct(SoundInstance redirected) {
        this.theimpossibleLibrary$redirected = redirected;
    }

    @Override
    public SoundInstance theimpossibleLibrary$getRedirect() {
        return this.theimpossibleLibrary$redirected;
    }

    @SuppressWarnings("RedundantCast")
    @Unique
    private SoundInstance theimpossiblelibrary$cast() {
        return (SoundInstance)(Object)this;
    }

    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/sounds/SoundSource;)V")
    private void theimpossibleLibrary$init(ResourceLocation location, SoundSource source, CallbackInfo ci) {
        this.theimpossibleLibrary$redirected = theimpossiblelibrary$cast();
    }

    @Unique
    private boolean isOriginal() {
        return Objects.isNull(this.theimpossibleLibrary$redirected) || 
                this.theimpossibleLibrary$redirected==theimpossiblelibrary$cast();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public ResourceLocation getLocation() {
        return isOriginal() ? this.location : this.theimpossibleLibrary$redirected.getLocation();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public WeighedSoundEvents resolve(SoundManager manager) {
        if(isOriginal()) {
            WeighedSoundEvents weighed = manager.getSoundEvent(this.location);
            this.sound = Objects.isNull(weighed) ? SoundManager.EMPTY_SOUND : weighed.getSound();
            return weighed;
        }
        return this.theimpossibleLibrary$redirected.resolve(manager);
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public Sound getSound() {
        return isOriginal() ? this.sound : this.theimpossibleLibrary$redirected.getSound();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public SoundSource getSource() {
        return isOriginal() ? this.source : this.theimpossibleLibrary$redirected.getSource();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public boolean isLooping() {
        return isOriginal() ? this.looping : this.theimpossibleLibrary$redirected.isLooping();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public int getDelay() {
        return isOriginal() ? this.delay : this.theimpossibleLibrary$redirected.getDelay();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public float getVolume() {
        return isOriginal() ? this.volume*this.sound.getVolume() : this.theimpossibleLibrary$redirected.getVolume();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public float getPitch() {
        return isOriginal() ? this.pitch*this.sound.getPitch() : this.theimpossibleLibrary$redirected.getPitch();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public double getX() {
        return isOriginal() ? this.x : this.theimpossibleLibrary$redirected.getX();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public double getY() {
        return isOriginal() ? this.y : this.theimpossibleLibrary$redirected.getY();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public double getZ() {
        return isOriginal() ? this.z : this.theimpossibleLibrary$redirected.getZ();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public SoundInstance.Attenuation getAttenuation() {
        return isOriginal() ? this.attenuation : this.theimpossibleLibrary$redirected.getAttenuation();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public boolean isRelative() {
        return isOriginal() ? this.relative : this.theimpossibleLibrary$redirected.isRelative();
    }

    /**
     * @author The_Computizer
     * @reason Overwrite all AbstractSoundInstance methods to get around needing to reassign new SoundInstance
     */
    @Overwrite
    public String toString() {
        return isOriginal() ? "SoundInstance["+this.location+"]" : this.theimpossibleLibrary$redirected.toString();
    }
}
