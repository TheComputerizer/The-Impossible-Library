package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.ClientEvents1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui.ScreenHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.input.KeyHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.sound.SoundHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.Common1_12_2;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.settings.KeyBinding;

import java.util.Objects;

public class Client1_12_2 extends Common1_12_2 implements ClientAPI {

    private ClientEvents1_12_2 events;
    private KeyHelper1_12_2 keyHelper;
    private ScreenHelper1_12_2 screenHelper;
    private SoundHelperAPI<ISound> soundHelper;

    @Override
    public EventsAPI getClientEvents() {
        if(Objects.isNull(this.events)) this.events = new ClientEvents1_12_2();
        return this.events;
    }

    @Override
    public KeyHelperAPI<KeyBinding> getKeyHelper() {
        if(Objects.isNull(this.keyHelper)) this.keyHelper = new KeyHelper1_12_2();
        return this.keyHelper;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return Minecraft1_12_2.getInstance();
    }

    @Override
    public ScreenHelperAPI getScreenHelper() {
        if(Objects.isNull(this.screenHelper)) this.screenHelper = new ScreenHelper1_12_2();
        return this.screenHelper;
    }

    @Override
    public SoundHelperAPI<ISound> getSoundHelper() {
        if(Objects.isNull(this.soundHelper)) this.soundHelper = new SoundHelper1_12_2();
        return this.soundHelper;
    }
}
