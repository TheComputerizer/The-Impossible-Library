package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.tab.CreativeTabBuilder1_21;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeTabBuilderNeoForge1_21 extends CreativeTabBuilder1_21 {
    
    @Override protected FutureCreativeTab<CreativeModeTab> makeFutureTab() {
        return new FutureCreativeTabNeoForge1_21(this.registryName);
    }
}
