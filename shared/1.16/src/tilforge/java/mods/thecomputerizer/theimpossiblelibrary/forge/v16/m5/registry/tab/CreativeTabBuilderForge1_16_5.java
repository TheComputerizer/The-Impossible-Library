package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.tab.CreativeTabBuilder1_16_5;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CreativeTabBuilderForge1_16_5 extends CreativeTabBuilder1_16_5 {
    
    @Override protected CreativeModeTab makeTab(String path, Supplier<ItemStack> iconSupplier) {
        return new CreativeModeTab(path) {
            @Override public @NotNull ItemStack makeIcon() {
                return iconSupplier.get();
            }
        };
    }
}