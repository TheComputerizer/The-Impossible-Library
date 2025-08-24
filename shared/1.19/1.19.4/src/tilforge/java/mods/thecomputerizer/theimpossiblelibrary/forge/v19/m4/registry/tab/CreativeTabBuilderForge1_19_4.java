package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab.CreativeTabBuilder1_19_4;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeTabBuilderForge1_19_4 extends CreativeTabBuilder1_19_4 {
    
    @Override protected FutureCreativeTab<CreativeModeTab> makeFutureTab() {
        return new FutureCreativeTabForge1_19_4(this.registryName);
    }
}
