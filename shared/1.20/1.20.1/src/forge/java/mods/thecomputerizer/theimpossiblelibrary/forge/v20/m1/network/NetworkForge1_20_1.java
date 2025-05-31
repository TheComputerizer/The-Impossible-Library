package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.network;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.network.Network1_20;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static net.minecraftforge.network.NetworkDirection.LOGIN_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.LOGIN_TO_SERVER;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;
import static net.minecraftforge.network.PacketDistributor.PLAYER;

public class NetworkForge1_20_1 extends Network1_20<SimpleChannel,NetworkDirection> {

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

    @SuppressWarnings("unchecked") @Override public void registerMessage(MessageDirectionInfo<NetworkDirection> dir, int id) {
        getNetwork().registerMessage(id,(Class<MessageWrapperForge1_20_1>)MessageWrapperForge1_20_1.getClass(dir.getDirection()),
                MessageWrapperAPI::encode, buf -> MessageWrapperForge1_20_1.getInstance(dir.getDirection(), buf),
                (message,supplier) -> { //Response handler
                    Context context = supplier.get();
                    MessageWrapperForge1_20_1 wrapper = (MessageWrapperForge1_20_1)message.handle(context);
                    if(Objects.nonNull(wrapper)) {
                        if(!dir.isToClient()) wrapper.setPlayer(context.getSender());
                        wrapper.send();
                    }
                },Optional.of(dir.getDirection()));
    }
    
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().send(PLAYER.with(() -> (ServerPlayer)player),(MessageWrapperForge1_20_1)message);
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageWrapperForge1_20_1)message);
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(NetworkDirection dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_20_1.getInstance(dir);
        wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_20_1.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_20_1.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
}