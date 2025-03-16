package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static net.minecraft.network.protocol.PacketFlow.CLIENTBOUND;

public abstract class MessageWrapperNeoForge1_21 extends MessageWrapperAPI<ServerPlayer,IPayloadContext> implements CustomPacketPayload {
    
    public static MessageWrapperNeoForge1_21 getInstance(Object dir, boolean login) {
        boolean client = dir==CLIENTBOUND;
        return login ? (client ? new ClientLogin() : new ServerLogin()) : (client ? new Client() : new Server());
    }
    
    public static MessageWrapperNeoForge1_21 getInstance(Object dir, boolean login, ByteBuf buf) {
        boolean client = dir==CLIENTBOUND;
        return login ? (client ? new ClientLogin(buf) : new ServerLogin(buf)) :
                (client ? new Client(buf) : new Server(buf));
    }
    
    MessageWrapperNeoForge1_21() {
        super();
    }
    
    MessageWrapperNeoForge1_21(ByteBuf buf) {
        super(buf);
    }
    
    @Override public abstract @NotNull Type<MessageWrapperNeoForge1_21> type();
    
    public static final class Client extends MessageWrapperNeoForge1_21 {
        
        static ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID,"message_play_to_client");
        static Type<MessageWrapperNeoForge1_21> TYPE = new Type<>(ID);
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<MessageWrapperNeoForge1_21> type() {
            return TYPE;
        }
    }
    
    public static final class ClientLogin extends MessageWrapperNeoForge1_21 {
        
        static ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID,"message_login_to_client");
        static Type<MessageWrapperNeoForge1_21> TYPE = new Type<>(ID);
        
        ClientLogin() {
            super();
        }
        
        ClientLogin(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<MessageWrapperNeoForge1_21> type() {
            return TYPE;
        }
    }
    
    public static final class Server extends MessageWrapperNeoForge1_21 {
        
        static ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID,"message_play_to_server");
        static Type<MessageWrapperNeoForge1_21> TYPE = new Type<>(ID);
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<MessageWrapperNeoForge1_21> type() {
            return TYPE;
        }
    }
    
    public static final class ServerLogin extends MessageWrapperNeoForge1_21 {
        
        static ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID,"message_login_to_server");
        static Type<MessageWrapperNeoForge1_21> TYPE = new Type<>(ID);
        
        ServerLogin() {
            super();
        }
        
        ServerLogin(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<MessageWrapperNeoForge1_21> type() {
            return TYPE;
        }
    }
}