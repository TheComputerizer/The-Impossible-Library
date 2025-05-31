package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.advancement.Advancement1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.item.ItemStack1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.wrappers.Wrapper1_20;
import org.jetbrains.annotations.Nullable;


import static net.minecraft.world.item.ItemStack.EMPTY;

public class Wrapper1_20_6 extends Wrapper1_20 {
    
    @Override public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable Object advancement) {
        return getAs(advancement,Advancement1_20_6::new);
    }
    
    @Override public <S> ItemStackAPI<S> wrapItemStack(@Nullable Object stack) {
        return getAs(stack,ItemStack1_20_6::new, () -> EMPTY);
    }
}