package mods.thecomputerizer.theimpossiblelibrary.api.client.sound;

public interface SoundHelperAPI<S> {

    SoundAPI<S> getAPI(S sound);
    float getCategoryVolume(String category);
    void setCategoryVolume(String category, float volume);
}