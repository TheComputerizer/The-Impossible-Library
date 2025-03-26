package mods.thecomputerizer.theimpossiblelibrary.fabric.network;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.BasicMutableWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.MutableWrapped;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl.LOGIN;
import static net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl.PLAY;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Abusing interfaces to abstract the hell out of fabric network stuff
 */
public interface FabricNetwork<N,DIR> extends NetworkAPI<N,DIR> {
    
    String IMPL_CLIENT = fabricPkg("impl.networking.client.ClientNetworkingImpl");
    Class<?> IMPL_CLIENT_CLASS = getClassIfClient(IMPL_CLIENT);
    Object CLIENT_LOGIN = getStaticField(IMPL_CLIENT_CLASS,"LOGIN");
    Object CLIENT_PLAY = getStaticField(IMPL_CLIENT_CLASS,"PLAY");
    String PLAY_CLIENT = fabricPkg("api.client.networking.v1.ClientPlayNetworking");
    Class<?> PLAY_CLIENT_CLASS = getClassIfClient(PLAY_CLIENT);
    Class<?> PLAY_CLIENT_HANDLER_CLASS = getClassIfClient(PLAY_CLIENT+"$PlayChannelHandler");
    Map<ResourceLocation,Object> PROXY_MAP = new HashMap<>();
    String PLAY_SERVER = fabricPkg("api.networking.v1.ServerPlayNetworking");
    Class<?> PLAY_SERVER_CLASS = tryGetClass(PLAY_SERVER);
    Class<?> PLAY_SERVER_HANDLER_CLASS = tryGetClass(PLAY_SERVER+"$PlayChannelHandler");
    @SuppressWarnings("UnstableApiUsage")
    Object SERVER_LOGIN = LOGIN;
    @SuppressWarnings("UnstableApiUsage")
    Object SERVER_PLAY = PLAY;
    MutableWrapped<Class<?>> WRAPPED_WRAPPER_CLASS = new BasicMutableWrapped<>();
    
    static String fabricPkg(String pkg) {
        return "net.fabricmc.fabric."+pkg;
    }
    
    static @Nullable Class<?> getClassIfClient(String className) {
        return CoreAPI.isClient() ? tryGetClass(className) : null;
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
        try {
            return ClassHelper.findClass(className);
        } catch(Throwable t) {
            TILRef.logError("Failed to get class {}",className,t);
        }
        return null;
    }
    
    @Nullable default Object createHandlerProxy(DIR dir) {
        if(Objects.isNull(dir)) return null;
        boolean client = isDirToClient(dir);
        return createHandlerProxy(dir,client,client ? PLAY_CLIENT_HANDLER_CLASS : PLAY_SERVER_HANDLER_CLASS);
    }
    
    default @Nullable Object createHandlerProxy(Object dir, boolean client, @Nullable Class<?> c) {
        if(Objects.isNull(c)) {
            TILRef.logError("Cannot create proxy with null class for {} dir {}",client ? "client" : "server",dir);
            return null;
        }
        return Proxy.newProxyInstance(c.getClassLoader(),new Class<?>[]{c},createInvoker(dir,client,c));
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
                                c,dir);
            }
            return null;
        };
    }
    
    default FriendlyByteBuf encodeMessage(MessageWrapperAPI<?,?> message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        message.encode(buf);
        return buf;
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
    
    @SuppressWarnings("unchecked")
    default <P,CTX,M extends MessageWrapperAPI<P,CTX>> void receiveAndRespond(Object dir, Object buf, CTX ctx,
            @Nullable P player) {
        Class<?> wrapperClass = getWrapperClass();
        if(Objects.isNull(wrapperClass)) return;
        M wrapper = Methods.invokeStaticDirect(wrapperClass,"getInstance",this,dir,buf);
        M response = (M)wrapper.handle(ctx);
        if(Objects.nonNull(response)) {
            if(Objects.nonNull(player)) response.setPlayer(player);
            response.send();
        }
    }
    
    @Override default void registerMessage(MessageDirectionInfo<DIR> directionInfo, int id) {
        DIR dir = directionInfo.getDirection();
        ResourceLocation registryName = getRegistryNameFromDir(dir);
        if(Objects.isNull(registryName)) return;
        if(PROXY_MAP.containsKey(registryName)) {
            TILRef.logWarn("Tried to register sided network receiver {} twice!",registryName);
            return;
        }
        Object proxy = createHandlerProxy(dir);
        if(Objects.isNull(proxy)) {
            TILRef.logError("Failed to create PlayChannelHandler proxy for dirction {} ({})",dir,registryName);
            return;
        }
        if(registerWithProxy(isDirToClient(dir),registryName,proxy)) PROXY_MAP.put(registryName,proxy);
    }
    
    default boolean registerWithProxy(boolean client, ResourceLocation registryName, Object proxy) {
        Class<?> c = client ? PLAY_CLIENT_CLASS : PLAY_SERVER_CLASS;
        if(Objects.isNull(c)) {
            TILRef.logError("Cannot register PlayChannelHandler proxy to null class!");
            return false;
        }
        try {
            Methods.invokeStatic(c,"registerGlobalReceiver",registryName,proxy);
            return true;
        } catch(Throwable t) {
            TILRef.logError("Failed to invoke registerGlobalReceiver for {} using ({},{})",c,registryName,proxy,t);
        }
        return false;
    }
    
    @Override default <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        if(Objects.isNull(PLAY_SERVER_CLASS)) {
            TILRef.logError("Cannot send message to player {} since class {} was not found",player,PLAY_SERVER);
            return;
        }
        if(Objects.isNull(message)) {
            TILRef.logError("Cannot send null message to {}!",player);
            return;
        }
        FriendlyByteBuf buf = encodeMessage(message);
        Methods.invokeStaticDirect(PLAY_SERVER_CLASS,"send",player,getRegistryName(message),buf);
    }
    
    default <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        if(Objects.isNull(PLAY_CLIENT_CLASS)) {
            TILRef.logError("Cannot send message to the server since class {} was not found",PLAY_CLIENT);
            return;
        }
        if(Objects.isNull(message)) {
            TILRef.logError("Cannot send null message to the server!");
            return;
        }
        FriendlyByteBuf buf = encodeMessage(message);
        Methods.invokeStaticDirect(PLAY_CLIENT_CLASS,"send",getRegistryName(message),buf);
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
}
