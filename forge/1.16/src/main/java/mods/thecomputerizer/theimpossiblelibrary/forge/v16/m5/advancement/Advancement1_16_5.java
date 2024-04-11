package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementDisplayInfoAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.advancements.Advancement;

public class Advancement1_16_5 implements AdvancementAPI<Advancement> {

    private final Advancement advancement;
    private final AdvancementDisplayInfo1_16_5 display;

    public Advancement1_16_5(Advancement advancement) {
        this.advancement = advancement;
        this.display = new AdvancementDisplayInfo1_16_5(advancement.getDisplay());
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
        return new ResourceLocation1_16_5(this.advancement.getId());
    }

    @Override
    public Advancement getParent() {
        return this.advancement.getParent();
    }
}
