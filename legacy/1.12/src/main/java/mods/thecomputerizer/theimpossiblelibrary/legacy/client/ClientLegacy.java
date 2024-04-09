package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui.ScreenHelperLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.sound.SoundHelperLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.CommonLegacy;
import net.minecraft.client.audio.ISound;

public class ClientLegacy extends CommonLegacy implements ClientAPI {

    private final ClientEventsLegacy events;
    private final ScreenHelperLegacy screenHelper;
    private final SoundHelperAPI<ISound> soundHelper;

    public ClientLegacy() {
        this.events = new ClientEventsLegacy();
        this.screenHelper = new ScreenHelperLegacy();
        this.soundHelper = new SoundHelperLegacy();
    }

    @Override
    public EventsAPI getClientEventsAPI() {
        return this.events;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return MinecraftLegacy.getInstance();
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
