package mods.thecomputerizer.theimpossiblelibrary.legacy.common.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementDisplayInfoAPI;
import net.minecraft.advancements.DisplayInfo;

public class AdvancementDisplayInfoLegacy implements AdvancementDisplayInfoAPI {

    private final DisplayInfo info;

    public AdvancementDisplayInfoLegacy(DisplayInfo info) {
        this.info = info;
    }

}