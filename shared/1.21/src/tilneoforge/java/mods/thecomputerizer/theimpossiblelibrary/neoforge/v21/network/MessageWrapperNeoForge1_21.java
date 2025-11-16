package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public abstract class MessageWrapperNeoForge1_21 extends MessageWrapperAPI<ServerPlayer,IPayloadContext> implements CustomPacketPayload {
    
    private static final String TYPE_BASE = "message_wrapper_neoforge";
    
    public static <M extends MessageWrapperNeoForge1_21> M getNeoInstance(Object dir) {
        return GenericUtils.cast(NetworkHelper.isDirToClient(dir) ? new Client() : new Server());
    }
    
    public static <M extends MessageWrapperNeoForge1_21> M getNeoInstance(ByteBuf buf, Object dir) {
        return GenericUtils.cast(NetworkHelper.isDirToClient(dir) ? new Client(buf) : new Server(buf));
    }
    
    private MessageWrapperNeoForge1_21() {
        super();
    }
    
    private MessageWrapperNeoForge1_21(ByteBuf buf) {
        super(buf);
    }
    
    /**
     * Sent from the server
     * Received on the client
     */
    public static final class Client extends MessageWrapperNeoForge1_21 {
        
        static Type<MessageWrapperNeoForge1_21.Client> TYPE = new Type<>(TILRef.res(TYPE_BASE+"_client").unwrap());
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
    
    /**
     * Sent from the client
     * Received on the server
     */
    public static final class Server extends MessageWrapperNeoForge1_21 {
        
        static Type<MessageWrapperNeoForge1_21.Server> TYPE = new Type<>(TILRef.res(TYPE_BASE+"_server").unwrap());
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}