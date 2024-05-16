package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;

import java.util.Collection;

@SuppressWarnings("unused")
public interface NetworkAPI<N,DIR> {

    DIR getDirFromName(String name);
    String getNameFromDir(DIR dir);
    DIR getDirToClient();
    DIR getDirToClientLogin();
    DIR getDirToServer();
    DIR getDirToServerLogin();
    DIR getOppositeDir(DIR dir);
    <CTX> MessageWrapperAPI<?,CTX> wrapMessage(DIR dir, MessageAPI<CTX> message);
    @SuppressWarnings("unchecked")
    <CTX> MessageWrapperAPI<?,CTX> wrapMessages(DIR dir, MessageAPI<CTX> ... messages);
    <CTX> MessageWrapperAPI<?,CTX> wrapMessages(DIR dir, Collection<MessageAPI<CTX>> messages);
    N getNetwork();
    boolean isDirToClient(DIR d);
    boolean isDirLogin(DIR d);
    ResourceLocationAPI<?> readResourceLocation(ByteBuf buf);
    CompoundTagAPI<?> readTag(ByteBuf buf);

    /**
     * There are at least four distinct methods of registering custom packets across the versions/loaders,
     * and since they all vary significantly, the API supports all of them.
     * API implementations only need to implement the methods specific to the versions/loaders they are running on
     */
    void registerMessage(MessageDirectionInfo<DIR> dir, int id);
    <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player);
    <M extends MessageWrapperAPI<?,?>> void sendToServer(M message);
    void writeTag(ByteBuf buf, CompoundTagAPI<?> tag);
}