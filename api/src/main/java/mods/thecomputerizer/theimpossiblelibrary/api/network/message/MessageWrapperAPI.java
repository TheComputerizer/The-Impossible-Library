package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEBUG_NETWORK;

/**
 * Wrapper class for interfacing version/loader-specific code.
 * Extend MessageAPI to send a packet through the API
 * Any class that extends this is required to have a constructor with a single ByteBuf as an input
 */
@SuppressWarnings("unused")
public abstract class MessageWrapperAPI<PLAYER,CTX> {
    
    protected static <M extends MessageWrapperAPI<?,?>,B extends ByteBuf> BiConsumer<M,B> encode() {
        return MessageWrapperAPI::encode;
    }
    
    private boolean debug = DEBUG_NETWORK;
    private Collection<MessageAPI<CTX>> messages;
    private Collection<PLAYER> players;
    protected MessageDirectionInfo<?> info;

    protected MessageWrapperAPI() {}

    protected MessageWrapperAPI(ByteBuf buf) {
        this.info = NetworkHandler.getDirectionInfo(NetworkHelper.readDir(buf));
        decode(buf);
    }
    
    public <DIR> void decode(ByteBuf buf) {
        this.debug = buf.readBoolean();
        if(this.debug) TILRef.logInfo("[Direction={}]: Decoding messages for type: {}",dirName(),getClass());
        this.messages = NetworkHelper.readCollection(buf,() -> {
            String name = NetworkHelper.readString(buf);
            if(Objects.isNull(this.info)) {
                TILRef.logError("Tried to decode class {} but direction info for {} is null!",name,getClass());
                return null;
            }
            if(this.debug)
                TILRef.logInfo("[Direction={}]: Decoding message type name: {}",dirName(),name);
            Class<?> clazz = ReflectionHelper.findExtensibleClass(name,MessageAPI.class);
            if(this.debug)
                TILRef.logInfo("[Direction={}]: Decoding message type: {}",dirName(),clazz);
            MessageAPI<CTX> decoded = Objects.nonNull(clazz) ? this.info.decode(clazz,buf) : null;
            if(this.debug) {
                if(Objects.nonNull(decoded))
                    TILRef.logInfo("[Direction={}]: Successfully decoded message type: {}",dirName(),clazz);
                else TILRef.logError("[Direction={}]: Failed to decoded message type: {}",dirName(),clazz);
            }
            return decoded;
        });
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Successfully decoded {} messages for type: {}",dirName(),
                           this.messages.size(),getClass());
    }
    
    protected String dirName() {
        if(Objects.isNull(this.info)) return "[NULL DIRECTION INFO]";
        Object direction = this.info.getDirection();
        if(Objects.isNull(direction)) return "[NULL DIRECTION]";
        return direction instanceof Enum<?> ? ((Enum<?>)direction).name() : direction.toString();
    }
    
    protected void disableDebug() {
        this.debug = false;
    }
    
    protected void enableDebug() {
        this.debug = true;
    }
    
    public void encode(ByteBuf buf) {
        if(Objects.isNull(this.messages)) this.messages = Collections.emptyList();
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Encoding {} messages for type: {}",dirName(),this.messages.size(),
                           getClass());
        NetworkHelper.writeDir(buf,this.info.getDirection());
        buf.writeBoolean(this.debug);
        NetworkHelper.writeCollection(buf,this.messages,message -> {
            if(this.debug) TILRef.logInfo("[Direction={}]: Encoding message: {}",dirName(),message.getClass());
            String className = message.getClass().getName();
            NetworkHelper.writeString(buf,className);
            if(Objects.isNull(this.info))
                TILRef.logError("Tried to encode class {} but direction info for {} is null!",className,getClass());
            else this.info.encode(message,buf);
            if(this.debug)
                TILRef.logInfo("[Direction={}]: Successfully encoded message: {}",dirName(),message.getClass());
        });
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Successfully encoded {} messages for type: {}",dirName(),
                           this.messages.size(),getClass());
    }
    
    protected <DIR> @Nullable DIR getDir() {
        return Objects.nonNull(this.info) ? GenericUtils.cast(this.info.getDirection()) : null;
    }
    
    protected <DIR> @Nullable DIR getOppositeDir() {
        DIR curDir = getDir();
        return Objects.nonNull(curDir) ? NetworkHelper.getOppositeDir(curDir) : null;
    }
    
    public @Nullable MessageWrapperAPI<PLAYER,CTX> handle(CTX context) {
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Handling {} messages for type: {}",dirName(),this.messages.size(),getClass());
        List<MessageAPI<CTX>> replies = new ArrayList<>();
        for(MessageAPI<CTX> message : this.messages) {
            if(this.debug) TILRef.logInfo("[Direction={}]: Handling message: {}",dirName(),message.getClass());
            MessageAPI<CTX> reply = this.info.handle(message,context);
            if(Objects.nonNull(reply)) {
                if(this.debug)
                    TILRef.logInfo("[Direction={}]: Handling message reply: {}",dirName(),reply.getClass());
                replies.add(reply);
            } else if(this.debug)
                TILRef.logInfo("[Direction={}]: No message reply to handle for {}",dirName(),message.getClass());
        }
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Sending {} message replies for type: {}",dirName(),
                           this.messages.size(),getClass());
        if(replies.isEmpty()) return null;
        MessageWrapperAPI<PLAYER,CTX> response = GenericUtils.cast(NetworkHelper.wrapMessages(getOppositeDir(),replies));
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Response message type: {}",Objects.nonNull(response) ? response.getClass() : null);
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Successfully handled {} messages for type: {}",dirName(),
                           this.messages.size(),getClass());
        return response;
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
            if(this.debug)
                TILRef.logInfo("Sending {} messages to {} clients (wrapper={})",Objects.nonNull(this.messages) ?
                        this.messages.size() : 0,this.players.size(),getClass().getName());
            for(PLAYER player : this.players) NetworkHelper.sendToPlayer(this,player);
        } else {
            if(this.debug)
                TILRef.logInfo("Sending {} messages to the server (wrapper={})",Objects.nonNull(this.messages) ?
                        this.messages.size() : 0,getClass().getName());
            NetworkHelper.sendToServer(this);
        }
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
}