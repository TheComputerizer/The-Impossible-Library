package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.NeoForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client.event.ClientEventsNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.event.CommonEventsNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.network.NetworkNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.registry.RegistryHandlerNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.server.MinecraftServerNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.server.event.ServerEventsNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.Client1_21;

import java.util.function.Supplier;

public abstract class ClientNeoForge1_21 extends Client1_21 {
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsNeoForge1_21::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsNeoForge1_21::new;
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkNeoForge1_21::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerNeoForge1_21::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerNeoForge1_21::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsNeoForge1_21::new;
    }
    
    @Override protected Supplier<SharedHandlesClient> initSharedHandlesClient() {
        return NeoForgeHandlesClient1_21::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return NeoForgeHandlesCommon::new;
    }
}