package mods.thecomputerizer.theimpossiblelibrary.api.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.util.Objects;

@SuppressWarnings("unused")
public class SoundHelper {
    
    public static SoundHelperAPI<?> getAPI() {
        return TILRef.getClientSubAPI(ClientAPI::getSoundHelper);
    }
    
    public static float getCategoryVolume(String category) {
        SoundHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.getCategoryVolume(category) : 0f;
    }
    
    public static void setCategoryVolume(String category, float volume) {
        SoundHelperAPI<?> api = getAPI();
        if(Objects.nonNull(api)) api.setCategoryVolume(category,volume);
    }
}