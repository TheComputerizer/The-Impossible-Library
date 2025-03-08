package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.network.MessageWrapperFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.network.Network1_20;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.client.ClientNetworkingImpl;
import net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

/**
 * Fabric doesn't have mod specific network channels or network direction API classes...
 */
@SuppressWarnings("UnstableApiUsage")
public class NetworkFabric1_20_6 extends Network1_20<Object,Object> {

    @Override public Object getDirFromName(String name) {
        return switch(name.toUpperCase()) {
            case "LOGIN_TO_CLIENT" -> ClientNetworkingImpl.LOGIN;
            case "LOGIN_TO_SERVER" -> ServerNetworkingImpl.LOGIN;
            case "PLAY_TO_SERVER" -> ServerNetworkingImpl.PLAY;
            default -> ClientNetworkingImpl.PLAY;
        };
    }

    @Override public String getNameFromDir(Object dir) {
        if(dir==ClientNetworkingImpl.LOGIN) return "LOGIN_TO_CLIENT";
        if(dir==ClientNetworkingImpl.PLAY) return "PLAY_TO_CLIENT";
        if(dir==ServerNetworkingImpl.LOGIN) return "LOGIN_TO_SERVER";
        return "PLAY_TO_SERVER";
    }

    @Override public Object getDirToClient() {
        return ClientNetworkingImpl.PLAY;
    }

    @Override public Object getDirToClientLogin() {
        return ClientNetworkingImpl.LOGIN;
    }

    @Override public Object getDirToServer() {
        return ServerNetworkingImpl.PLAY;
    }

    @Override public Object getDirToServerLogin() {
        return ServerNetworkingImpl.LOGIN;
    }
    
    @Override public Object getNetwork() {
        return null; //Nothing to register or get
    }

    @Override public @Nullable Object getOppositeDir(Object dir) {
        if(dir==ClientNetworkingImpl.LOGIN) return ServerNetworkingImpl.LOGIN;
        if(dir==ClientNetworkingImpl.PLAY) return ServerNetworkingImpl.PLAY;
        if(dir==ServerNetworkingImpl.LOGIN) return ClientNetworkingImpl.LOGIN;
        return ClientNetworkingImpl.PLAY;
    }

    @Override public boolean isDirToClient(Object dir) {
        return dir==ClientNetworkingImpl.PLAY || dir==ClientNetworkingImpl.LOGIN;
    }

    @Override public boolean isDirLogin(Object dir) {
        return dir==ClientNetworkingImpl.LOGIN || dir==ServerNetworkingImpl.LOGIN;
    }

    @Override public void registerMessage(MessageDirectionInfo<Object> dir, int id) {
        if(dir.isToClient()) registerClientReceiver(dir);
        else registerServerReceiver(dir);
    }
    
    void registerClientReceiver(MessageDirectionInfo<Object> info) {
        Object dir = info.getDirection();
        MessageWrapperFabric1_20_6 wrapper = MessageWrapperFabric1_20_6.getInstance(info.getDirection());
        Type<MessageWrapperFabric1_20_6> type = wrapper.type();
        PayloadTypeRegistry.playS2C().register(type,streamCodec(dir));
        ClientPlayNetworking.registerGlobalReceiver(type,(payload,ctx) -> {
            MessageWrapperFabric1_20_6 response = (MessageWrapperFabric1_20_6)payload.handle(ctx.responseSender());
            if(Objects.nonNull(response)) response.send();
        });
    }
    
    void registerServerReceiver(MessageDirectionInfo<Object> info) {
        Object dir = info.getDirection();
        MessageWrapperFabric1_20_6 wrapper = MessageWrapperFabric1_20_6.getInstance(dir);
        Type<MessageWrapperFabric1_20_6> type = wrapper.type();
        PayloadTypeRegistry.playC2S().register(type,streamCodec(dir));
        ServerPlayNetworking.registerGlobalReceiver(type,(payload,ctx) -> {
            MessageWrapperFabric1_20_6 response = (MessageWrapperFabric1_20_6)payload.handle(ctx.responseSender());
            if(Objects.nonNull(response)) {
                response.setPlayer(ctx.player());
                response.send();
            }
        });
    }
    
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        ServerPlayNetworking.send((ServerPlayer)player,(MessageWrapperFabric1_20_6)message);
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        ClientPlayNetworking.send((MessageWrapperFabric1_20_6)message);
    }
    
    protected <B extends ByteBuf> StreamCodec<B,MessageWrapperFabric1_20_6> streamCodec(Object dir) {
        StreamEncoder<B,MessageWrapperFabric1_20_6> encoder = (buf,payload) -> payload.encode(buf);
        StreamDecoder<B,MessageWrapperFabric1_20_6> decoder = buf -> MessageWrapperFabric1_20_6.getInstance(dir,buf);
        return StreamCodec.of(encoder,decoder);
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(Object dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperFabric1_20.getInstance(dir);
        wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperFabric1_20.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperFabric1_20.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
}
