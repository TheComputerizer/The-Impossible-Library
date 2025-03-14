package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.tab.CreativeTabBuilder1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.tab.FutureCreativeTab1_19;
import net.minecraft.world.item.CreativeModeTab;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class CreativeTabBuilder1_19_4 extends CreativeTabBuilder1_19 {
    
    static final List<FutureCreativeTab1_19> registeredFutures = new ArrayList<>();
    
    public static void onRegister(@Nullable Object register) {
        for(FutureCreativeTab1_19 future : registeredFutures) future.register(register);
    }
    
    public static void onSupply(@Nullable Object supply) {
        for(FutureCreativeTab1_19 future : registeredFutures) future.supply(supply);
    }
    
    @Override public CreativeTabAPI<?> build() {
        if(Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot build creative tab with null registry name!");
            return null;
        }
        FutureCreativeTab<CreativeModeTab> future = makeFutureTab();
        future.setIconSupplier(this.icon);
        FutureCreativeTab1_19 futureWrapper = new FutureCreativeTab1_19(future);
        registeredFutures.add(futureWrapper);
        return futureWrapper;
    }
    
    protected abstract FutureCreativeTab<CreativeModeTab> makeFutureTab();
}