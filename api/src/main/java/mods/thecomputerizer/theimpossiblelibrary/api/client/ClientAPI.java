package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class ClientAPI extends CommonAPI {
    
    private ClientEventsAPI clientEvents;
    private KeyHelperAPI<?> keyHelper;
    private ScreenHelperAPI screenHelper;
    private SoundHelperAPI<?> soundHelper;

    public ClientEventsAPI getClientEvents() {
        if(Objects.isNull(this.clientEvents)) this.clientEvents = initClientEvents().get();
        return this.clientEvents;
    }
    
    public KeyHelperAPI<?> getKeyHelper() {
        if(Objects.isNull(this.keyHelper)) this.keyHelper = initKeyHelper().get();
        return this.keyHelper;
    }
    
    public abstract MinecraftAPI getMinecraft();
    
    public ScreenHelperAPI getScreenHelper() {
        if(Objects.isNull(this.screenHelper)) this.screenHelper = initScreenHelper().get();
        return this.screenHelper;
    }
    
    public SoundHelperAPI<?> getSoundHelper() {
        if(Objects.isNull(this.soundHelper)) this.soundHelper = initSoundHelper().get();
        return this.soundHelper;
    }
    
    protected abstract Supplier<ClientEventsAPI> initClientEvents();
    protected abstract Supplier<KeyHelperAPI<?>> initKeyHelper();
    protected abstract Supplier<ScreenHelperAPI> initScreenHelper();
    protected abstract Supplier<SoundHelperAPI<?>> initSoundHelper();
}