package mods.thecomputerizer.theimpossiblelibrary.fabric.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.BasicMutableWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.MutableWrapped;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_4;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_6;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Abusing interfaces to abstract the hell out of fabric network stuff
 */
public interface FabricNetwork<N,DIR> extends NetworkAPI<N,DIR> {
    
    String IMPL_CLIENT = fabricPkg("impl.networking.client.ClientNetworkingImpl");
    Class<?> IMPL_CLIENT_CLASS = getClassIfClient(IMPL_CLIENT);
    String IMPL_SERVER = fabricPkg("impl.networking.server.ServerNetworkingImpl");
    Class<?> IMPL_SERVER_CLASS = tryGetClass(IMPL_SERVER);
    Object CLIENT_LOGIN = getStaticField(IMPL_CLIENT_CLASS,"LOGIN");
    Object CLIENT_PLAY = getStaticField(IMPL_CLIENT_CLASS,"PLAY");
    String PLAY_CLIENT = fabricPkg("api.client.networking.v1.ClientPlayNetworking");
    Class<?> PLAY_CLIENT_CLASS = getClassIfClient(PLAY_CLIENT);
    Class<?> PLAY_CLIENT_HANDLER_CLASS = getHandlerClassIfClient(PLAY_CLIENT,
            "PlayChannelHandler","PlayPayloadHandler");
    Map<ResourceLocation,Object> PROXY_MAP = new HashMap<>();
    String PLAY_SERVER = fabricPkg("api.networking.v1.ServerPlayNetworking");
    Class<?> PLAY_SERVER_CLASS = tryGetClass(PLAY_SERVER);
    Class<?> PLAY_SERVER_HANDLER_CLASS = tryGetHandlerClass(PLAY_SERVER,
            "PlayChannelHandler","PlayPayloadHandler");
    Object SERVER_LOGIN = getStaticField(IMPL_SERVER_CLASS,"LOGIN");
    Object SERVER_PLAY = getStaticField(IMPL_SERVER_CLASS,"PLAY");
    NbtAccounter UNLIMITED_ACCOUNTER = unlimitedAccounter();
    MutableWrapped<Class<?>> WRAPPED_WRAPPER_CLASS = new BasicMutableWrapped<>();
    
    static boolean atLeastV20_4() {
        return CoreAPI.isVersionAtLeast(V20_4);
    }
    
    static boolean atLeastV20_6() {
        return CoreAPI.isVersionAtLeast(V20_6);
    }
    
    static String fabricPkg(String pkg) {
        return "net.fabricmc.fabric."+pkg;
    }
    
    static @Nullable Class<?> getClassIfClient(String className) {
        return CoreAPI.isClient() ? tryGetClass(className) : null;
    }
    
    static @Nullable Class<?> getHandlerClassIfClient(String baseClass, String oldHandler, String newHandler) {
        return CoreAPI.isClient() ? tryGetHandlerClass(baseClass,oldHandler,newHandler) : null;
    }
    
    static @Nullable Object getStaticField(@Nullable Class<?> c, String fieldName) {
        try {
            ClassHelper.checkBurningWaveInit();
            return Objects.nonNull(c) ? Fields.getStatic(c, fieldName) : null;
        } catch(Throwable t) {
            TILRef.logError("Failed to get static field {} for {}",fieldName,c,t);
        }
        return null;
    }
    
    static Class<?> getWrapperClass() {
        Class<?> wrapperClass = WRAPPED_WRAPPER_CLASS.getWrapped();
        if(Objects.isNull(wrapperClass)) {
            wrapperClass = initWrapperClass(CoreAPI.getInstance());
            WRAPPED_WRAPPER_CLASS.setWrapped(wrapperClass);
        }
        return wrapperClass;
    }
    
