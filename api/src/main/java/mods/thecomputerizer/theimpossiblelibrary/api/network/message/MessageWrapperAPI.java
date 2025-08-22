package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreStateAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEBUG_NETWORK;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;

/**
 * Wrapper class for interfacing version/loader-specific code.
 * Extend MessageAPI to send a packet through the API
 * Any class that extends this is required to have a constructor with a single ByteBuf as an input
 */
public abstract class MessageWrapperAPI<PLAYER,CTX> implements CoreStateAccessor {
    
    private static final String FORGE_NETWORK_HELPER = BASE_PACKAGE+".forge.network.ForgeNetworkHelper";
    private static Class<?> FORGE_NETWORK_HELPER_CLASS;
    
    public static <DIR,P,C,B extends ByteBuf> @NotNull Function<B,MessageWrapperAPI<P,C>> decoder(
            final MessageDirectionInfo<DIR> dir) {
        return Objects.nonNull(dir) ? decoder(dir.getDirection()) : buf -> null;
    }
    
    public static <DIR,P,C,B extends ByteBuf> @NotNull Function<B,MessageWrapperAPI<P,C>> decoder(final DIR dir) {
        if(Objects.isNull(dir)) return buf -> null;
        return buf -> GenericUtils.cast(getInstance(dir,buf));
    }
    
    public static <P,C,B extends ByteBuf> @NotNull BiConsumer<MessageWrapperAPI<P,C>,B> encoder() {
        return MessageWrapperAPI::encode;
    }
    
    public static <DIR,P,C> Class<MessageWrapperAPI<P,C>> getClass(MessageDirectionInfo<DIR> dir) {
        if(Objects.isNull(dir)) {
            if(DEBUG_NETWORK) TILRef.logWarn("Tried to call MessageWrapperAPI#getClass with null direction info!");
            return null;
        }
        return getClass(dir.getDirection());
    }
    
    public static <DIR,P,C> Class<MessageWrapperAPI<P,C>> getClass(DIR dir) {
        if(Objects.isNull(dir)) {
            if(DEBUG_NETWORK) TILRef.logWarn("Tried to call MessageWrapperAPI#getClass with null direction!");
            return null;
        }
        boolean client = NetworkHelper.isDirToClient(dir);
        boolean login = NetworkHelper.isDirLogin(dir);
        Class<?> cls = login ? (client ? ClientLogin.class : ServerLogin.class) :
                (client ? Client.class : Server.class);
        return GenericUtils.cast(cls);
    }
    
    private static @Nullable Class<?> getForgeHelperClass() {
        if(Objects.nonNull(FORGE_NETWORK_HELPER_CLASS)) return FORGE_NETWORK_HELPER_CLASS;
        FORGE_NETWORK_HELPER_CLASS = Hacks.findClass(FORGE_NETWORK_HELPER);
        return FORGE_NETWORK_HELPER_CLASS;
    }
    
    public static <DIR,P,C> MessageWrapperAPI<P,C> getInstance(final MessageDirectionInfo<DIR> dir) {
        if(Objects.isNull(dir)) {
            if(DEBUG_NETWORK)
                TILRef.logWarn("Tried to call MessageWrapperAPI#getInstance(dir) with null direction info!");
            return null;
        }
        return getInstance(dir.getDirection());
    }
    
    public static <DIR,P,C> MessageWrapperAPI<P,C> getInstance(DIR dir) {
        if(Objects.isNull(dir)) {
            if(DEBUG_NETWORK)
                TILRef.logWarn("Tried to call MessageWrapperAPI#getInstance(dir) with null direction!");
            return null;
        }
        boolean client = NetworkHelper.isDirToClient(dir);
        boolean login = NetworkHelper.isDirLogin(dir);
        return login ? (client ? new ClientLogin<>() : new ServerLogin<>()) :
                (client ? new Client<>() : new Server<>());
    }
    
