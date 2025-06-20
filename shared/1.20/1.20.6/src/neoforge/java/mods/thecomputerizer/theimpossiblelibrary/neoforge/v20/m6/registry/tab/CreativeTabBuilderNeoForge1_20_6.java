package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeTabBuilderNeoForge1_20_6 extends CreativeTabBuilder1_20 {
    
    @Override protected FutureCreativeTab<CreativeModeTab> makeFutureTab() {
        return new FutureCreativeTabNeoForge1_20_6(this.registryName);
    }
}
