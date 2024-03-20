package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface NetworkAPI<N,DIR> {

    DIR getDirToClient();
    DIR getDirToClientLogin();
    DIR getDirToServer();
    DIR getDirToServerLogin();
    <CONTEXT> MessageWrapperAPI<?,CONTEXT> wrapMessage(MessageAPI<CONTEXT> message);
    @SuppressWarnings("unchecked")
    <CONTEXT> MessageWrapperAPI<?,CONTEXT> wrapMessages(MessageAPI<CONTEXT> ... messages);
    <CONTEXT> MessageWrapperAPI<?,CONTEXT> wrapMessages(Collection<MessageAPI<CONTEXT>> messages);
    N getNetwork();
    boolean isDirToClient(DIR d);
    <B extends ByteBuf> ResourceLocationAPI<?> readResourceLocation(B buf);

    /**
     * There are at least 4 distinct methods of registering custom packets across the versions/loaders and since they
     * all vary significantly the API supports all of them.
     * API implementations only need to implement the methods specific to the versions/loaders they are running on
     */
    void registerMessage(Object handler);
    <SIDE,M extends MessageWrapperAPI<?,?>> void registerMessage(int id, Class<M> clazz, SIDE side);
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    <CONTEXT,B extends ByteBuf,M extends MessageWrapperAPI<?,?>> void registerMessage(
            int id, Class<M> clazz, BiConsumer<M,B> encoder, Function<B,M> decoder,
            BiConsumer<M,Supplier<CONTEXT>> handler, Optional<?> dir);
    ResourceLocationAPI<?> registerMessage(ResourceLocationAPI<?> resource, boolean toClient);

    <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player);

    <M extends MessageWrapperAPI<?,?>> void sendToServer(M message);
}