    public static <DIR,P,C,B extends ByteBuf> MessageWrapperAPI<P,C> getInstance(MessageDirectionInfo<DIR> dir,
            B buf) {
        if(Objects.isNull(dir)) {
            if(DEBUG_NETWORK)
                TILRef.logWarn("Tried to call MessageWrapperAPI#getInstance(dir,buf) with null direction info!");
            return null;
        }
        return getInstance(dir.getDirection(),buf);
    }
    
    public static <DIR,P,C,B extends ByteBuf> MessageWrapperAPI<P,C> getInstance(DIR dir, B buf) {
        if(Objects.isNull(dir)) {
            if(DEBUG_NETWORK)
                TILRef.logWarn("Tried to call MessageWrapperAPI#getInstance(dir,buf) with null direction!");
            return null;
        }
        boolean client = NetworkHelper.isDirToClient(dir);
        boolean login = NetworkHelper.isDirLogin(dir);
        return login ? (client ? new ClientLogin<>(buf) : new ServerLogin<>(buf)) :
                (client ? new Client<>(buf) : new Server<>(buf));
    }
    
    public static <DIR,P,C,S> @NotNull BiConsumer<MessageWrapperAPI<P,C>,S> handler(final MessageDirectionInfo<DIR> dir,
            final Function<S,C> contextGetter, final Function<C,P> playerGetter) {
        return handler(Objects.nonNull(dir) ? dir.getDirection() : null,contextGetter,playerGetter);
    }
    
    public static <DIR,P,C,S> @NotNull BiConsumer<MessageWrapperAPI<P,C>,S> handler(final DIR dir,
            final Function<S,C> contextGetter, final Function<C,P> playerGetter) {
        return (message,contextHolder) -> {
            C context = contextGetter.apply(contextHolder);
            MessageWrapperAPI<P,C> response = message.handle(context);
            if(Objects.nonNull(response) && Objects.nonNull(dir)) {
                if(NetworkHelper.isDirToClient(response.getDir()))
                    response.setPlayer(playerGetter.apply(context)).send();
                else response.send();
            }
            if(CoreAPI.legacyPacketEnv()) Hacks.invoke(context,"setPacketHandled",true);
        };
    }
    
    @SuppressWarnings("SameParameterValue")
    private static void invokeForgeHelper(String method, Object ... args) {
        if(!FORGE) return;
        Class<?> forgeHelper = getForgeHelperClass();
        if(Objects.isNull(forgeHelper) || TextHelper.isBlank(method)) {
            TILRef.logError("Cannot invoke Forge network helper method {}#{} (args={})",FORGE_NETWORK_HELPER,
                            method,args);
            return;
        }
        Hacks.invokeStatic(forgeHelper,method,args);
    }
    
    private boolean debug = DEBUG_NETWORK;
    private Collection<MessageAPI<CTX>> messages;
    private Collection<PLAYER> players;
    private Collection<Class<?>> missingDecoders;
    protected MessageDirectionInfo<?> info;

    protected MessageWrapperAPI() {}

    protected MessageWrapperAPI(ByteBuf buf) {
        this.info = NetworkHandler.getDirectionInfo(NetworkHelper.readDir(buf));
        decode(buf);
    }
    
    /**
     * In the case of a decoding failure we can't easily determine how to allocate the rest of the buffer.
     * As such, the decoding is immediately stopped while the failure is addressed.
     * If this is a forge environment with legacy network handling, we will try to request any missing decoders and
     * resume the decoding process if that is successful.
     * Otherwise, any extra encoded data is lost.
     */
    public void decode(ByteBuf buf) {
        this.debug = buf.readBoolean();
        if(this.debug) TILRef.logInfo("[Direction={}]: Decoding messages for type: {}",dirName(),getClass());
        final AtomicBoolean failure = new AtomicBoolean();
        this.messages = NetworkHelper.readCollection(buf,() -> decodeMessage(buf,failure));
        if(failure.get()) {
            if(decodingFailure(buf,failure)) {
                if(this.debug)
                    TILRef.logInfo("[Direction={}]: Successfully resolved & decoded {} messages for type: {}",
                                   dirName(),this.messages.size(),getClass());
            } else TILRef.logError("[Direction={}]: Failed to decode all messages for {}",dirName(),getClass());
        } else if(this.debug)
            TILRef.logInfo("[Direction={}]: Successfully decoded {} messages for type: {}",dirName(),
                           this.messages.size(),getClass());
    }
    
