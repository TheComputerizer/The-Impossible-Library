package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.network;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.network.MessageWrapper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.network.MessageWrapper1_20_6.Client;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.network.MessageWrapper1_20_6.Server;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.network.Network1_20_6;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkProtocol;
import net.minecraftforge.network.payload.PayloadFlow;
import net.minecraftforge.network.payload.PayloadProtocol;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.BiConsumer;

import static net.minecraftforge.network.NetworkDirection.*;
import static net.minecraftforge.network.NetworkProtocol.CONFIGURATION;
import static net.minecraftforge.network.NetworkProtocol.LOGIN;
import static net.minecraftforge.network.NetworkProtocol.PLAY;
import static net.minecraftforge.network.PacketDistributor.PLAYER;
import static net.minecraftforge.network.PacketDistributor.SERVER;

public class NetworkForge1_20_6 extends Network1_20_6<Channel<CustomPacketPayload>,NetworkDirection<?>> {
    
    static <B extends FriendlyByteBuf,P extends CustomPacketPayload> PayloadFlow<B,P> addPayloads(
            PayloadFlow<B,P> flow, NetworkProtocol<B> protocol, Class<?> ... msgClasses) {
        for(Class<?> msgCls : msgClasses) flow = addPayload(flow,protocol,msgCls);
        return flow;
    }
    
    static <B extends FriendlyByteBuf,P extends CustomPacketPayload> PayloadFlow<B,P> addPayload(
            PayloadFlow<B,P> flow, NetworkProtocol<B> protocol, Class<?> msgCls) {
        NetworkDirection<B> dir = GenericUtils.cast(selectDir(protocol,msgCls));
        return Objects.nonNull(dir) ? addPayload(flow,dir,msgCls) : flow;
    }
    
    @SuppressWarnings("unchecked")
    static <B extends FriendlyByteBuf,P extends CustomPacketPayload,M extends MessageWrapper1_20_6<Context>> PayloadFlow<B,P> addPayload(
            PayloadFlow<B,P> flow, NetworkDirection<B> dir, Class<?> msgCls) {
        final Type<M> type = MessageWrapper1_20_6.getClassType(msgCls);
        final StreamCodec<B,M> codec = MessageWrapper1_20_6.streamCodec(dir);
        final BiConsumer<M,Context> handler = buildPayloadHandler();
        return GenericUtils.cast(((PayloadFlow<B,M>)flow.flow(MessageWrapper1_20_6.getClassFlow(msgCls)))
                                         .add(type,codec,handler));
    }
    
    static <M extends MessageWrapper1_20_6<Context>>BiConsumer<M,Context> buildPayloadHandler() {
        return (msg,ctx) -> {
            MessageWrapperAPI<?,Context> maybeReply = msg.handle(ctx);
            if(maybeReply instanceof MessageWrapper1_20_6<?> reply) {
                if(ctx.isServerSide()) reply.setPlayer(ctx.getSender());
                reply.send();
            }
            ctx.setPacketHandled(true);
        };
    }
    
    static Channel<CustomPacketPayload> registerPayloads(ChannelBuilder builder) {
        return registerPayloads(builder,PLAY,null);
    }
    
    @SuppressWarnings("SameParameterValue")
    static <B extends FriendlyByteBuf,P extends CustomPacketPayload> Channel<P> registerPayloads(
            ChannelBuilder builder, NetworkProtocol<B> protocol, @Nullable PacketFlow channelFlow) {
        final PayloadProtocol<B,P> protocolBuilder = builder.payloadChannel().protocol(protocol);
        PayloadFlow<B,P> flowBuilder = Objects.nonNull(channelFlow) ?
                protocolBuilder.flow(channelFlow) : protocolBuilder.bidirectional();
        return GenericUtils.cast(addPayloads(flowBuilder,protocol,Client.class,Server.class).build());
    }
    
    static NetworkDirection<?> selectDir(NetworkProtocol<?> protocol, Class<?> msgClass) {
        if(protocol==CONFIGURATION) return Client.class==msgClass ? CONFIGURATION_TO_CLIENT : CONFIGURATION_TO_SERVER;
        if(protocol==LOGIN) return Client.class==msgClass ? LOGIN_TO_CLIENT : LOGIN_TO_SERVER;
        return Client.class==msgClass ? PLAY_TO_CLIENT : PLAY_TO_SERVER;
    }
    
