package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.network;

import io.github.toolfactory.jvm.function.template.TriConsumer;
import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.network.protocol.PacketFlow.CLIENTBOUND;
import static net.minecraft.network.protocol.PacketFlow.SERVERBOUND;

public abstract class MessageWrapperFabric1_20_4<CTX> extends MessageWrapperAPI<ServerPlayer,CTX> implements FabricPacket {
    
    protected static final String TYPE_BASE = "message_wrapper_fabric";
    
    public static <M extends MessageWrapperFabric1_20_4<?>> M getPayloadInstance(Object dir) {
        return GenericUtils.cast(NetworkHelper.isDirToClient(dir) ? new Client<>() : new Server<>());
    }
    
    static <M extends MessageWrapperAPI<?,PacketSender>,P extends Player> TriConsumer<M,P,PacketSender> handler() {
        return (msg,player,sender) -> {
            if(msg.handle(sender) instanceof FabricPacket reply) sender.sendPacket(sender.createPacket(reply));
        };
    }
    
    public static void register(boolean toClient, boolean toServer) {
        if(toClient && CoreAPI.isClient()) Client.register();
        if(toServer) Server.register();
    }
    
    protected MessageWrapperFabric1_20_4() {
        super();
    }
    
    protected MessageWrapperFabric1_20_4(ByteBuf buf) {
        super(buf);
    }
    
    public final void send() {
        send(null);
    }
    
    public abstract void send(@Nullable Player player);
    
    public final void write(FriendlyByteBuf buf) {
        encode(buf);
    }
    
    public static final class Client<CTX> extends MessageWrapperFabric1_20_4<CTX> {
        
        @IndirectCallers public static PacketFlow FLOW = CLIENTBOUND;
        public static PacketType<MessageWrapperFabric1_20_4.Client<?>> TYPE = PacketType.create(
                TILRef.res(TYPE_BASE+"_client").unwrap(),MessageWrapperFabric1_20_4.Client::new);
        
        static void register() {
            ClientPlayNetworking.registerGlobalReceiver(TYPE,(msg,player,sender) ->
                    handler().accept(GenericUtils.cast(msg),player,sender));
        }
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
        
        @Override public void send(@Nullable Player player) {
            if(player instanceof ServerPlayer sPlayer) ServerPlayNetworking.send(sPlayer,this);
        }
        
        @Override public @NotNull PacketType<MessageWrapperFabric1_20_4.Client<?>> getType() {
            return TYPE;
        }
    }
    
    public static final class Server<CTX> extends MessageWrapperFabric1_20_4<CTX> {
        
        @IndirectCallers public static PacketFlow FLOW = SERVERBOUND;
        public static PacketType<MessageWrapperFabric1_20_4.Server<?>> TYPE = PacketType.create(
                TILRef.res(TYPE_BASE+"_server").unwrap(),MessageWrapperFabric1_20_4.Server::new);
        
        static void register() {
            ServerPlayNetworking.registerGlobalReceiver(TYPE,(msg,player,sender) ->
                    handler().accept(GenericUtils.cast(msg),player,sender));
        }
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
        
        @Override public @NotNull PacketType<MessageWrapperFabric1_20_4.Server<?>> getType() {
            return TYPE;
        }
        
        @Override public void send(@Nullable Player player) {
            ClientPlayNetworking.send(this);
        }
    }
}