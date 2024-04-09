package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.gui.ScreenHelperForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.sound.SoundHelperForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.CommonForge;
import net.minecraft.client.audio.ISound;

public class ClientForge extends CommonForge implements ClientAPI {

    private final ClientEventsForge events;
    private final ScreenHelperForge screenHelper;
    private final SoundHelperAPI<ISound> soundHelper;

    public ClientForge() {
        this.events = new ClientEventsForge();
        this.screenHelper = new ScreenHelperForge();
        this.soundHelper = new SoundHelperForge();
    }

    @Override
    public EventsAPI getClientEventsAPI() {
        return this.events;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return MinecraftForgeTIL.getInstance();
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