    private final Collection<MessageDirectionInfo<NetworkDirection<?>>> registeredDirs = new HashSet<>();
    private Channel<CustomPacketPayload> network;
    
    Channel<CustomPacketPayload> buildChannel(ResourceLocation name) {
        return registerPayloads(ChannelBuilder.named(name)
                        .clientAcceptedVersions((status,version) -> true)
                        .serverAcceptedVersions((status,version) -> true)
                        .networkProtocolVersion(1));
    }

    @Override public NetworkDirection<?> getDirFromName(String name) {
        return switch(name.toUpperCase()) {
            case "LOGIN_TO_CLIENT" -> LOGIN_TO_CLIENT;
            case "PLAY_TO_SERVER" -> PLAY_TO_SERVER;
            case "LOGIN_TO_SERVER" -> LOGIN_TO_SERVER;
            default -> PLAY_TO_CLIENT;
        };
    }

    @Override public String getNameFromDir(NetworkDirection<?> dir) {
        if(dir==PLAY_TO_CLIENT) return "PLAY_TO_CLIENT";
        if(dir==PLAY_TO_SERVER) return "PLAY_TO_SERVER";
        if(dir==LOGIN_TO_CLIENT) return "LOGIN_TO_CLIENT";
        if(dir==LOGIN_TO_SERVER) return "LOGIN_TO_SERVER";
        if(dir==CONFIGURATION_TO_CLIENT) return "CONFIGURATION_TO_CLIENT";
        return "CONFIGURATION_TO_SERVER";
    }

    @Override public NetworkDirection<?> getDirToClient() {
        return PLAY_TO_CLIENT;
    }

    @Override public NetworkDirection<?> getDirToClientLogin() {
        return LOGIN_TO_CLIENT;
    }

    @Override public NetworkDirection<?> getDirToServer() {
        return PLAY_TO_SERVER;
    }

    @Override public NetworkDirection<?> getDirToServerLogin() {
        return LOGIN_TO_SERVER;
    }

    @Override public @Nullable NetworkDirection<?> getOppositeDir(NetworkDirection<?> dir) {
        if(dir==PLAY_TO_CLIENT) return PLAY_TO_SERVER;
        if(dir==PLAY_TO_SERVER) return PLAY_TO_CLIENT;
        if(dir==LOGIN_TO_CLIENT) return LOGIN_TO_SERVER;
        return LOGIN_TO_CLIENT;
    }
    
    @Override public Channel<CustomPacketPayload> getNetwork() {
        if(Objects.isNull(this.network)) {
            if(this.registeredDirs.isEmpty()) return null;
            this.network = buildChannel(TILRef.res("main_network").unwrap());
        }
        return this.network;
    }

    @Override public boolean isDirToClient(NetworkDirection<?> dir) {
        return dir==PLAY_TO_CLIENT || dir==LOGIN_TO_CLIENT;
    }

    @Override public boolean isDirLogin(NetworkDirection<?> dir) {
        return dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
    }
    
    @Override public void messageRegistrationStarted() {
        this.registeredDirs.clear();
    }

    @Override public void registerMessage(MessageDirectionInfo<NetworkDirection<?>> dir, int id) {
        this.registeredDirs.add(dir);
    }
    
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().send((MessageWrapper1_20_6<?>)message,PLAYER.with((ServerPlayer)player));
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().send((MessageWrapper1_20_6<?>)message,SERVER.noArg());
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(NetworkDirection<?> dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = MessageWrapperAPI.getInstance(dir);
        if(Objects.nonNull(wrapper)) wrapper.setMessage(dir,message);
        else TILRef.logError("Null message wrapper for dir {}",dir);
        return wrapper;
    }
    
    @SafeVarargs
    @Override public final <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection<?> dir,
            MessageAPI<CTX>... messages) {
        MessageWrapperAPI<?,CTX> wrapper = MessageWrapperAPI.getInstance(dir);
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir,messages);
        else TILRef.logError("Null message wrapper for dir {}",dir);
        return wrapper;
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection<?> dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = MessageWrapperAPI.getInstance(dir);
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir,messages);
        else TILRef.logError("Null message wrapper for dir {}",dir);
        return wrapper;
    }
}