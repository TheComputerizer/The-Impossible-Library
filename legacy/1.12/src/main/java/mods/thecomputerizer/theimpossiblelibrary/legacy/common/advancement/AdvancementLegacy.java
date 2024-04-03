package mods.thecomputerizer.theimpossiblelibrary.legacy.common.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementDisplayInfoAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.resource.ResourceLocationLegacy;
import net.minecraft.advancements.Advancement;

public class AdvancementLegacy implements AdvancementAPI<Advancement> {

    private final Advancement advancement;
    private final AdvancementDisplayInfoLegacy display;

    public AdvancementLegacy(Advancement advancement) {
        this.advancement = advancement;
        this.display = new AdvancementDisplayInfoLegacy(advancement.getDisplay());
    }

    @Override
    public Advancement getAdvancement() {
        return this.advancement;
    }

    @Override
    public AdvancementDisplayInfoAPI getDisplayInfo() {
        return this.display;
    }

    @Override
    public ResourceLocationAPI<?> getID() {
        return new ResourceLocationLegacy(this.advancement.getId());
    }

    @Override
    public Advancement getParent() {
        return this.advancement.getParent();
    }
}
