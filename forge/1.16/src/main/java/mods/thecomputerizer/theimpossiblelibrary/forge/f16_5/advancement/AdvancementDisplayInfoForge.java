package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementDisplayInfoAPI;
import net.minecraft.advancements.DisplayInfo;

public class AdvancementDisplayInfoForge implements AdvancementDisplayInfoAPI {

    private final DisplayInfo info;

    public AdvancementDisplayInfoForge(DisplayInfo info) {
        this.info = info;
    }

}