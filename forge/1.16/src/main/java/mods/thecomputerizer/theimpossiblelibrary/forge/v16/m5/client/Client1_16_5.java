package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui.ScreenHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.sound.SoundHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.Common1_16_5;
import net.minecraft.client.audio.ISound;

public class Client1_16_5 extends Common1_16_5 implements ClientAPI {

    private final ClientEvents1_16_5 events;
    private final ScreenHelper1_16_5 screenHelper;
    private final SoundHelperAPI<ISound> soundHelper;

    public Client1_16_5() {
        super();
        this.events = new ClientEvents1_16_5();
        this.screenHelper = new ScreenHelper1_16_5();
        this.soundHelper = new SoundHelper1_16_5();
    }

    @Override
    public EventsAPI getClientEvents() {
        return this.events;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return Minecraft1_16_5.getInstance();
    }

    @Override
    public ScreenHelperAPI getScreenHelper() {
        return this.screenHelper;
    }

    @Override
    public SoundHelperAPI<ISound> getSoundHelper() {
        return this.soundHelper;
    }
}
