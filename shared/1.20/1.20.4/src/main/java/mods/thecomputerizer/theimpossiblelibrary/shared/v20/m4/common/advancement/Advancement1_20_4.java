package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.common.advancement;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.advancement.Advancement1_20;
import net.minecraft.advancements.AdvancementHolder;

public class Advancement1_20_4 extends Advancement1_20 {
    
    public Advancement1_20_4(Object advancement) {
        super(advancement instanceof AdvancementHolder ? ((AdvancementHolder)advancement).value() : advancement);
    }
}