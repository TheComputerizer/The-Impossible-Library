package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource.ResourceLocationForge;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static net.minecraftforge.fml.network.NetworkDirection.*;
import static org.apache.http.params.CoreProtocolPNames.PROTOCOL_VERSION;

public class NetworkForge implements NetworkAPI<SimpleChannel,NetworkDirection> {

    private SimpleChannel network;

    @Override
    public NetworkDirection getDirFromName(String name) {
        switch(name.toUpperCase()) {
            case "LOGIN_TO_CLIENT" : return LOGIN_TO_CLIENT;
            case "PLAY_TO_SERVER" : return PLAY_TO_SERVER;
            case "LOGIN_TO_SERVER" : return LOGIN_TO_SERVER;
            default: return PLAY_TO_CLIENT;
        }
    }

    @Override
    public String getNameFromDir(NetworkDirection dir) {
        return dir.name();
    }

    @Override
    public NetworkDirection getDirToClient() {
        return PLAY_TO_CLIENT;
    }

    @Override
    public NetworkDirection getDirToClientLogin() {
        return LOGIN_TO_CLIENT;
    }

    @Override
    public NetworkDirection getDirToServer() {
        return PLAY_TO_SERVER;
    }

    @Override
    public NetworkDirection getDirToServerLogin() {
        return LOGIN_TO_SERVER;
    }

    @Override
    public @Nullable NetworkDirection getOppositeDir(NetworkDirection dir) {
        switch(dir) {
            case PLAY_TO_CLIENT: return PLAY_TO_SERVER;
            case PLAY_TO_SERVER: return PLAY_TO_CLIENT;
            case LOGIN_TO_CLIENT: return LOGIN_TO_SERVER;
            case LOGIN_TO_SERVER: return LOGIN_TO_CLIENT;
            default: return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(NetworkDirection dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapperForge();
        wrapper.setMessage(dir,message);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapperForge();
        wrapper.setMessages(dir,messages);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapperForge();
        wrapper.setMessages(dir,messages);
        return wrapper;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SimpleChannel getNetwork() {
        if(Objects.isNull(this.network))
            this.network = NetworkRegistry.ChannelBuilder.named((ResourceLocation)TILRef.res("main_network").get())
                .clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
        return this.network;
    }

    @Override
    public boolean isDirToClient(NetworkDirection dir) {
        return dir==PLAY_TO_CLIENT || dir==LOGIN_TO_CLIENT;
    }

    @Override
    public boolean isDirLogin(NetworkDirection dir) {
        return dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
    }

    @Override
    public <B extends ByteBuf> ResourceLocationAPI<?> readResourceLocation(B buf) {
        return new ResourceLocationForge(new ResourceLocation(NetworkHelper.readString(buf)));
    }

    @Override
    public void registerMessage(MessageDirectionInfo<NetworkDirection> dir, int id) {
        getNetwork().registerMessage(id,MessageWrapperForge.class,MessageWrapperAPI::encode,MessageWrapperForge::new,
                (message,supplier) -> message.handle(supplier.get()),Optional.of(dir.getDirection()));
    }

    @Override
    public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player),(MessageWrapperForge)message);
    }

    @Override
    public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageWrapperForge)message);
    }
}