    static Class<?> initWrapperClass(CoreAPI core) {
        if(Objects.isNull(core)) {
            TILRef.logError("Cannot initialize MessageWrapper class with null CoreAPI instance!");
            return null;
        }
        Function<String,String> classNameGetter;
        Supplier<String> packageGetter;
        GameVersion version = core.getVersion();
        switch(version) {
            case V20_6: {
                classNameGetter = version::withClassExt;
                packageGetter = () -> version.getPackageName(core.getModLoader().getPackageName());
                break;
            }
            case V21_1: {
                classNameGetter = className -> version.withClassExt(className,false);
                packageGetter = () -> version.getPackageName(core.getModLoader().getPackageName(),false);
                break;
            }
            default: return MessageWrapperFabric.class;
        }
        String className = classNameGetter.apply(packageGetter.get()+".network.MessageWrapperFabric");
        Class<?> wrapperClass = ClassHelper.findClass(className);
        if(Objects.isNull(wrapperClass)) TILRef.logError("Failed to find MessageWrapper class {}",className);
        return wrapperClass;
    }
    
    /**
     * The class might not exist
     */
    static @Nullable Class<?> tryGetClass(String className) {
        Class<?> c;
        try {
            c = ClassHelper.findClass(className);
        } catch(Throwable t) {
            TILRef.logError("Failed to get class {}",className,t);
            return null;
        }
        if(Objects.isNull(c)) TILRef.logError("Failed to get class {}",className);
        return c;
    }
    
    static @Nullable Class<?> tryGetHandlerClass(String baseClass, String oldHandler, String newHandler) {
        return tryGetClass(baseClass+"$"+(atLeastV20_6() ? newHandler : oldHandler));
    }
    
    static NbtAccounter unlimitedAccounter() {
        return atLeastV20_4() ? Methods.invokeStaticDirect(NbtAccounter.class,DEV ? "unlimitedHeap" : "method_53898") :
                Fields.getStaticDirect(NbtAccounter.class,DEV ? "UNLIMITED" : "field_11556");
    }
    
    @Nullable default Object createHandlerProxy(DIR dir, boolean newType) {
        if(Objects.isNull(dir)) return null;
        boolean client = isDirToClient(dir);
        return createHandlerProxy(dir,newType,client,client ? PLAY_CLIENT_HANDLER_CLASS : PLAY_SERVER_HANDLER_CLASS);
    }
    
    default @Nullable Object createHandlerProxy(Object dir, boolean newType, boolean client, @Nullable Class<?> c) {
        if(Objects.isNull(c)) {
            TILRef.logError("Cannot create proxy with null class for {} dir {}",client ? "client" : "server",dir);
            return null;
        }
        InvocationHandler invoker = newType ? createInvokerCustomPayload(dir,client,c) : createInvoker(dir,client,c);
        return Proxy.newProxyInstance(c.getClassLoader(),new Class<?>[]{c},invoker);
    }
    
    default InvocationHandler createInvoker(final Object dir, boolean client, final Class<?> c) {
        return (proxy,method,args) -> {
            String methodName = method.getName();
            if(!"receive".equals(methodName)) {
                TILRef.logDebug("InvocationHandler method '{}' was not 'receive'",methodName);
                try {
                    return method.invoke(proxy,args);
                } catch(Throwable t) {
                    TILRef.logError("Failed to execute non receive method ({}) for InvocationHandler",methodName,t);
                }
                return null;
            }
            try {
                receiveAndRespond(dir,args[client ? 2 : 3],args[client ? 3 : 4],client ? null : args[1]);
                TILRef.logDebug("InvocationHandler success for {} ({})",dir,c);
            } catch(Throwable t) {
                TILRef.logError("Failed to execute InvocationHandler for proxy instance of {} (direction={})",
                                c,dir,t);
            }
            return null;
        };
    }
    
