package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Wrapper class for interfacing version/loader specific code. Extend MessageAPI to send a packet through the API
 * Any class that extends this is required to have a constructor with a single PacketBuffer as an input
 */
@SuppressWarnings("unused")
public abstract class MessageWrapperAPI<PLAYER,CTX> {

    protected MessageDirectionInfo<?> info;
    private Collection<MessageAPI<CTX>> messages;
    private Collection<PLAYER> players;

    protected MessageWrapperAPI() {}

    protected MessageWrapperAPI(ByteBuf buf) {
        this.info = NetworkHandler.getDirectionInfo(NetworkHelper.readDir(buf));
        decode(buf);
    }

    @SuppressWarnings("UnusedReturnValue")
    public <DIR> MessageWrapperAPI<PLAYER,CTX> setMessage(DIR dir, MessageAPI<CTX> message) {
        this.info = NetworkHandler.getDirectionInfo(dir);
        this.messages = Collections.singletonList(message);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    @SafeVarargs
    public final <DIR> MessageWrapperAPI<PLAYER,CTX> setMessages(DIR dir, MessageAPI<CTX>... messages) {
        return setMessages(dir,Arrays.asList(messages));
    }

    @SuppressWarnings("UnusedReturnValue")
    public <DIR> MessageWrapperAPI<PLAYER,CTX> setMessages(DIR dir, Collection<MessageAPI<CTX>> messages) {
        this.info = NetworkHandler.getDirectionInfo(dir);
        this.messages = Collections.unmodifiableCollection(messages);
        return this;
    }

    public MessageWrapperAPI<PLAYER,CTX> setPlayer(PLAYER player) {
        this.players = Collections.singletonList(player);
        return this;
    }

    @SafeVarargs
    public final MessageWrapperAPI<PLAYER,CTX> setPlayers(PLAYER ... players) {
        this.players = Arrays.asList(players);
        return this;
    }

    public MessageWrapperAPI<PLAYER,CTX> setPlayers(Collection<PLAYER> players) {
        this.players = Collections.unmodifiableCollection(players);
        return this;
    }

    public void decode(ByteBuf buf) {
        this.messages = NetworkHelper.readCollection(buf,() -> {
            Class<?> clazz = ReflectionHelper.findExtensibleClass(NetworkHelper.readString(buf),MessageAPI.class);
            return Objects.nonNull(clazz) ? this.info.decode(clazz,buf) : null;
        });
    }

    public void encode(ByteBuf buf) {
        if(Objects.isNull(this.messages)) this.messages = Collections.emptyList();
        NetworkHelper.writeDir(buf,this.info.getDirection());
        NetworkHelper.writeCollection(buf,this.messages,message -> {
            NetworkHelper.writeString(buf,message.getClass().getName());
            this.info.encode(message,buf);
        });
    }

    @SuppressWarnings("unchecked")
    public @Nullable MessageWrapperAPI<PLAYER,CTX> handle(CTX context) {
        List<MessageAPI<CTX>> replies = new ArrayList<>();
        for(MessageAPI<CTX> message : this.messages) {
            MessageAPI<CTX> reply = this.info.handle(message,context);
            if(Objects.nonNull(reply)) replies.add(reply);
        }
        return (MessageWrapperAPI<PLAYER,CTX>)NetworkHelper.wrapMessages(NetworkHelper.getOppositeDir(this.info.getDirection()),replies);
    }

    public void send() {
        if(Objects.isNull(this.info) || Objects.isNull(this.info.getDirection())) {
            TILRef.logError("Cannot send packet of class `{}` with null info or direction!");
            return;
        }
        if(Objects.isNull(this.messages)) {
            TILRef.logError("Cannot send packet of class `{}` with no messages set!");
            return;
        }
        if(NetworkHelper.isDirToClient(this.info.getDirection())) {
            if(Objects.isNull(this.players)) {
                TILRef.logError("Cannot send packet of class `{}` to client with no players set!");
                return;
            }
            for(PLAYER player : this.players) NetworkHelper.sendToPlayer(this,player);
        } else NetworkHelper.sendToServer(this);
    }
}