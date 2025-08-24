package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.tab.CreativeTabBuilder1_18_2;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CreativeTabBuilderForge1_18_2 extends CreativeTabBuilder1_18_2 {
    
    @Override protected CreativeModeTab makeTab(String path, Supplier<ItemStack> iconSupplier) {
        return new CreativeModeTab(path) {
            @Override public @NotNull ItemStack makeIcon() {
                return iconSupplier.get();
            }
        };
    }
}