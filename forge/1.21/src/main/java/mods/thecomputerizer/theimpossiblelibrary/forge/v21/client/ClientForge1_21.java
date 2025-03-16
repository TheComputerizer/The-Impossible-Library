package mods.thecomputerizer.theimpossiblelibrary.forge.v21.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.ForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.event.ClientEventsForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.common.event.CommonEventsForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.network.NetworkForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.registry.RegistryHandlerForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.server.MinecraftServerForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.server.event.ServerEventsForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.Client1_21;

import java.util.function.Supplier;

public abstract class ClientForge1_21 extends Client1_21 {
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsForge1_21::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_21::new;
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_21::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerForge1_21::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerForge1_21::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsForge1_21::new;
    }
    
    @Override protected Supplier<SharedHandlesClient> initSharedHandlesClient() {
        return ForgeHandlesClient1_21::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return ForgeHandlesCommon::new;
    }
}