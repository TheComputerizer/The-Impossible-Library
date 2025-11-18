package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static net.minecraft.network.protocol.PacketFlow.CLIENTBOUND;
import static net.minecraft.network.protocol.PacketFlow.SERVERBOUND;

public abstract class MessageWrapper1_20_4<CTX> extends MessageWrapperAPI<ServerPlayer,CTX> implements CustomPacketPayload {
    
    protected static final String TYPE_BASE = "message_wrapper";
    
    public static <M extends MessageWrapper1_20_4<?>> M getPayloadInstance(Object dir) {
        return GenericUtils.cast(NetworkHelper.isDirToClient(dir) ? new Client<>() : new Server<>());
    }
    
    public static <M extends MessageWrapper1_20_4<?>> M getPayloadInstance(ByteBuf buf, Object dir) {
        return GenericUtils.cast(NetworkHelper.isDirToClient(dir) ? new Client<>(buf) : new Server<>(buf));
    }
    
    public static PacketFlow getClassFlow(Class<?> msgCls) {
        return Hacks.getFieldStatic(msgCls,"FLOW");
    }
    
    public static ResourceLocation getClassID(Class<?> msgCls) {
        return Hacks.getFieldStatic(msgCls,"ID");
    }
    
    public static <B extends ByteBuf,CTX,M extends MessageWrapper1_20_4<CTX>> Function<B,M> streamDecoder(
            Object dir) {
        return buf -> MessageWrapper1_20_4.getPayloadInstance(buf,dir);
    }
    
    protected MessageWrapper1_20_4() {
        super();
    }
    
    protected MessageWrapper1_20_4(ByteBuf buf) {
        super(buf);
    }
    
    public final void write(FriendlyByteBuf buf) {
        encode(buf);
    }
    
    public static final class Client<CTX> extends MessageWrapper1_20_4<CTX> {
        
        @IndirectCallers public static PacketFlow FLOW = CLIENTBOUND;
        public static ResourceLocation ID = TILRef.res(TYPE_BASE+"_client").unwrap();
        
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
    
    public static final class Server<CTX> extends MessageWrapper1_20_4<CTX> {
        
        @IndirectCallers public static PacketFlow FLOW = SERVERBOUND;
        public static ResourceLocation ID = TILRef.res(TYPE_BASE+"_server").unwrap();
        
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