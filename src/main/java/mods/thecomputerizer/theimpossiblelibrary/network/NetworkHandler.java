package mods.thecomputerizer.theimpossiblelibrary.network;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public final class NetworkHandler {

    private static final List<PacketQueue<? extends MessageImpl>> PACKET_QUEUES = new ArrayList<>();
    private static final List<Class<? extends MessageImpl>> REGISTERED_MESSAGES = new ArrayList<>();
    private static SimpleNetworkWrapper NETWORK;
    private static int globalPacketDisc;

    /**
     * This has to be called in the mod constructor or mod using this needs to be loaded before this one. Packets will
     * not be registered if CLIENT_ONLY is turned on in the main mod class.
     */
    public static void queueServerPacketRegister(Class<? extends MessageImpl> classType) {
        queuePacketRegister(classType,Side.SERVER,null);
    }
    public static void queueClientPacketRegister(Class<? extends MessageImpl> classType) {
        queuePacketRegister(classType,Side.CLIENT,null);
    }

    @SafeVarargs
    public static void queueServerPacketRegistries(Class<? extends MessageImpl>... packetQueues) {
        for(Class<? extends MessageImpl> classType : packetQueues)
            queuePacketRegister(classType,Side.SERVER,null);
    }
    @SafeVarargs
    public static void queueClientPacketRegistries(Class<? extends MessageImpl>... packetQueues) {
        for(Class<? extends MessageImpl> classType : packetQueues)
            queuePacketRegister(classType,Side.CLIENT,null);
    }

    public static void queueServerPacketRegistries(Collection<Class<? extends MessageImpl>> packetQueues) {
        for(Class<? extends MessageImpl> classType : packetQueues)
            queuePacketRegister(classType,Side.SERVER,null);
    }
    public static void queueClientPacketRegistries(Collection<Class<? extends MessageImpl>> packetQueues) {
        for(Class<? extends MessageImpl> classType : packetQueues)
            queuePacketRegister(classType,Side.CLIENT,null);
    }

    /**
     * Only use these versions if you need a custom extension of the PacketHandler class
     */
    public static <M extends MessageImpl> void queueServerPacketRegister(
            Class<M> classType, Supplier<? extends PacketHandler<M>> customPacketHandler) {
        PACKET_QUEUES.add(new PacketQueue<>(classType,Side.SERVER,customPacketHandler));
    }
    public static <M extends MessageImpl> void queueClientPacketRegister(
            Class<M> classType, Supplier<? extends PacketHandler<M>> customPacketHandler) {
        PACKET_QUEUES.add(new PacketQueue<>(classType,Side.CLIENT,customPacketHandler));
    }
    public static <M extends MessageImpl> void queuePacketRegister(
            Class<M> classType, Side sendTo, Supplier<? extends PacketHandler<M>> customPacketHandler) {
        PACKET_QUEUES.add(new PacketQueue<>(classType,sendTo,customPacketHandler));
    }

    public static void init() {
        REGISTERED_MESSAGES.clear();
        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MODID);
        int id = 0;
        for(PacketQueue<? extends MessageImpl> packetQueue : PACKET_QUEUES) {
            packetQueue.register(NETWORK,id);
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

    @SideOnly(Side.CLIENT)
    public static void sendToServer(IMessage packet) {
        NETWORK.sendToServer(packet);
    }

    public static void sendToPlayer(IMessage packet, @Nonnull EntityPlayerMP player) {
        NETWORK.sendTo(packet, player);
    }

    public static void sendToTracking(IMessage packet, Entity tracking) {
        NETWORK.sendToAllTracking(packet,tracking);
    }

    public static void sendToTracking(IMessage packet, NetworkRegistry.TargetPoint point) {
        NETWORK.sendToAllTracking(packet,point);
    }

    public static void sendToDimension(IMessage packet, int dimension) {
        NETWORK.sendToDimension(packet,dimension);
    }

    public static void sendToAllAround(IMessage packet, NetworkRegistry.TargetPoint point) {
        NETWORK.sendToAllAround(packet,point);
    }

    private static final class PacketQueue<M extends MessageImpl> {

        private final Class<M> type;
        private final Side sendTo;
        private final Supplier<? extends PacketHandler<M>> customPacketHandler;

        private PacketQueue(Class<M> classType, Side sendTo, @Nullable Supplier<? extends PacketHandler<M>> customPacketHandler) {
            this.type = classType;
            this.sendTo = sendTo;
            this.customPacketHandler = customPacketHandler;
        }

        private void register(SimpleNetworkWrapper network, int globalID) {
            network.registerMessage(Objects.nonNull(this.customPacketHandler) ? this.customPacketHandler.get() :
                    new PacketHandler<>(),this.type,globalID,this.sendTo);
        }
    }
}
