package mods.thecomputerizer.theimpossiblelibrary.forge.v20.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.ForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.ClientEventsForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.registry.RegistryHandlerForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.server.MinecraftServerForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.server.event.ServerEventsForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.Client1_20;

import java.util.function.Supplier;

public abstract class ClientForge1_20 extends Client1_20 {
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsForge1_20::new;
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
    
    @Override protected Supplier<SharedHandlesClient> initSharedHandlesClient() {
        return ForgeHandlesClient1_20::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return ForgeHandlesCommon::new;
    }
}