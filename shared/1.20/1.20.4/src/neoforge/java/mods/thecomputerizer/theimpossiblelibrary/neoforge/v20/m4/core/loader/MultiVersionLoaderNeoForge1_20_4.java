package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.MultiVersionLoaderNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader.MultiVersionLoaderNeoForge1_20;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

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
