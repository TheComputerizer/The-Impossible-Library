package mods.thecomputerizer.theimpossiblelibrary.events;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.resources.sounds.SoundInstance;

public interface PlaySoundEvent {

    Event<PlaySoundEvent> EVENT = EventFactory.createArrayBacked(PlaySoundEvent.class, events -> sound -> {
        for (PlaySoundEvent event : events)
            sound = event.register(sound);
        return sound;
    });

    /**
     * Called when a sound tries to play
     */
    SoundInstance register(SoundInstance sound);
}