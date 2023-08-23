package mods.thecomputerizer.theimpossiblelibrary.client.test;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.events.AdvancmentEvents;
import mods.thecomputerizer.theimpossiblelibrary.events.PlaySoundEvent;
import mods.thecomputerizer.theimpossiblelibrary.events.ResourcesLoadedEvent;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

import java.util.Objects;

@SuppressWarnings("ConstantValue")
public class ClientEventTest {

    private static final boolean RESOURCE = false;
    private static final boolean SOUND = false;
    private static final boolean ADVANCEMENT = true;

    public static void init() {
        Constants.testLog("INITIALIZING CLIENT EVENT TESTS");
        if(RESOURCE) {
            Constants.testLog("ENABLED TEST RESOURCE EVENT");
            ResourcesLoadedEvent.EVENT.register(ClientEventTest::testResourceEvent);
        }
        if(SOUND) {
            Constants.testLog("ENABLED TEST SOUND EVENT");
            PlaySoundEvent.EVENT.register(ClientEventTest::testSoundEvent);
        }
        if(ADVANCEMENT) {
            Constants.testLog("ENABLED TEST ADVANCEMENT EVENT");
            AdvancmentEvents.CLIENT_GRANTED.register(ClientEventTest::testAdvancementEvent);
        }
    }

    private static void testResourceEvent() {
        Constants.testLog("TESTING RESOURCE EVENT");
    }

    private static SoundInstance testSoundEvent(SoundInstance sound) {
        Constants.testLog("TESTING SOUND EVENT START");
        if(Objects.isNull(sound)) Constants.testLog("SOUND INSTANCEWAS NULL");
        else {
            Constants.testLog("CATEGORY {} VOLUME {} PITCH {} INSTANCE SOURCE {}",sound.getSource().getName(),
                    sound.getVolume(),sound.getPitch(),sound.getLocation());
            if(Objects.isNull(sound.getSound())) Constants.testLog("SOUND WAS NULL");
            else Constants.testLog("SOUND SOURCE {}",sound.getSound().getLocation());
            if(sound.getSource()==SoundSource.MUSIC) {
                Constants.testLog("TESTING MUSIC PITCH CHANGE");
                return new SimpleSoundInstance(sound.getLocation(), sound.getSource(),
                        sound.getVolume(), 1.5f, sound.isLooping(), sound.getDelay(), sound.getAttenuation(), sound.getX(),
                        sound.getY(), sound.getZ(), sound.isRelative());
            }
        }
        Constants.testLog("TESTING SOUND EVENT END");
        return sound;
    }

    private static void testAdvancementEvent(Advancement advancement) {
        Constants.testLog("TESTING CLIENT ADVANCEMENT EVENT");
    }
}
