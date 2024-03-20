package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource.ResourceLocationForge;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraftforge.fml.network.NetworkDirection.*;
import static org.apache.http.params.CoreProtocolPNames.PROTOCOL_VERSION;

public class NetworkForge implements NetworkAPI<SimpleChannel,NetworkDirection> {

    private SimpleChannel network;

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

    @SuppressWarnings("unchecked")
    @Override
    public <CONTEXT> MessageWrapperAPI<?, CONTEXT> wrapMessage(MessageAPI<CONTEXT> message) {
        MessageWrapperAPI<?,CONTEXT> wrapper = (MessageWrapperAPI<?,CONTEXT>)new MessageWrapperForge();
        wrapper.setMessage(message);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CONTEXT> MessageWrapperAPI<?, CONTEXT> wrapMessages(MessageAPI<CONTEXT>... messages) {
        MessageWrapperAPI<?,CONTEXT> wrapper = (MessageWrapperAPI<?,CONTEXT>)new MessageWrapperForge();
        wrapper.setMessages(messages);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CONTEXT> MessageWrapperAPI<?, CONTEXT> wrapMessages(Collection<MessageAPI<CONTEXT>> messages) {
        MessageWrapperAPI<?,CONTEXT> wrapper = (MessageWrapperAPI<?,CONTEXT>)new MessageWrapperForge();
        wrapper.setMessages(messages);
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
    public <B extends ByteBuf> ResourceLocationAPI<?> readResourceLocation(B buf) {
        return new ResourceLocationForge(new ResourceLocation(NetworkHelper.readString(buf)));
    }

    @Override
    public void registerMessage(Object handler) {

    }

    @Override
    public <SIDE, M extends MessageWrapperAPI<?, ?>> void registerMessage(int id, Class<M> clazz, SIDE side) {

    }

    @Override
    public <CONTEXT,B extends ByteBuf, M extends MessageWrapperAPI<?,?>> void registerMessage(
            int id, Class<M> clazz, BiConsumer<M,B> encoder, Function<B,M> decoder,
            BiConsumer<M,Supplier<CONTEXT>> handler, Optional<?> dir) {

    }

    @Override
    public ResourceLocationAPI<?> registerMessage(ResourceLocationAPI<?> resource, boolean toClient) {
        return null;
    }

    @Override
    public <P, M extends MessageWrapperAPI<?, ?>> void sendToPlayer(M message, P player) {
        getNetwork().send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player),(MessageWrapperForge)message);
    }

    @Override
    public <M extends MessageWrapperAPI<?, ?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageWrapperForge)message);
    }
}
