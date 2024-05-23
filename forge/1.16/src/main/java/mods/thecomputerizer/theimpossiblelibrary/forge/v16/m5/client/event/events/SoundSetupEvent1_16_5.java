package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.SoundSetupEventWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.SoundSetupEvent;

public class SoundSetupEvent1_16_5 extends SoundSetupEventWrapper<SoundSetupEvent,ISound> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(SoundSetupEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}