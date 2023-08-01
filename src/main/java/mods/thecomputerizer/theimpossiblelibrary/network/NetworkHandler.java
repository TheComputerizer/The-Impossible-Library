package mods.thecomputerizer.theimpossiblelibrary.network;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
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
    public static <M extends MessageImpl> void queueServerPacketRegister(
            Class<M> type, Function<PacketBuffer,M> decoder, boolean isLogin) {
        queuePacketRegister(type,decoder,true,isLogin);
    }
    public static <M extends MessageImpl> void queueClientPacketRegister(
            Class<M> type, Function<PacketBuffer,M> decoder, boolean isLogin) {
        queuePacketRegister(type,decoder,false,isLogin);
    }

    /**
     * Assumes isLogin is false
     */
    public static <M extends MessageImpl> void queueServerPacketRegister(
            Class<M> type, Function<PacketBuffer,M> decoder) {
        queuePacketRegister(type,decoder,true,false);
    }
    public static <M extends MessageImpl> void queueClientPacketRegister(
            Class<M> type, Function<PacketBuffer,M> decoder) {
        queuePacketRegister(type,decoder,false,false);
    }

    private static <M extends MessageImpl> void queuePacketRegister(Class<M> type, Function<PacketBuffer,M> decoder,
                                                                   boolean isClient, boolean isLogin) {
        NetworkDirection direction = isClient ? isLogin ? NetworkDirection.LOGIN_TO_CLIENT : NetworkDirection.PLAY_TO_CLIENT :
                isLogin ? NetworkDirection.LOGIN_TO_SERVER : NetworkDirection.PLAY_TO_SERVER;
        PACKET_QUEUES.add(new PacketQueue<>(type,decoder,direction));
    }

    /**
     * Only use these versions if you need a custom extension of IPacketHandler
     */
    public static <M extends MessageImpl> void queueServerPacketRegister(
            Class<M> type, boolean isLogin, Supplier<? extends DefaultPacketHandler<M>> customPacketHandler) {
        queuePacketRegister(type,false,isLogin,customPacketHandler);
    }
    public static <M extends MessageImpl> void queueClientPacketRegister(
            Class<M> type, boolean isLogin, Supplier<? extends DefaultPacketHandler<M>> customPacketHandler) {
        queuePacketRegister(type,true,isLogin,customPacketHandler);
    }

    /**
     * Assumes isLogin is false
     */
    public static <M extends MessageImpl> void queueServerPacketRegister(
            Class<M> type, Supplier<? extends DefaultPacketHandler<M>> customPacketHandler) {
        queuePacketRegister(type,false,false,customPacketHandler);
    }
    public static <M extends MessageImpl> void queueClientPacketRegister(
            Class<M> type, Supplier<? extends DefaultPacketHandler<M>> customPacketHandler) {
        queuePacketRegister(type,true,false,customPacketHandler);
    }

    private static <M extends MessageImpl> void queuePacketRegister(
            Class<M> type, boolean isClient, boolean isLogin, Supplier<? extends DefaultPacketHandler<M>> customPacketHandler) {
        NetworkDirection direction = isClient ? isLogin ? NetworkDirection.LOGIN_TO_CLIENT : NetworkDirection.PLAY_TO_CLIENT :
                isLogin ? NetworkDirection.LOGIN_TO_SERVER : NetworkDirection.PLAY_TO_SERVER;
        PACKET_QUEUES.add(new PacketQueue<>(type,customPacketHandler,direction));
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
        sendToDist(packet,PacketDistributor.PLAYER,() -> player);
    }

    /**
     * Sends the input message to the input supplier for the input PacketDistributor
     */
    public static <T> void sendToDist(MessageImpl packet, PacketDistributor<T> dist, Supplier<T> packetSupplier) {
        HANDLER.send(dist.with(packetSupplier),packet);
    }

    private static final class PacketQueue<M extends MessageImpl> {

        private final Class<M> type;
        private final Function<PacketBuffer,M> decoder;
        private final NetworkDirection dir;
        private final Supplier<? extends DefaultPacketHandler<M>> customHandler;

        private PacketQueue(Class<M> type, Function<PacketBuffer,M> decoder, NetworkDirection dir) {
            this.type = type;
            this.decoder = decoder;
            this.dir = dir;
            this.customHandler = null;
        }

        private PacketQueue(Class<M> type, Supplier<? extends DefaultPacketHandler<M>> handler, NetworkDirection dir) {
            this.type = type;
            this.decoder = null;
            this.dir = dir;
            this.customHandler = handler;
        }

        private void register(SimpleChannel network, int globalID) {
            IPacketHandler<M> handler = Objects.nonNull(this.customHandler) ?
                    this.customHandler.get() : new DefaultPacketHandler<>(this.decoder);
            network.registerMessage(globalID,this.type,handler::encode,handler::decode,handler::handle,Optional.of(this.dir));
        }
    }
}
