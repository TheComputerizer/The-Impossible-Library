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
    private KeyHelperAPI keyHelper;
    private ScreenHelperAPI screenHelper;
    private SharedHandlesClient sharedHandles;
    private SoundHelperAPI soundHelper;

    public final ClientEventsAPI getClientEvents() {
        if(Objects.isNull(this.clientEvents)) this.clientEvents = safelySupply(initClientEvents(),"ClientEvents");
        return this.clientEvents;
    }
    
    public final KeyHelperAPI getKeyHelper() {
        if(Objects.isNull(this.keyHelper)) this.keyHelper = safelySupply(initKeyHelper(),"ClientEvents");
        return this.keyHelper;
    }
    
    public final MinecraftAPI<?> getMinecraft() {
        return safelySupply(minecraftGetter(),"Minecraft");
    }
    
    public final ScreenHelperAPI getScreenHelper() {
        if(Objects.isNull(this.screenHelper)) this.screenHelper = safelySupply(initScreenHelper(),"ScreenHelper");
        return this.screenHelper;
    }
    
    public final SharedHandlesClient getSharedHandlesClient() {
        if(Objects.isNull(this.sharedHandles))
            this.sharedHandles = safelySupply(initSharedHandlesClient(),"SharedHandlesClient");
        return this.sharedHandles;
    }
    
    public final SoundHelperAPI getSoundHelper() {
        if(Objects.isNull(this.soundHelper)) this.soundHelper = safelySupply(initSoundHelper(),"SoundHelper");
        return this.soundHelper;
    }
    
    protected abstract Supplier<ClientEventsAPI> initClientEvents();
    protected abstract Supplier<KeyHelperAPI> initKeyHelper();
    protected abstract Supplier<ScreenHelperAPI> initScreenHelper();
    protected abstract Supplier<SharedHandlesClient> initSharedHandlesClient();
    protected abstract Supplier<SoundHelperAPI> initSoundHelper();
    protected abstract Supplier<MinecraftAPI<?>> minecraftGetter();
}