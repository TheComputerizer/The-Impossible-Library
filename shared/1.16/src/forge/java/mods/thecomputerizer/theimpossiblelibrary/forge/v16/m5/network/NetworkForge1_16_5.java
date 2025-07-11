package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.network;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.network.Network1_16_5;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraftforge.fml.network.NetworkDirection.*;
import static net.minecraftforge.fml.network.PacketDistributor.PLAYER;

public class NetworkForge1_16_5 extends Network1_16_5<SimpleChannel,NetworkDirection> {

    private SimpleChannel network;

    @Override public NetworkDirection getDirFromName(String name) {
        switch(name.toUpperCase()) {
            case "LOGIN_TO_CLIENT" : return LOGIN_TO_CLIENT;
            case "PLAY_TO_SERVER" : return PLAY_TO_SERVER;
            case "LOGIN_TO_SERVER" : return LOGIN_TO_SERVER;
            default: return PLAY_TO_CLIENT;
        }
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
        switch(dir) {
            case PLAY_TO_CLIENT: return PLAY_TO_SERVER;
            case PLAY_TO_SERVER: return PLAY_TO_CLIENT;
            case LOGIN_TO_CLIENT: return LOGIN_TO_SERVER;
            case LOGIN_TO_SERVER: return LOGIN_TO_CLIENT;
            default: return null;
        }
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
        NetworkDirection direction = dir.getDirection();
        Class<MessageWrapperForge1_16_5> msgClass = GenericUtils.cast(MessageWrapperForge1_16_5.getClass(direction));
        BiConsumer<MessageWrapperForge1_16_5,FriendlyByteBuf> encoder = MessageWrapperAPI::encode;
        Function<FriendlyByteBuf,MessageWrapperForge1_16_5> decoder =
                buf -> MessageWrapperForge1_16_5.getInstance(NetworkHelper.getOppositeDir(direction),buf);
        BiConsumer<MessageWrapperForge1_16_5,Supplier<Context>> handler =
                (message,supplier) -> {
            Context context = supplier.get();
            MessageWrapperAPI<?,Context> response = message.handle(context);
            if(Objects.nonNull(response)) {
                if(!dir.isToClient()) ((MessageWrapperForge1_16_5)response).setPlayer(context.getSender());
                response.send();
            }
        };
        getNetwork().messageBuilder(msgClass,id,direction).encoder(encoder).decoder(decoder).consumer(handler).add();
    }
    
    //TODO Does not support login direction
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().send(PLAYER.with(() -> (ServerPlayer)player), (MessageWrapperForge1_16_5)message);
    }
    
    //TODO Does not support login direction
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageWrapperForge1_16_5)message);
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(NetworkDirection dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_16_5.getInstance(dir);
        wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_16_5.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_16_5.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
}