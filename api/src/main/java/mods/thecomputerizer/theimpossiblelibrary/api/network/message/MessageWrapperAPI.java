package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Wrapper class for interfacing version/loader specific code. Extend MessageAPI to send a packet through the API
 * Any class that extends this is required to have a constructor with a single PacketBuffer as an input
 */
@SuppressWarnings("unused")
public abstract class MessageWrapperAPI<PLAYER,CONTEXT> {

    protected final MessageInfo<?,?> info;
    private Collection<MessageAPI<CONTEXT>> messages;
    private Collection<PLAYER> players;

    protected MessageWrapperAPI() {
        this.info = NetworkHandler.getMessageInfo(getClass());
    }

    protected MessageWrapperAPI(ByteBuf buf) {
        this.info = NetworkHandler.getMessageInfo(getClass());
    }

    public MessageWrapperAPI<PLAYER,CONTEXT> setMessage(MessageAPI<CONTEXT> message) {
        this.messages = Collections.singletonList(message);
        return this;
    }

    @SafeVarargs
    public final MessageWrapperAPI<PLAYER,CONTEXT> setMessages(MessageAPI<CONTEXT>... messages) {
        this.messages = Arrays.asList(messages);
        return this;
    }

    public MessageWrapperAPI<PLAYER,CONTEXT> setMessages(Collection<MessageAPI<CONTEXT>> messages) {
        this.messages = Collections.unmodifiableCollection(messages);
        return this;
    }

    public MessageWrapperAPI<PLAYER,CONTEXT> setPlayer(PLAYER player) {
        this.players = Collections.singletonList(player);
        return this;
    }

    @SafeVarargs
    public final MessageWrapperAPI<PLAYER,CONTEXT> setPlayers(PLAYER ... players) {
        this.players = Arrays.asList(players);
        return this;
    }

    public MessageWrapperAPI<PLAYER,CONTEXT> setPlayers(Collection<PLAYER> players) {
        this.players = Collections.unmodifiableCollection(players);
        return this;
    }

    public void decodeMessages(ByteBuf buf) {
        for(MessageAPI<CONTEXT> message : this.messages) message.decode(buf);
    }

    public void encodeMessages(ByteBuf buf) {
        if(Objects.isNull(this.messages)) return;
        for(MessageAPI<CONTEXT> message : this.messages) message.encode(buf);
    }

    public void handleMessages(CONTEXT ctx) {
        if(Objects.isNull(this.messages)) return;
        for(MessageAPI<CONTEXT> message : this.messages) message.handle(ctx);
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