    protected @Nullable MessageAPI<CTX> decodeMessage(ByteBuf buf, AtomicBoolean failure) {
        if(failure.get()) return null;
        String name = NetworkHelper.readString(buf);
        if(Objects.isNull(this.info)) {
            TILRef.logError("Tried to decode class {} but direction info for {} is null!",name,getClass());
            return null;
        }
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Decoding message type name: {}",dirName(),name);
        return decodeMessage(buf,ClassHelper.findExtensibleClass(name,MessageAPI.class), failure);
    }
    
    protected @Nullable MessageAPI<CTX> decodeMessage(ByteBuf buf, Class<?> msgClass, AtomicBoolean failure) {
        if(failure.get()) return null;
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Decoding message type: {}",dirName(),msgClass);
        MessageAPI<CTX> decoded = Objects.nonNull(msgClass) ? this.info.decode(msgClass,buf) : null;
        if(Objects.isNull(decoded)) markMissingDecoder(msgClass,buf,failure);
        else if(this.debug)
            TILRef.logInfo("[Direction={}]: Successfully decoded message type: {}",dirName(),msgClass);
        return decoded;
    }
    
    private boolean decodingFailure(ByteBuf buf, AtomicBoolean failure) {
        Class<?> failedClass = getOrInitMissingDecoders().stream().findFirst().orElse(null);
        if(Objects.isNull(failedClass)) {
            TILRef.logError("Attempted to address a message decoding failure but no failed message class was "+
                            "found! (wrapper={})",getClass());
            return false;
        }
        return decodingFailure(buf,failedClass,failure);
    }
    
    private boolean decodingFailure(ByteBuf buf, Class<?> failedClass, AtomicBoolean failure) {
        final AtomicInteger removals = new AtomicInteger();
        this.messages.removeIf(msg -> {
            if(Objects.isNull(msg)) {
                removals.incrementAndGet();
                return true;
            }
            return false;
        });
        failure.set(false);
        int failures = removals.decrementAndGet();
        if(failures<0) {
            TILRef.logError("Attempted to address a message decoding failure for {} but no failed messages were "+
                            "found! (wrapper={})",failedClass,getClass());
            return true;
        }
        MessageAPI<CTX> failed = Objects.nonNull(failedClass) ? decodeMessage(buf,failedClass,failure) : null;
        if(Objects.isNull(failed)) {
            TILRef.logError("Failed to decode message {} for the 2nd time! Remaining messages will now be " +
                            "dropped!",failedClass);
            return false;
        }
        markFoundMissingDecoder(failedClass);
        for(int i=0;i<failures;i++) {
            MessageAPI<CTX> decoded = decodeMessage(buf,failure);
            if(Objects.nonNull(decoded)) this.messages.add(decoded);
            else if(failure.get()) break;
        }
        return !failure.get() || decodingFailure(buf,failure);
    }
    
    protected String dirName() {
        if(Objects.isNull(this.info)) return "[NULL DIRECTION INFO]";
        Object direction = this.info.getDirection();
        if(Objects.isNull(direction)) return "[NULL DIRECTION]";
        return direction instanceof Enum<?> ? ((Enum<?>)direction).name() : direction.toString();
    }
    
    @IndirectCallers
    public void disableDebug() {
        this.debug = false;
    }
    
