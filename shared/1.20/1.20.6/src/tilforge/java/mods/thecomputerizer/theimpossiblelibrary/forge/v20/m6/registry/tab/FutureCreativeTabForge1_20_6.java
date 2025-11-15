package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.registry.tab.FutureCreativeTabForge1_20;

import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;

public class FutureCreativeTabForge1_20_6 extends FutureCreativeTabForge1_20 {
    
    public FutureCreativeTabForge1_20_6(ResourceLocationAPI<?> registryName) {
        super(registryName,() -> CREATIVE_MODE_TAB);
    }
}
