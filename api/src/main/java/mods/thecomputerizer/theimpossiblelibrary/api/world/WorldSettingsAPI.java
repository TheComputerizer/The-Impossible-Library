package mods.thecomputerizer.theimpossiblelibrary.api.world;

import lombok.Getter;

@Getter
public abstract class WorldSettingsAPI<S> {

    protected final S settings;

    protected WorldSettingsAPI(S settings) {
        this.settings = settings;
    }

    public abstract long getSeed();
}