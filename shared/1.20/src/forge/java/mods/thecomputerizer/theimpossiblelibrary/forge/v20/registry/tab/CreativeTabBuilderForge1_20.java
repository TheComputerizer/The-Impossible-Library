package mods.thecomputerizer.theimpossiblelibrary.forge.v20.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeTabBuilderForge1_20 extends CreativeTabBuilder1_20 {
    
    @Override protected FutureCreativeTab<CreativeModeTab> makeFutureTab() {
        return new FutureCreativeTabForge1_20(this.registryName);
    }
}
