package mods.thecomputerizer.theimpossiblelibrary.legacy.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementDisplayInfoAPI;
import net.minecraft.advancements.DisplayInfo;

public class AdvancementDisplayInfoLegacy implements AdvancementDisplayInfoAPI {

    private final DisplayInfo info;

    public AdvancementDisplayInfoLegacy(DisplayInfo info) {
        this.info = info;
    }

}