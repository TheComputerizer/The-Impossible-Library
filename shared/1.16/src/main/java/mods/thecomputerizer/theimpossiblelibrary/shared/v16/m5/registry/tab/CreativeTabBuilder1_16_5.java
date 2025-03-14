package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

import static net.minecraft.item.ItemStack.EMPTY;

public class CreativeTabBuilder1_16_5 extends CreativeTabBuilderAPI<ItemStack> {
    
    @Override public CreativeTabAPI<?> build() {
        if(Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot build creative tab with null registry name!");
            return null;
        }
        ItemGroup tab = new ItemGroup(this.registryName.getPath()) {
            @Override public @Nonnull ItemStack makeIcon() {
        
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