package mods.thecomputerizer.theimpossiblelibrary.api.common.advancement;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

@Getter
public abstract class AdvancementAPI<A> {

    protected final A advancement;

    protected AdvancementAPI(A advancement) {
        this.advancement = advancement;
    }

    public abstract AdvancementDisplayInfoAPI getDisplayInfo();
    public abstract ResourceLocationAPI<?> getID();
    public abstract A getParent();
}