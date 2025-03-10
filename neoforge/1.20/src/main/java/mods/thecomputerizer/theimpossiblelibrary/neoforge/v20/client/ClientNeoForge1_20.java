package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.NeoForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client.event.ClientEventsNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.registry.RegistryHandlerNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.server.MinecraftServerNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.server.event.ServerEventsNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.Client1_20;

import java.util.function.Supplier;

public abstract class ClientNeoForge1_20 extends Client1_20 {
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsNeoForge1_20::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerNeoForge1_20::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerNeoForge1_20::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsNeoForge1_20::new;
    }
    
    @Override protected Supplier<SharedHandlesClient> initSharedHandlesClient() {
        return NeoForgeHandlesClient1_20::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return NeoForgeHandlesCommon::new;
    }
}