    /**
     * Network registration stuff is a bit different in 1.20.6+ and needs a different InvocationHandler
     */
    default InvocationHandler createInvokerCustomPayload(final Object dir, boolean client, final Class<?> c) {
        return (proxy,method,args) -> {
            String methodName = method.getName();
            if(!"receive".equals(methodName)) {
                TILRef.logDebug("InvocationHandler method '{}' was not 'receive'",methodName);
                try {
                    return method.invoke(proxy,args);
                } catch(Throwable t) {
                    TILRef.logError("Failed to execute non receive method ({}) for InvocationHandler",methodName,t);
                }
                return null;
            }
            try {
                MessageWrapperFabric wrapper = (MessageWrapperFabric)args[0];
                PacketSender sender = Methods.invoke(args[1],"responseSender");
                ServerPlayer player = client ? null : Methods.invoke(args[1],"player");
                receiveAndRespond(wrapper,sender,player);
                TILRef.logDebug("InvocationHandler success for {} ({})",dir,c);
            } catch(Throwable t) {
                TILRef.logError("Failed to execute InvocationHandler for proxy instance of {} (direction={})",
                                c,dir,t);
            }
            return null;
        };
    }
    
    default FriendlyByteBuf encodeMessage(MessageWrapperAPI<?,?> message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        message.encode(buf);
        return buf;
    }
    
    /**
     * Unwraps MessageDirectionInfo with checks to ensure it is able to get unwrapped on the current side
     */
    default @Nullable DIR getCheckedDir(MessageDirectionInfo<DIR> info) {
        if(Objects.isNull(info)) return null;
        DIR dir = info.getDirection();
        return isDirToClient(dir) ? (CoreAPI.isClient() ? dir : null) : dir;
    }
    
