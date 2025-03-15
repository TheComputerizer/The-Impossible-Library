package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class CreativeTabBuilder1_21 extends CreativeTabBuilderAPI<ItemStack> {
    
    static final List<FutureCreativeTab1_21> registeredFutures = new ArrayList<>();
    
    public static void onRegister(@Nullable Object register) {
        for(FutureCreativeTab1_21 future : registeredFutures) future.register(register);
    }
    
    public static void onSupply(@Nullable Object supply) {
        for(FutureCreativeTab1_21 future : registeredFutures) future.supply(supply);
    }
    
    @Override public CreativeTabAPI<?> build() {
        if(Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot build creative tab with null registry name!");
            return null;
        }
        FutureCreativeTab<CreativeModeTab> future = makeFutureTab();
        future.setIconSupplier(this.icon);
        FutureCreativeTab1_21 futureWrapper = new FutureCreativeTab1_21(future);
        registeredFutures.add(futureWrapper);
        return futureWrapper;
    }
    
    protected abstract FutureCreativeTab<CreativeModeTab> makeFutureTab();
}