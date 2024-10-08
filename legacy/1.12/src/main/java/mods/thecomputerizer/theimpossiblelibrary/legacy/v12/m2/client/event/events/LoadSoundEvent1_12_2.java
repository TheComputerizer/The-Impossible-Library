package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.LoadSoundEventWrapper;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_LOAD;

public class LoadSoundEvent1_12_2 extends LoadSoundEventWrapper<SoundLoadEvent> {

    @SubscribeEvent
    public static void onEvent(SoundLoadEvent event) {
        SOUND_LOAD.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(SoundLoadEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}