    @SuppressWarnings("unchecked")
    @Override default DIR getDirFromName(String name) {
        switch(name.toUpperCase()) {
            case "LOGIN_TO_CLIENT": return (DIR)CLIENT_LOGIN;
            case "LOGIN_TO_SERVER": return (DIR)SERVER_LOGIN;
            case "PLAY_TO_SERVER": return (DIR)SERVER_PLAY;
            default: return (DIR)CLIENT_PLAY;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override default @Nullable DIR getDirToClient() {
        return (DIR)CLIENT_PLAY;
    }
    
    @SuppressWarnings("unchecked")
    @Override default @Nullable DIR getDirToClientLogin() {
        return (DIR)CLIENT_LOGIN;
    }
    
    @SuppressWarnings("unchecked")
    @Override default DIR getDirToServer() {
        return (DIR)SERVER_PLAY;
    }
    
    @SuppressWarnings("unchecked")
    @Override default DIR getDirToServerLogin() {
        return (DIR)SERVER_LOGIN;
    }
    
    @Override default String getNameFromDir(DIR dir) {
        if(dir==CLIENT_LOGIN) return "LOGIN_TO_CLIENT";
        if(dir==CLIENT_PLAY) return "PLAY_TO_CLIENT";
        if(dir==SERVER_LOGIN) return "LOGIN_TO_SERVER";
        return "PLAY_TO_SERVER";
    }
    
    @Override default @Nullable N getNetwork() {
        return null; //Nothing to register or get
    }
    
    @SuppressWarnings("unchecked")
    @Override default @Nullable DIR getOppositeDir(DIR dir) {
        if(dir==CLIENT_LOGIN) return (DIR)SERVER_LOGIN;
        if(dir==CLIENT_PLAY) return (DIR)SERVER_PLAY;
        if(dir==SERVER_LOGIN) return (DIR)CLIENT_LOGIN;
        return (DIR)CLIENT_PLAY;
    }
    
    default ResourceLocation getRegistryName(MessageWrapperAPI<?,?> message) {
        if(Objects.isNull(message)) {
            TILRef.logError("Cannot get registry name from null MessageWrapper!");
            return null;
        }
        return ((MessageWrapperFabric)message).getRegistryName();
    }
    
    default ResourceLocation getRegistryNameFromDir(Object dir) {
        return getRegistryName(getWrapper(dir));
    }
    
    default <CTX> MessageWrapperAPI<?,CTX> getWrapper(Object dir) {
        Class<?> wrapperClass = getWrapperClass();
        if(Objects.isNull(wrapperClass)) {
            TILRef.logError("Cannot get direction-based registry name from null MessageWrapper class!");
            return null;
        }
        return Methods.invokeStatic(wrapperClass,"getInstance",this,dir);
    }
    
    @Override default boolean isDirToClient(DIR dir) {
        return dir==CLIENT_PLAY || dir==CLIENT_LOGIN;
    }
    
    @Override default boolean isDirLogin(DIR dir) {
        return dir==CLIENT_LOGIN || dir==SERVER_LOGIN;
    }
    
    @Override default ResourceLocationAPI<?> readResourceLocation(ByteBuf buf) {
        return ResourceHelper.getResource(NetworkHelper.readString(buf));
    }
    
    @Override default CompoundTagAPI<?> readTag(ByteBuf buf) {
        if(Objects.isNull(buf)) {
            TILRef.logWarn("Tried to read tag from null ByteBuf");
            return TagHelper.makeCompoundTag();
        }
        NbtAccounter accounter = Objects.nonNull(UNLIMITED_ACCOUNTER) ? UNLIMITED_ACCOUNTER : unlimitedAccounter();
        if(Objects.isNull(accounter)) {
            TILRef.logWarn("Cannot read tag with null UNLIMITED_ACCOUNTER field");
            return TagHelper.makeCompoundTag();
        }
        try(ByteBufInputStream stream = new ByteBufInputStream(buf)) {
            Object tag = NbtIo.read(stream,accounter);
            if(Objects.nonNull(tag)) return (CompoundTagAPI<?>)TagHelper.getWrapped(tag);
        } catch(Exception ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
        return TagHelper.makeCompoundTag();
    }
    
    default <P,CTX,M extends MessageWrapperAPI<P,CTX>> void receiveAndRespond(Object dir, Object buf, CTX ctx,
            @Nullable P player) {
        Class<?> wrapperClass = getWrapperClass();
        if(Objects.isNull(wrapperClass)) return;
        M wrapper = Methods.invokeStaticDirect(wrapperClass,"getInstance",this,dir,buf);
        receiveAndRespond(wrapper,ctx,player);
    }
    
    @SuppressWarnings("unchecked")
    default <P,CTX,M extends MessageWrapperAPI<P,CTX>> void receiveAndRespond(M wrapper, CTX ctx, @Nullable P player) {
        M response = (M)wrapper.handle(ctx);
        if(Objects.nonNull(response)) {
            if(Objects.nonNull(player)) response.setPlayer(player);
            response.send();
        }
    }
    
    @Override default void registerMessage(MessageDirectionInfo<DIR> directionInfo, int id) {
        DIR dir = getCheckedDir(directionInfo);
        if(Objects.isNull(dir)) return;
        ResourceLocation registryName = getRegistryNameFromDir(dir);
        if(Objects.isNull(registryName)) return;
        if(PROXY_MAP.containsKey(registryName)) {
            TILRef.logWarn("Tried to register sided network receiver {} twice!",registryName);
            return;
        }
        Object proxy = createHandlerProxy(dir,false);
        if(Objects.isNull(proxy)) {
            TILRef.logError("Failed to create PlayChannelHandler proxy for dirction {} ({})",dir,registryName);
            return;
        }
        if(registerWithProxy(isDirToClient(dir),registryName,proxy)) PROXY_MAP.put(registryName,proxy);
    }
    
    /**
     * Network registration stuff is a bit different in 1.20.6+ and needs to be handled separately
     */
    default void registerMessageCustomPayload(MessageDirectionInfo<DIR> directionInfo,
            Function<DIR,Object> codecBuilder) {
        DIR dir = getCheckedDir(directionInfo);
        if(Objects.isNull(dir)) return;
        MessageWrapperAPI<?,?> wrapper = getWrapper(dir);
        if(Objects.isNull(wrapper)) return;
        ResourceLocation registryName = getRegistryName(wrapper);
        if(Objects.isNull(registryName)) return;
        if(PROXY_MAP.containsKey(registryName)) {
            TILRef.logWarn("Tried to register sided network receiver {} twice!",registryName);
            return;
        }
        Object type = Methods.invoke(wrapper,DEV ? "type" : "method_56479");
        if(Objects.isNull(type)) return;
        String registryClassName = fabricPkg("api.networking.v1.PayloadTypeRegistry");
        Class<?> c = tryGetClass(registryClassName);
        if(Objects.isNull(c)) {
            TILRef.logError("Failed to get class payload registry class {}",registryClassName);
            return;
        }
        boolean client = isDirToClient(dir);
        String methodName = "play"+(client ? "S2C" : "C2S");
        Object registry = Methods.invokeStatic(c,methodName);
        if(Objects.isNull(registry)) {
            TILRef.logError("Failed to get payload registry from {}#{}",registryClassName,methodName);
            return;
        }
        Object codec = codecBuilder.apply(dir);
        try {
            Methods.invoke(registry,"register",type,codec);
        } catch(Throwable t) {
            TILRef.logError("Failed to register payload codec for type {} ({})",type,codec,t);
            return;
        }
        Object proxy = createHandlerProxy(dir,true);
        if(Objects.isNull(proxy)) {
            TILRef.logError("Failed to create PlayChannelHandler proxy for dirction {} ({})",dir,registryName);
            return;
        }
        if(registerWithProxy(client,type,proxy)) PROXY_MAP.put(registryName,proxy);
    }
    
    default boolean registerWithProxy(boolean client, Object registerAs, Object proxy) {
        Class<?> c = client ? PLAY_CLIENT_CLASS : PLAY_SERVER_CLASS;
        if(Objects.isNull(c)) {
            TILRef.logError("Cannot register PlayChannelHandler proxy to null class!");
            return false;
        }
        try {
            Methods.invokeStatic(c,"registerGlobalReceiver",registerAs,proxy);
            return true;
        } catch(Throwable t) {
            TILRef.logError("Failed to invoke registerGlobalReceiver for {} using ({},{})",c,registerAs,proxy,t);
        }
        return false;
    }
    
    @Override default <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        sendToPlayer(message,player,atLeastV20_6());
    }
    
    default <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player, boolean newType) {
        if(Objects.isNull(PLAY_SERVER_CLASS)) {
            TILRef.logError("Cannot send message to player {} since class {} was not found",player,PLAY_SERVER);
            return;
        }
        if(Objects.isNull(message)) {
            TILRef.logError("Cannot send null message to {}!",player);
            return;
        }
        Object[] args = newType ? new Object[]{player,message} :
                new Object[]{player,getRegistryName(message),encodeMessage(message)};
        Methods.invokeStaticDirect(PLAY_SERVER_CLASS,"send",args);
    }
    
    @Override default <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        sendToServer(message,atLeastV20_6());
    }
    
