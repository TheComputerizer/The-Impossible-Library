package mods.thecomputerizer.theimpossiblelibrary.forge.v19.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.tab.CreativeTabBuilder1_19;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CreativeTabBuilderForge1_19 extends CreativeTabBuilder1_19 {
    
    @Override protected CreativeModeTab makeTab(String path, Supplier<ItemStack> iconSupplier) {
        return new CreativeModeTab(this.registryName.getPath()) {
            @Override public @NotNull ItemStack makeIcon() {
                return iconSupplier.get();
            }
        };
    }
}