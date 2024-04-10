package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementDisplayInfoAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource.ResourceLocationForge;
import net.minecraft.advancements.Advancement;

public class AdvancementForge implements AdvancementAPI<Advancement> {

    private final Advancement advancement;
    private final AdvancementDisplayInfoForge display;

    public AdvancementForge(Advancement advancement) {
        this.advancement = advancement;
        this.display = new AdvancementDisplayInfoForge(advancement.getDisplay());
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
        return new ResourceLocationForge(this.advancement.getId());
    }

    @Override
    public Advancement getParent() {
        return this.advancement.getParent();
    }
}