    @IndirectCallers
    public void enableDebug() {
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
    
    /**
     * Get the current network direction from the info field or null if the field is not set
     */
    protected <DIR> @Nullable DIR getDir() {
        return Objects.nonNull(this.info) ? GenericUtils.cast(this.info.getDirection()) : null;
    }
    
    /**
     * Expose missing decoder collection to subclasses
     */
    @IndirectCallers
    protected @Nullable Collection<Class<?>> getMissingDecoders() {
        return this.missingDecoders;
    }
    
    /**
     * Get the opposite of the current network direction from the info field or null if the field is not set
     */
    protected <DIR> @Nullable DIR getOppositeDir() {
        DIR curDir = getDir();
        return Objects.nonNull(curDir) ? NetworkHelper.getOppositeDir(curDir) : null;
    }
    
    /**
     * Expose missing decoder collection to subclasses
     */
    @IndirectCallers
    protected Collection<Class<?>> getOrInitMissingDecoders() {
        if(Objects.isNull(this.missingDecoders)) this.missingDecoders = initMissingDecoderCollection();
        return this.missingDecoders;
    }
    
    public @Nullable MessageWrapperAPI<PLAYER,CTX> handle(CTX context) {
        if(Objects.isNull(this.messages)) this.messages = Collections.emptyList();
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Handling {} messages for type: {}",dirName(),this.messages.size(),getClass());
        List<MessageAPI<CTX>> replies = new ArrayList<>();
        for(MessageAPI<CTX> message : this.messages) {
            if(Objects.isNull(message)) {
                if(this.debug) TILRef.logInfo("[Direction={}]: Skipping handle for null message",dirName());
                continue;
            }
            if(this.debug)
                TILRef.logInfo("[Direction={}]: Handling message: {}",dirName(),message.getClass());
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
    
    /**
     * Extensible for subclasses
     */
    protected Collection<Class<?>> initMissingDecoderCollection() {
        return new HashSet<>();
    }
    
    protected void markFoundMissingDecoder(Class<?> msgClass) {
        if(this.debug)
            TILRef.logInfo("[Direction={}]: Missing decoder has been found for type: {}!",dirName(),msgClass);
        if(Objects.nonNull(this.missingDecoders)) this.missingDecoders.remove(msgClass);
    }
    
    protected void markMissingDecoder(Class<?> msgClass, ByteBuf buf, AtomicBoolean decodingFailure) {
        if(this.debug)
            TILRef.logError("[Direction={}]: Failed to decode message type: {}",dirName(),msgClass);
        Collection<Class<?>> missingDecoders = getOrInitMissingDecoders();
        if(!missingDecoders.contains(msgClass)) {
            invokeForgeHelper("requestDecoder",this.info,msgClass,buf);
            missingDecoders.add(msgClass);
        }
        decodingFailure.set(true);
    }
    
    public void send() {
        if(Objects.isNull(this.info) || Objects.isNull(getDir())) {
            TILRef.logError("Cannot send packet of class `{}` with null info or direction!",getClass());
            return;
        }
        if(Objects.isNull(this.messages)) {
            TILRef.logError("Cannot send packet of class `{}` with no messages set!",getClass());
            return;
        }
        if(NetworkHelper.isDirToClient(getDir())) {
            if(Objects.isNull(this.players)) {
                TILRef.logError("Cannot send packet of class `{}` to client with no players set!",getClass());
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

    @SafeVarargs @IndirectCallers
    public final MessageWrapperAPI<PLAYER,CTX> setPlayers(PLAYER ... players) {
        this.players = Arrays.asList(players);
        return this;
    }
    
    @IndirectCallers
    public MessageWrapperAPI<PLAYER,CTX> setPlayers(Collection<PLAYER> players) {
        this.players = Collections.unmodifiableCollection(players);
        return this;
    }
    
    public static final class Client<PLAYER,CTX> extends MessageWrapperAPI<PLAYER,CTX> {
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class ClientLogin<PLAYER,CTX> extends MessageWrapperAPI<PLAYER,CTX> {
        
        ClientLogin() {
            super();
        }
        
        ClientLogin(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class Server<PLAYER,CTX> extends MessageWrapperAPI<PLAYER,CTX> {
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class ServerLogin<PLAYER,CTX> extends MessageWrapperAPI<PLAYER,CTX> {
        
        ServerLogin() {
            super();
        }
        
        ServerLogin(ByteBuf buf) {
            super(buf);
        }
    }
}