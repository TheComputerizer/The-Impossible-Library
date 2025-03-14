package mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

import static net.minecraft.world.item.CreativeModeTabs.TOOLS_AND_UTILITIES;

//TODO Finish implementing this
public class CreativeTabBuilder1_20 extends CreativeTabBuilderAPI<ItemStack> {
    
    @Override public CreativeTabAPI<?> build() {
        if(Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot build creative tab with null registry name!");
            return null;
        }
        return WrapperHelper.wrapTab(TOOLS_AND_UTILITIES);
    }
}