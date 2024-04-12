package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementDisplayInfoAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.advancements.Advancement;

public class Advancement1_16_5 extends AdvancementAPI<Advancement> {

    private final AdvancementDisplayInfo1_16_5 display;

    public Advancement1_16_5(Advancement advancement) {
        super(advancement);
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
