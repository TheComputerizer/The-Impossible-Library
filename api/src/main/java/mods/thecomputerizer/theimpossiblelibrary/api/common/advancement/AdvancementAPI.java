package mods.thecomputerizer.theimpossiblelibrary.api.common.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class AdvancementAPI<A> extends AbstractWrapped<A> {

    protected AdvancementAPI(A advancement) {
        super(advancement);
    }

    @IndirectCallers public abstract AdvancementDisplayInfoAPI getDisplayInfo();
    public abstract ResourceLocationAPI<?> getID();
    public abstract A getParent();
}