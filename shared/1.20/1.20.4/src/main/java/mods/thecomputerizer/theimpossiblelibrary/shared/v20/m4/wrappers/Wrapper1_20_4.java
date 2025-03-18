package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.common.advancement.Advancement1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.wrappers.Wrapper1_20;

import javax.annotation.Nullable;

public class Wrapper1_20_4 extends Wrapper1_20 {
    
    @Override public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable Object advancement) {
        return getAs(advancement,Advancement1_20_4::new);
    }
}