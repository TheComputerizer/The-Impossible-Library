package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementDisplayInfoAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.advancements.Advancement;

public class Advancement1_18_2 extends AdvancementAPI<Advancement> {

    private final AdvancementDisplayInfo1_18_2 display;

    public Advancement1_18_2(Object advancement) {
        super((Advancement)advancement);
        this.display = new AdvancementDisplayInfo1_18_2(((Advancement)advancement).getDisplay());
    }

    @Override public AdvancementDisplayInfoAPI getDisplayInfo() {
        return this.display;
    }

    @Override public ResourceLocationAPI<?> getID() {
        return WrapperHelper.wrapResourceLocation(this.wrapped.getId());
    }

    @Override public Advancement getParent() {
        return this.wrapped.getParent();
    }
}