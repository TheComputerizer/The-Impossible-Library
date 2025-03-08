package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.impl.networking.client.ClientNetworkingImpl;
import net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

@SuppressWarnings("UnstableApiUsage")
public abstract class MessageWrapperFabric1_20_6 extends MessageWrapperAPI<ServerPlayer,PacketSender> implements CustomPacketPayload {
    
    public static MessageWrapperFabric1_20_6 getInstance(Object dir) {
        boolean client = dir==ClientNetworkingImpl.PLAY || dir==ClientNetworkingImpl.LOGIN;
        boolean login = dir==ClientNetworkingImpl.LOGIN || dir==ServerNetworkingImpl.LOGIN;
        return login ? (client ? new ClientLogin() : new ServerLogin()) : (client ? new Client() : new Server());
    }
    
    public static MessageWrapperFabric1_20_6 getInstance(Object dir, ByteBuf buf) {
        boolean client = dir==ClientNetworkingImpl.PLAY || dir==ClientNetworkingImpl.LOGIN;
        boolean login = dir==ClientNetworkingImpl.LOGIN || dir==ServerNetworkingImpl.LOGIN;
        return login ? (client ? new ClientLogin(buf) : new ServerLogin(buf)) :
                (client ? new Client(buf) : new Server(buf));
    }
    
    public static Class<? extends MessageWrapperFabric1_20_6> getClass(Object dir) {
        boolean client = dir==ClientNetworkingImpl.PLAY || dir==ClientNetworkingImpl.LOGIN;
        boolean login = dir==ClientNetworkingImpl.LOGIN || dir==ServerNetworkingImpl.LOGIN;
        return login ? (client ? ClientLogin.class : ServerLogin.class) : (client ? Client.class : Server.class);
    }
    
    MessageWrapperFabric1_20_6() {
        super();
    }
    
    MessageWrapperFabric1_20_6(ByteBuf buf) {
        super(buf);
    }
    
    @Override public abstract @NotNull Type<MessageWrapperFabric1_20_6> type();
    
    public static final class Client extends MessageWrapperFabric1_20_6 {
        
        static ResourceLocation ID = new ResourceLocation(MODID,"message_play_to_client");
        static Type<MessageWrapperFabric1_20_6> TYPE = new Type<>(ID);
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<MessageWrapperFabric1_20_6> type() {
            return TYPE;
        }
    }
    
    public static final class ClientLogin extends MessageWrapperFabric1_20_6 {
        
        static ResourceLocation ID = new ResourceLocation(MODID,"message_login_to_client");
        static Type<MessageWrapperFabric1_20_6> TYPE = new Type<>(ID);
        
        ClientLogin() {
            super();
        }
        
        ClientLogin(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<MessageWrapperFabric1_20_6> type() {
            return TYPE;
        }
    }
    
    public static final class Server extends MessageWrapperFabric1_20_6 {
        
        static ResourceLocation ID = new ResourceLocation(MODID,"message_play_to_server");
        static Type<MessageWrapperFabric1_20_6> TYPE = new Type<>(ID);
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<MessageWrapperFabric1_20_6> type() {
            return TYPE;
        }
    }
    
    public static final class ServerLogin extends MessageWrapperFabric1_20_6 {
        
        static ResourceLocation ID = new ResourceLocation(MODID,"message_login_to_server");
        static Type<MessageWrapperFabric1_20_6> TYPE = new Type<>(ID);
        
        ServerLogin() {
            super();
        }
        
        ServerLogin(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<MessageWrapperFabric1_20_6> type() {
            return TYPE;
        }
    }
}