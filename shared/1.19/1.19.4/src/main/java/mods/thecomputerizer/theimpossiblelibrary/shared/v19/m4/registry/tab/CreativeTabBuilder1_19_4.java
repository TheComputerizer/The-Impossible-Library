package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.tab.CreativeTabBuilder1_19;

import java.util.Objects;

import static net.minecraft.world.item.CreativeModeTabs.TOOLS_AND_UTILITIES;

//TODO Finish implementing this
public class CreativeTabBuilder1_19_4 extends CreativeTabBuilder1_19 {
    
    @Override public CreativeTabAPI<?> build() {
        if(Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot build creative tab with null registry name!");
            return null;
        }
        return WrapperHelper.wrapTab(TOOLS_AND_UTILITIES);
    }
}
