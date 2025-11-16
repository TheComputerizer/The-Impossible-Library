package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public abstract class MessageWrapperNeoForge1_20_4 extends MessageWrapperAPI<ServerPlayer,IPayloadContext> implements CustomPacketPayload {
    
    public static <M extends MessageWrapperNeoForge1_20_4> M getNeoInstance(Object dir) {
        return GenericUtils.cast(NetworkHelper.isDirToClient(dir) ? new Client() : new Server());
    }
    
    public static <M extends MessageWrapperNeoForge1_20_4> M getNeoInstance(ByteBuf buf, Class<M> msgCls) {
        return GenericUtils.cast(isClient(msgCls) ? new Client(buf) : new Server(buf));
    }
    
    public static boolean isClient(Class<?> c) {
        return c==Client.class;
    }
    
    public static boolean isServer(Class<?> c) {
        return c==Server.class;
    }
    
    private MessageWrapperNeoForge1_20_4() {
        super();
    }
    
    private MessageWrapperNeoForge1_20_4(ByteBuf buf) {
        super(buf);
    }
    
    
    @Override public void write(@NotNull FriendlyByteBuf buf) {
        encode(buf);
    }
    
    /**
     * Sent from the server
     * Received on the client
     */
    public static final class Client extends MessageWrapperNeoForge1_20_4 {
        
        static final ResourceLocation ID = TILRef.res("message_wrapper_neoforge_client").unwrap();
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull ResourceLocation id() {
            return ID;
        }
    }
    
    /**
     * Sent from the client
     * Received on the server
     */
    public static final class Server extends MessageWrapperNeoForge1_20_4 {
        
        static final ResourceLocation ID = TILRef.res("message_wrapper_neoforge_server").unwrap();
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull ResourceLocation id() {
            return ID;
        }
    }
}