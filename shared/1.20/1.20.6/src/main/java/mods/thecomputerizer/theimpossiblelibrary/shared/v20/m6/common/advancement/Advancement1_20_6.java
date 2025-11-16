package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.advancement;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.advancement.Advancement1_20;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import org.jetbrains.annotations.Nullable;

public class Advancement1_20_6 extends Advancement1_20 {
    
    static @Nullable Object getDisplayInfo(@Nullable Object o) {
        Object unwrapped = unwrap(o);
        return unwrapped instanceof Advancement advancement ? advancement.display().orElse(null) : null;
    }
    
    static @Nullable Object unwrap(@Nullable Object o) {
        return o instanceof AdvancementHolder ? ((AdvancementHolder)o).value() : o;
    }
    
    public Advancement1_20_6(Object advancement) {
        super(unwrap(advancement),getDisplayInfo(advancement));
    }
}