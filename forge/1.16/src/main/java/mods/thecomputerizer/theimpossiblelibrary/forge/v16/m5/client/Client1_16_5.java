package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui.ScreenHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.input.KeyHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.sound.SoundHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.Common1_16_5;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.settings.KeyBinding;

import java.util.Objects;

public class Client1_16_5 extends Common1_16_5 implements ClientAPI {

    private ClientEvents1_16_5 events;
    private KeyHelper1_16_5 keyHelper;
    private ScreenHelper1_16_5 screenHelper;
    private SoundHelperAPI<ISound> soundHelper;

    @Override
    public EventsAPI getClientEvents() {
        if(Objects.isNull(this.events)) this.events = new ClientEvents1_16_5();
        return this.events;
    }

    @Override
    public KeyHelperAPI<KeyBinding> getKeyHelper() {
        if(Objects.isNull(this.keyHelper)) this.keyHelper = new KeyHelper1_16_5();
        return this.keyHelper;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return Minecraft1_16_5.getInstance();
    }

    @Override
    public ScreenHelperAPI getScreenHelper() {
        if(Objects.isNull(this.screenHelper)) this.screenHelper = new ScreenHelper1_16_5();
        return this.screenHelper;
    }

    @Override
    public SoundHelperAPI<ISound> getSoundHelper() {
        if(Objects.isNull(this.soundHelper)) this.soundHelper = new SoundHelper1_16_5();
        return this.soundHelper;
    }
}
