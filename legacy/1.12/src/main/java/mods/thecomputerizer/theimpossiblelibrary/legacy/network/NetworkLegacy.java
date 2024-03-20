package mods.thecomputerizer.theimpossiblelibrary.legacy.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.resource.ResourceLocationLegacy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkLegacy implements NetworkAPI<SimpleNetworkWrapper,Side> { //TODO registerMessage

    private SimpleNetworkWrapper network;

    @Override
    public Side getDirToClient() {
        return Side.CLIENT;
    }

    @Override
    public Side getDirToClientLogin() {
        return Side.CLIENT;
    }

    @Override
    public Side getDirToServer() {
        return Side.SERVER;
    }

    @Override
    public Side getDirToServerLogin() {
        return Side.SERVER;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CONTEXT> MessageWrapperAPI<?,CONTEXT> wrapMessage(MessageAPI<CONTEXT> message) {
        MessageWrapperAPI<?,CONTEXT> wrapper = (MessageWrapperAPI<?,CONTEXT>)new MessageWrapperLegacy();
        wrapper.setMessage(message);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CONTEXT> MessageWrapperAPI<?,CONTEXT> wrapMessages(MessageAPI<CONTEXT> ... messages) {
        MessageWrapperAPI<?,CONTEXT> wrapper = (MessageWrapperAPI<?,CONTEXT>)new MessageWrapperLegacy();
        wrapper.setMessages(messages);
        return wrapper;
    }

    @Override
    public <CONTEXT> MessageWrapperAPI<?, CONTEXT> wrapMessages(Collection<MessageAPI<CONTEXT>> messages) {
        @SuppressWarnings("unchecked") MessageWrapperAPI<?,CONTEXT> wrapper = (MessageWrapperAPI<?,CONTEXT>)new MessageWrapperLegacy();
        wrapper.setMessages(messages);
        return wrapper;
    }

    @Override
    public SimpleNetworkWrapper getNetwork() {
        if(Objects.isNull(this.network)) this.network = NetworkRegistry.INSTANCE.newSimpleChannel(TILRef.MODID);
        return this.network;
    }

    @Override
    public boolean isDirToClient(Side side) {
        return side.isClient();
    }

    @Override
    public <B extends ByteBuf> ResourceLocationAPI<?> readResourceLocation(B buf) {
        return new ResourceLocationLegacy(new ResourceLocation(NetworkHelper.readString(buf)));
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
        getNetwork().sendTo((MessageWrapperLegacy)message,(EntityPlayerMP)player);
    }

    @Override
    public <M extends MessageWrapperAPI<?, ?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageWrapperLegacy)message);
    }
}
