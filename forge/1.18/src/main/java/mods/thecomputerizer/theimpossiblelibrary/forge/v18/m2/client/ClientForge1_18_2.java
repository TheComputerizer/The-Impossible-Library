package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.ForgeHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.ForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client.event.ClientEventsForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.CommonEventsForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.integration.ModHelperForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.network.NetworkForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.registry.RegistryHandlerForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.server.MinecraftServerForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.server.event.ServerEventsForge1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.Client1_18_2;

import java.util.function.Supplier;

public class ClientForge1_18_2 extends Client1_18_2 {
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsForge1_18_2::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_18_2::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_18_2(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_18_2::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerForge1_18_2::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerForge1_18_2::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsForge1_18_2::new;
    }
    
    @Override protected Supplier<SharedHandlesClient> initSharedHandlesClient() {
        return ForgeHandlesClient::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return ForgeHandlesCommon::new;
    }
}