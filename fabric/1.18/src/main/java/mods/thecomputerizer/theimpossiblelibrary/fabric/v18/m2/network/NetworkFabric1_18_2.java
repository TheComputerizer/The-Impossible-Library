package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.network;

import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.network.Network1_18_2;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.client.ClientNetworkingImpl;
import net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

/**
 * Fabric doesn't have mod specific network channels or network direction API classes...
 */
@SuppressWarnings("UnstableApiUsage")
public class NetworkFabric1_18_2 extends Network1_18_2<Object,Object> {
    
    FriendlyByteBuf encodeMessage(MessageWrapperAPI<?,?> message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        message.encode(buf);
        return buf;
    }

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
    
    ResourceLocation getRegistryName(MessageWrapperAPI<?,?> message) {
        return ((MessageWrapperFabric1_18_2)message).getRegistryName();
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
        ResourceLocation registryName = MessageWrapperFabric1_18_2.getInstance(dir).getRegistryName();
        ClientPlayNetworking.registerGlobalReceiver(registryName,(mc,handler,buf,sender) -> {
            MessageWrapperFabric1_18_2 wrapper = MessageWrapperFabric1_18_2.getInstance(dir,buf);
            MessageWrapperFabric1_18_2 response = (MessageWrapperFabric1_18_2)wrapper.handle(sender);
            if(Objects.nonNull(response)) response.send();
        });
    }
    
    void registerServerReceiver(MessageDirectionInfo<Object> info) {
        Object dir = info.getDirection();
        ResourceLocation registryName = MessageWrapperFabric1_18_2.getInstance(dir).getRegistryName();
        ServerPlayNetworking.registerGlobalReceiver(registryName, (server,player,handler,buf,sender) -> {
            MessageWrapperFabric1_18_2 wrapper = MessageWrapperFabric1_18_2.getInstance(dir,buf);
            MessageWrapperFabric1_18_2 response = (MessageWrapperFabric1_18_2)wrapper.handle(sender);
            if(Objects.nonNull(response)) {
                response.setPlayer(player);
                response.send();
            }
        });
    }
    
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        ServerPlayNetworking.send((ServerPlayer)player,getRegistryName(message),encodeMessage(message));
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        ClientPlayNetworking.send(getRegistryName(message),encodeMessage(message));
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(Object dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperFabric1_18_2.getInstance(dir);
        wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperFabric1_18_2.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperFabric1_18_2.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
}
