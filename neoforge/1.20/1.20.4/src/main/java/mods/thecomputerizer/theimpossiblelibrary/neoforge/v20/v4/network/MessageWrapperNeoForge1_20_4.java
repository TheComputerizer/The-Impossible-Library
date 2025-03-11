package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static net.minecraft.network.protocol.PacketFlow.CLIENTBOUND;

public abstract class MessageWrapperNeoForge1_20_4 extends MessageWrapperAPI<ServerPlayer,IPayloadContext> implements CustomPacketPayload {
    
    public static MessageWrapperNeoForge1_20_4 getInstance(Object dir, boolean login) {
        boolean client = dir==CLIENTBOUND;
        return login ? (client ? new ClientLogin() : new ServerLogin()) : (client ? new Client() : new Server());
    }
    
    public static MessageWrapperNeoForge1_20_4 getInstance(Object dir, boolean login, ByteBuf buf) {
        boolean client = dir==CLIENTBOUND;
        return login ? (client ? new ClientLogin(buf) : new ServerLogin(buf)) :
                (client ? new Client(buf) : new Server(buf));
    }
    
    public static Class<? extends MessageWrapperNeoForge1_20_4> getClass(Object dir, boolean login) {
        boolean client = dir==CLIENTBOUND;
        return login ? (client ? ClientLogin.class : ServerLogin.class) : (client ? Client.class : Server.class);
    }
    
    MessageWrapperNeoForge1_20_4() {
        super();
    }
    
    MessageWrapperNeoForge1_20_4(ByteBuf buf) {
        super(buf);
    }
    
    @Override public void write(@NotNull FriendlyByteBuf buf) {
        encode(buf);
    }
    
    public static final class Client extends MessageWrapperNeoForge1_20_4 {
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull ResourceLocation id() {
            return new ResourceLocation(MODID,"message_play_to_client");
        }
    }
    
    public static final class ClientLogin extends MessageWrapperNeoForge1_20_4 {
        
        ClientLogin() {
            super();
        }
        
        ClientLogin(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull ResourceLocation id() {
            return new ResourceLocation(MODID,"message_login_to_client");
        }
    }
    
    public static final class Server extends MessageWrapperNeoForge1_20_4 {
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull ResourceLocation id() {
            return new ResourceLocation(MODID,"message_play_to_server");
        }
    }
    
    public static final class ServerLogin extends MessageWrapperNeoForge1_20_4 {
        
        ServerLogin() {
            super();
        }
        
        ServerLogin(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull ResourceLocation id() {
            return new ResourceLocation(MODID,"message_login_to_server");
        }
    }
}