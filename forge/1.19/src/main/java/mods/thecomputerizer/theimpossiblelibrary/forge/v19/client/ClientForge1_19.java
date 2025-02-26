package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client;

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
import mods.thecomputerizer.theimpossiblelibrary.forge.common.ForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.ClientEventsForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.CommonEventsForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.integration.ModHelperForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.network.NetworkForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.registry.RegistryHandlerForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.server.MinecraftServerForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.server.event.ServerEventsForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.Client1_19;

import java.util.function.Supplier;

public class ClientForge1_19 extends Client1_19 {
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsForge1_19::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_19::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_19(CoreAPI.getInstance().getSide());
    }
    
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
    
    @Override protected Supplier<SharedHandlesClient> initSharedHandlesClient() {
        return ForgeHandlesClient1_19::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return ForgeHandlesCommon::new;
    }
}