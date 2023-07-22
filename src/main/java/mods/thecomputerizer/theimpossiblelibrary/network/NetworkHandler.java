package mods.thecomputerizer.theimpossiblelibrary.network;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

import static org.apache.http.params.CoreProtocolPNames.PROTOCOL_VERSION;

public class NetworkHandler {

    private static final List<PacketQueue<? extends MessageImpl>> PACKET_QUEUES = new ArrayList<>();
    private static final List<Class<? extends MessageImpl>> REGISTERED_MESSAGES = new ArrayList<>();
    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(Constants.res("main_network"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();

    /**
     * This has to be called in the mod constructor or mod using this needs to be loaded before this one. Packets will
     * not be registered if CLIENT_ONLY is turned on in the main mod class.
     */
    public static void queueServerPacketRegister(Class<? extends MessageImpl> classType, boolean isLogin) {
        queuePacketRegister(classType,false,isLogin,null);
    }
    public static void queueClientPacketRegister(Class<? extends MessageImpl> classType, boolean isLogin) {
        queuePacketRegister(classType,true,isLogin,null);
    }

    @SafeVarargs
    public static void queueServerPacketRegistries(boolean isLogin, Class<? extends MessageImpl>... packetQueues) {
        for(Class<? extends MessageImpl> classType : packetQueues)
            queuePacketRegister(classType,false,isLogin,null);
    }
    @SafeVarargs
    public static void queueClientPacketRegistries(boolean isLogin, Class<? extends MessageImpl>... packetQueues) {
        for(Class<? extends MessageImpl> classType : packetQueues)
            queuePacketRegister(classType,true,isLogin,null);
    }

    public static void queueServerPacketRegistries(Collection<Class<? extends MessageImpl>> packetQueues, boolean isLogin) {
        for(Class<? extends MessageImpl> classType : packetQueues)
            queuePacketRegister(classType,false,isLogin,null);
    }
    public static void queueClientPacketRegistries(Collection<Class<? extends MessageImpl>> packetQueues, boolean isLogin) {
        for(Class<? extends MessageImpl> classType : packetQueues)
            queuePacketRegister(classType,true,isLogin,null);
    }

    /**
     * Only use these versions if you need a custom extension of IPacketHandler
     */
    public static <M extends MessageImpl> void queueServerPacketRegister(
            Class<M> classType, boolean isLogin, Supplier<? extends DefaultPacketHandler<M>> customPacketHandler) {
        queuePacketRegister(classType,false,isLogin,customPacketHandler);
    }
    public static <M extends MessageImpl> void queueClientPacketRegister(
            Class<M> classType, boolean isLogin, Supplier<? extends DefaultPacketHandler<M>> customPacketHandler) {
        queuePacketRegister(classType,true,isLogin,customPacketHandler);
    }
    public static <M extends MessageImpl> void queuePacketRegister(
            Class<M> classType, boolean isClient, boolean isLogin, Supplier<? extends DefaultPacketHandler<M>> customPacketHandler) {
        NetworkDirection direction = isClient ? isLogin ? NetworkDirection.LOGIN_TO_CLIENT : NetworkDirection.PLAY_TO_CLIENT :
                isLogin ? NetworkDirection.LOGIN_TO_SERVER : NetworkDirection.PLAY_TO_SERVER;
        PACKET_QUEUES.add(new PacketQueue<>(classType,direction,customPacketHandler));
    }

    public static void init() {
        REGISTERED_MESSAGES.clear();
        int id = 0;
        for(PacketQueue<? extends MessageImpl> packetQueue : PACKET_QUEUES) {
            packetQueue.register(HANDLER,id);
            REGISTERED_MESSAGES.add(packetQueue.type);
            id++;
        }
    }

    /**
     * Handles both client and server packets. Assumes players have already been added to messages getting sent to clients
     */
    public static <M extends MessageImpl> void sendMessageImpl(M message) {
        if(!REGISTERED_MESSAGES.contains(message.getClass())) LogUtil.logInternal(Level.ERROR,"Message of " +
                "class {} has not been registered and cannot be sent!",message.getClass().getName());
        else message.send();
    }

    /**
     * Sends the input message from the client to the server
     */
    @OnlyIn(Dist.CLIENT)
    public static void sendToServer(MessageImpl packet) {
        HANDLER.sendToServer(packet);
    }

    /**
     * Sends the input message to the input player
     */
    public static void sendToPlayer(MessageImpl packet, @Nonnull ServerPlayerEntity player) {
        HANDLER.sendTo(packet,player.connection.getConnection(),NetworkDirection.PLAY_TO_CLIENT);
    }

    /**
     * Sends the input message to all the players in the input world
     */
    public static void sendToWorld(MessageImpl packet, ServerWorld world) {
        for(ServerPlayerEntity player : world.players())
            if(Objects.nonNull(player)) sendToPlayer(packet,player);
    }


    private static final class PacketQueue<M extends MessageImpl> {

        private final Class<M> type;
        private final NetworkDirection direction;
        private final Supplier<? extends DefaultPacketHandler<M>> customPacketHandler;

        private PacketQueue(Class<M> classType, NetworkDirection direction, @Nullable Supplier<? extends DefaultPacketHandler<M>> customPacketHandler) {
            this.type = classType;
            this.direction = direction;
            this.customPacketHandler = customPacketHandler;
        }

        private void register(SimpleChannel network, int globalID) {
            IPacketHandler<M> handler = Objects.nonNull(this.customPacketHandler) ?
                    this.customPacketHandler.get() : new DefaultPacketHandler<>(this.type);
            network.registerMessage(globalID,this.type,handler::encode,handler::decode,handler::handle,Optional.of(this.direction));
        }
    }
}
