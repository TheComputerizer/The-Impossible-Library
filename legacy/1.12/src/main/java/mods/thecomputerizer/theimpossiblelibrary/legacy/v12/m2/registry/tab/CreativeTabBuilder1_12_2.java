package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

import static net.minecraft.item.ItemStack.EMPTY;

public class CreativeTabBuilder1_12_2 extends CreativeTabBuilderAPI<ItemStack> {
    
    @Override public CreativeTabAPI<?> build() {
        if(Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot build creative tab with null registry name!");
            return null;
        }
        CreativeTabs tab = new CreativeTabs(this.registryName.getPath()) {
            @Override public @Nonnull ItemStack createIcon() {
                return getBuilderIcon();
            }
        };
        return WrapperHelper.wrapTab(tab);
    }
    
    ItemStack getBuilderIcon() {
        ItemStack icon = Objects.nonNull(this.icon) ? this.icon.get() : EMPTY;
        return Objects.nonNull(icon) ? icon : EMPTY;
    }
}