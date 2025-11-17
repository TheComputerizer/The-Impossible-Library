package mods.thecomputerizer.theimpossiblelibrary.shared.v21.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.network.protocol.PacketFlow.CLIENTBOUND;
import static net.minecraft.network.protocol.PacketFlow.SERVERBOUND;

public abstract class MessageWrapper1_21<CTX> extends MessageWrapperAPI<ServerPlayer,CTX> implements CustomPacketPayload {
    
    protected static final String TYPE_BASE = "message_wrapper_forgelike";
    
    public static <M extends MessageWrapper1_21<?>> M getPayloadInstance(Object dir) {
        return GenericUtils.cast(NetworkHelper.isDirToClient(dir) ? new Client<>() : new Server<>());
    }
    
    public static <M extends MessageWrapper1_21<?>> M getPayloadInstance(ByteBuf buf, Object dir) {
        return GenericUtils.cast(NetworkHelper.isDirToClient(dir) ? new Client<>(buf) : new Server<>(buf));
    }
    
    public static PacketFlow getClassFlow(Class<?> msgCls) {
        return Hacks.getFieldStatic(msgCls,"FLOW");
    }
    
    public static <CTX,M extends MessageWrapper1_21<CTX>> Type<M> getClassType(Class<?> msgCls) {
        return Hacks.getFieldStatic(msgCls,"TYPE");
    }
    
    public static <B extends ByteBuf,CTX,M extends MessageWrapper1_21<CTX>> StreamCodec<B,M> streamCodec(
            Object dir) {
        final StreamEncoder<B,M> encoder = (buf,msg) -> msg.encode(buf);
        final StreamDecoder<B,M> decoder = buf -> MessageWrapper1_21.getPayloadInstance(buf,dir);
        return StreamCodec.of(encoder,decoder);
    }
    
    protected MessageWrapper1_21() {
        super();
    }
    
    protected MessageWrapper1_21(ByteBuf buf) {
        super(buf);
    }
    
    public static final class Client<CTX> extends MessageWrapper1_21<CTX> {
        
        @IndirectCallers public static PacketFlow FLOW = CLIENTBOUND;
        public static Type<MessageWrapper1_21.Client<?>> TYPE = new Type<>(TILRef.res(TYPE_BASE+"_client").unwrap());
        
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
    
    public static final class Server<CTX> extends MessageWrapper1_21<CTX> {
        
        @IndirectCallers public static PacketFlow FLOW = SERVERBOUND;
        public static Type<MessageWrapper1_21.Client<?>> TYPE = new Type<>(TILRef.res(TYPE_BASE+"_server").unwrap());
        
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