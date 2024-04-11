package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.ClientEvents1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui.ScreenHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.sound.SoundHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.Common1_12_2;
import net.minecraft.client.audio.ISound;

public class Client1_12_2 extends Common1_12_2 implements ClientAPI {

    private final ClientEvents1_12_2 events;
    private final ScreenHelper1_12_2 screenHelper;
    private final SoundHelperAPI<ISound> soundHelper;

    public Client1_12_2() {
        this.events = new ClientEvents1_12_2();
        this.screenHelper = new ScreenHelper1_12_2();
        this.soundHelper = new SoundHelper1_12_2();
    }

    @Override
    public EventsAPI getClientEventsAPI() {
        return this.events;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return Minecraft1_12_2.getInstance();
    }

    @Override
    public ScreenHelperAPI getScreenHelperAPI() {
        return this.screenHelper;
    }

    @Override
    public SoundHelperAPI<ISound> getSoundHelperAPI() {
        return this.soundHelper;
    }
}
