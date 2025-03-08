package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.impl.networking.client.ClientNetworkingImpl;
import net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

@SuppressWarnings("UnstableApiUsage")
public abstract class MessageWrapperFabric1_20_4 extends MessageWrapperAPI<ServerPlayer,PacketSender> {
    
    public static MessageWrapperFabric1_20_4 getInstance(Object dir) {
        boolean client = dir==ClientNetworkingImpl.PLAY || dir==ClientNetworkingImpl.LOGIN;
        boolean login = dir==ClientNetworkingImpl.LOGIN || dir==ServerNetworkingImpl.LOGIN;
        return login ? (client ? new ClientLogin() : new ServerLogin()) : (client ? new Client() : new Server());
    }
    
    public static MessageWrapperFabric1_20_4 getInstance(Object dir, ByteBuf buf) {
        boolean client = dir==ClientNetworkingImpl.PLAY || dir==ClientNetworkingImpl.LOGIN;
        boolean login = dir==ClientNetworkingImpl.LOGIN || dir==ServerNetworkingImpl.LOGIN;
        return login ? (client ? new ClientLogin(buf) : new ServerLogin(buf)) :
                (client ? new Client(buf) : new Server(buf));
    }
    
    public static Class<? extends MessageWrapperFabric1_20_4> getClass(Object dir) {
        boolean client = dir==ClientNetworkingImpl.PLAY || dir==ClientNetworkingImpl.LOGIN;
        boolean login = dir==ClientNetworkingImpl.LOGIN || dir==ServerNetworkingImpl.LOGIN;
        return login ? (client ? ClientLogin.class : ServerLogin.class) : (client ? Client.class : Server.class);
    }
    
    MessageWrapperFabric1_20_4() {
        super();
    }
    
    MessageWrapperFabric1_20_4(ByteBuf buf) {
        super(buf);
    }
    
    public abstract ResourceLocation getRegistryName();
    
    public static final class Client extends MessageWrapperFabric1_20_4 {
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
        
        @Override public ResourceLocation getRegistryName() {
            return new ResourceLocation(MODID,"message_play_to_client");
        }
    }
    
    public static final class ClientLogin extends MessageWrapperFabric1_20_4 {
        
        ClientLogin() {
            super();
        }
        
        ClientLogin(ByteBuf buf) {
            super(buf);
        }
        
        @Override public ResourceLocation getRegistryName() {
            return new ResourceLocation(MODID,"message_login_to_client");
        }
    }
    
    public static final class Server extends MessageWrapperFabric1_20_4 {
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
        
        @Override public ResourceLocation getRegistryName() {
            return new ResourceLocation(MODID,"message_play_to_server");
        }
    }
    
    public static final class ServerLogin extends MessageWrapperFabric1_20_4 {
        
        ServerLogin() {
            super();
        }
        
        ServerLogin(ByteBuf buf) {
            super(buf);
        }
        
        @Override public ResourceLocation getRegistryName() {
            return new ResourceLocation(MODID,"message_login_to_server");
        }
    }
}