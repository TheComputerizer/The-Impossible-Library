package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.ForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.network.NetworkForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.registry.RegistryHandlerForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.server.MinecraftServerForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.server.event.ServerEventsForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.Common1_19;

import java.util.function.Supplier;

public abstract class CommonForge1_19 extends Common1_19 {
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_19::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerForge1_19::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerForge1_19::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsForge1_19::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return ForgeHandlesCommon::new;
    }
}