    default <M extends MessageWrapperAPI<?,?>> void sendToServer(M message, boolean newType) {
        if(Objects.isNull(PLAY_CLIENT_CLASS)) {
            TILRef.logError("Cannot send message to the server since class {} was not found",PLAY_CLIENT);
            return;
        }
        if(Objects.isNull(message)) {
            TILRef.logError("Cannot send null message to the server!");
            return;
        }
        Object[] args = newType ? new Object[]{message} : new Object[]{getRegistryName(message),encodeMessage(message)};
        Methods.invokeStaticDirect(PLAY_CLIENT_CLASS,"send",args);
    }
    
    @Override default <CTX> MessageWrapperAPI<?,CTX> wrapMessage(DIR dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = getWrapper(dir);
        wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override default <CTX> MessageWrapperAPI<?,CTX> wrapMessages(DIR dir, MessageAPI<CTX>... messages) {
        MessageWrapperAPI<?,CTX> wrapper = getWrapper(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @Override default <CTX> MessageWrapperAPI<?,CTX> wrapMessages(DIR dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = getWrapper(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @Override default void writeTag(ByteBuf buf, CompoundTagAPI<?> tag) {
        try(ByteBufOutputStream stream = new ByteBufOutputStream(buf)) {
            NbtIo.write((CompoundTag)tag.getWrapped(),stream);
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
    }
}
