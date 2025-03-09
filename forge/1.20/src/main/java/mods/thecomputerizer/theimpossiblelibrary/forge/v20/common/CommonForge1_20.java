package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.ForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.network.NetworkForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.registry.RegistryHandlerForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.server.MinecraftServerForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.server.event.ServerEventsForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.Common1_20;

import java.util.function.Supplier;

public abstract class CommonForge1_20 extends Common1_20 {
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_20::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerForge1_20::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerForge1_20::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsForge1_20::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return ForgeHandlesCommon::new;
    }
}