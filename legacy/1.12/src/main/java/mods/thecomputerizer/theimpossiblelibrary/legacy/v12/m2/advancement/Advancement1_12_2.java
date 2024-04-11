package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementDisplayInfoAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.advancements.Advancement;

public class Advancement1_12_2 implements AdvancementAPI<Advancement> {

    private final Advancement advancement;
    private final AdvancementDisplayInfo1_12_2 display;

    public Advancement1_12_2(Advancement advancement) {
        this.advancement = advancement;
        this.display = new AdvancementDisplayInfo1_12_2(advancement.getDisplay());
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
        return new ResourceLocation1_12_2(this.advancement.getId());
    }

    @Override
    public Advancement getParent() {
        return this.advancement.getParent();
    }
}
