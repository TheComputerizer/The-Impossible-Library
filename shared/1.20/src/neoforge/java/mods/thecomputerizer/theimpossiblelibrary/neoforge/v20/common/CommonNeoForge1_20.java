package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.NeoForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.registry.RegistryHandlerNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.server.MinecraftServerNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.Common1_20;

import java.util.function.Supplier;

public abstract class CommonNeoForge1_20 extends Common1_20 {
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerNeoForge1_20::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerNeoForge1_20::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return NeoForgeHandlesCommon::new;
    }
}