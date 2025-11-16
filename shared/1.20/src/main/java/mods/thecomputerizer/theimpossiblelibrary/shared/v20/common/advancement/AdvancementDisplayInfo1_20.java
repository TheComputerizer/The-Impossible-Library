package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.advancement;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementDisplayInfoAPI;
import net.minecraft.advancements.DisplayInfo;

@SuppressWarnings("ClassCanBeRecord")
public class AdvancementDisplayInfo1_20 implements AdvancementDisplayInfoAPI {

    @Getter private final DisplayInfo info;

    public AdvancementDisplayInfo1_20(Object info) {
        this.info = (DisplayInfo)info;
    }
}