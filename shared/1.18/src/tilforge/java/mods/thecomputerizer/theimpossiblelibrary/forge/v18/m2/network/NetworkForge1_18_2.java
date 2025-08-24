package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.network;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.network.Network1_18_2;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import static net.minecraftforge.network.NetworkDirection.LOGIN_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.LOGIN_TO_SERVER;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;
import static net.minecraftforge.network.PacketDistributor.PLAYER;

public class NetworkForge1_18_2 extends Network1_18_2<SimpleChannel,NetworkDirection> {

    private SimpleChannel network;

    @Override public NetworkDirection getDirFromName(String name) {
        return switch(name.toUpperCase()) {
            case "LOGIN_TO_CLIENT" -> LOGIN_TO_CLIENT;
            case "PLAY_TO_SERVER" -> PLAY_TO_SERVER;
            case "LOGIN_TO_SERVER" -> LOGIN_TO_SERVER;
            default -> PLAY_TO_CLIENT;
        };
    }

    @Override public String getNameFromDir(NetworkDirection dir) {
        return dir.name();
    }

    @Override public NetworkDirection getDirToClient() {
        return PLAY_TO_CLIENT;
    }

    @Override public NetworkDirection getDirToClientLogin() {
        return LOGIN_TO_CLIENT;
    }

    @Override public NetworkDirection getDirToServer() {
        return PLAY_TO_SERVER;
    }

    @Override public NetworkDirection getDirToServerLogin() {
        return LOGIN_TO_SERVER;
    }

    @Override public @Nullable NetworkDirection getOppositeDir(NetworkDirection dir) {
        return switch(dir) {
            case PLAY_TO_CLIENT -> PLAY_TO_SERVER;
            case PLAY_TO_SERVER -> PLAY_TO_CLIENT;
            case LOGIN_TO_CLIENT -> LOGIN_TO_SERVER;
            case LOGIN_TO_SERVER -> LOGIN_TO_CLIENT;
        };
    }

    @Override public SimpleChannel getNetwork() {
        if(Objects.isNull(this.network))
            this.network = ChannelBuilder.named(TILRef.res("main_network").unwrap())
                    .clientAcceptedVersions(version -> true)
                    .serverAcceptedVersions(version -> true)
                    .networkProtocolVersion(TILRef::getNetworkVersion)
                    .simpleChannel();
        return this.network;
    }

    @Override public boolean isDirToClient(NetworkDirection dir) {
        return dir==PLAY_TO_CLIENT || dir==LOGIN_TO_CLIENT;
    }

    @Override public boolean isDirLogin(NetworkDirection dir) {
        return dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
    }

    @Override public void registerMessage(MessageDirectionInfo<NetworkDirection> dir, int id) {
        final Class<MessageWrapperAPI<Player,Context>> msgClass = MessageWrapperAPI.getClass(dir);
        getNetwork().messageBuilder(msgClass,id)
                .encoder(MessageWrapperAPI.encoder()).decoder(MessageWrapperAPI.decoder(dir))
                .consumer(MessageWrapperAPI.handler(dir,Supplier::get,Context::getSender))
                .add();
    }
    
    //TODO Does not support login direction
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().send(PLAYER.with(() -> (ServerPlayer)player),message);
    }
    
    //TODO Does not support login direction
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().sendToServer(message);
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(NetworkDirection dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = MessageWrapperAPI.getInstance(dir);
        if(Objects.nonNull(wrapper)) wrapper.setMessage(dir,message);
        else TILRef.logError("Null message wrapper for dir {}",dir);
        return wrapper;
    }
    
    @SafeVarargs
    @Override public final <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir,
            MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = MessageWrapperAPI.getInstance(dir);
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir,messages);
        else TILRef.logError("Null message wrapper for dir {}",dir);
        return wrapper;
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir,
            Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = MessageWrapperAPI.getInstance(dir);
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir,messages);
        else TILRef.logError("Null message wrapper for dir {}",dir);
        return wrapper;
    }
}