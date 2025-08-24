package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader.MultiVersionLoaderNeoForge1_20;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.javafmlmod.FMLModContainer;

import java.util.Optional;

public class MultiVersionLoaderNeoForge1_20_4 extends MultiVersionLoaderNeoForge1_20 {
    
    public MultiVersionLoaderNeoForge1_20_4(CoreAPI parent) {
        super(parent);
    }
    
    @IndirectCallers
    public FMLModContainer getModContainer(String modid) {
        ModList list = ModList.get();
        Optional<? extends ModContainer> container = list.getModContainerById(modid);
        return (FMLModContainer)container.orElse(null);
    }
}
