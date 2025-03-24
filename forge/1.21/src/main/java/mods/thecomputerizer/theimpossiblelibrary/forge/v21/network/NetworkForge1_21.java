package mods.thecomputerizer.theimpossiblelibrary.forge.v21.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.network.Network1_21;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.forge.v21.network.MessageWrapperForge1_21.TYPE;
import static net.minecraftforge.network.NetworkDirection.*;
import static net.minecraftforge.network.PacketDistributor.PLAYER;
import static net.minecraftforge.network.PacketDistributor.SERVER;

public class NetworkForge1_21 extends Network1_21<Channel<CustomPacketPayload>,NetworkDirection<?>> {
    
    static <B extends ByteBuf> StreamCodec<B,MessageWrapperForge1_21> streamCodec() {
        return StreamCodec.of((buf,payload) -> payload.encode(buf),
                              MessageWrapperForge1_21::getInstance);
    }
    
    private Channel<CustomPacketPayload> network;

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
            ResourceLocation name = TILRef.res("main_network").unwrap();
            this.network = ChannelBuilder.named(name)
                    .clientAcceptedVersions((status,version) -> true)
                    .serverAcceptedVersions((status,version) -> true).networkProtocolVersion(1)
                    .payloadChannel().any().bidirectional()
                    .add(TYPE,streamCodec(),MessageWrapperForge1_21::handle).build();
        }
        return this.network;
    }

    @Override public boolean isDirToClient(NetworkDirection<?> dir) {
        return dir==PLAY_TO_CLIENT || dir==LOGIN_TO_CLIENT;
    }

    @Override public boolean isDirLogin(NetworkDirection<?> dir) {
        return dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
    }

    @Override public void registerMessage(MessageDirectionInfo<NetworkDirection<?>> dir, int id) {}
    
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().send((MessageWrapperForge1_21)message,PLAYER.with((ServerPlayer)player));
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().send((MessageWrapperForge1_21)message,SERVER.noArg());
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(NetworkDirection<?> dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_21.getInstance();
        wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection<?> dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_21.getInstance();
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection<?> dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_21.getInstance();
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
}