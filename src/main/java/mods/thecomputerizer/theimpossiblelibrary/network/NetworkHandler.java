package mods.thecomputerizer.theimpossiblelibrary.network;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public class NetworkHandler {

    private static final List<PacketQueue<? extends MessageImpl>> PACKET_QUEUES = Collections.synchronizedList(new ArrayList<>());
    private static final Map<ResourceLocation,Class<? extends MessageImpl>> REGISTERED_MESSAGES = Collections.synchronizedMap(new HashMap<>());

    /**
     * Packets must be registered in both the main and client entry point initializers using the respective to and from
     * queuePacketRegister methods. Packets will not be registered if CLIENT_ONLY is turned on in the main mod class.
     */
    public static <M extends MessageImpl> void queuePacketRegisterToClient(
            Class<M> type, Function<FriendlyByteBuf,M> decoder, ResourceLocation registryName) {
        queuePacketRegister(type,decoder,true,registryName);
    }
    public static <M extends MessageImpl> void queuePacketRegisterToServer(
            Class<M> type, Function<FriendlyByteBuf,M> decoder, ResourceLocation registryName) {
        queuePacketRegister(type,decoder,false,registryName);
    }

    private static <M extends MessageImpl> void queuePacketRegister(
            Class<M> type, Function<FriendlyByteBuf,M> decoder, boolean isToClient,ResourceLocation registryName) {
        synchronized (PACKET_QUEUES) {
            PACKET_QUEUES.add(new PacketQueue<>(type, decoder, isToClient, registryName));
        }
    }

    public static void init() {
        REGISTERED_MESSAGES.clear();
        for(PacketQueue<? extends MessageImpl> packetQueue : PACKET_QUEUES) {
            ResourceLocation location = packetQueue.register();
            if(Objects.nonNull(location)) REGISTERED_MESSAGES.put(location,packetQueue.type);
        }
    }

    /**
     * Handles both client and server packets. Assumes players have already been added to messages getting sent to clients
     */
    public static void sendMessageImpl(MessageImpl message) {
        message.send();
    }

    /**
     * Checks if the input message can be sent to the server
     */
    @Environment(EnvType.CLIENT)
    private static boolean canSendToServer(MessageImpl message) {
        return ClientPlayNetworking.canSend(message.getRegistryName());
    }

    /**
     * Checks if the input message can be sent to the input player
     */
    private static boolean canSendToPlayer(ServerPlayer player, MessageImpl message) {
        return ServerPlayNetworking.canSend(player,message.getRegistryName());
    }

    /**
     * Checks if the input message can be sent to the input listener
     */
    private static boolean canSendToListener(ServerGamePacketListenerImpl listener, MessageImpl message) {
        return ServerPlayNetworking.canSend(listener,message.getRegistryName());
    }

    /**
     * Sends the input message from the client to the server
     */
    @Environment(EnvType.CLIENT)
    public static void sendToServer(MessageImpl packet) {
        Constants.testLog("Attempting to send MessageImpl packet with class {} to the server",packet.getClass());
        if(canSendToServer(packet)) ClientPlayNetworking.send(packet.getRegistryName(),packet.encode());
        else LogUtil.logInternal(Level.ERROR,"Unable to send packet with class {} to the server! Was it " +
                "registered correctly? [{}]",packet.getClass(),packet.getRegistryName());
    }

    /**
     * Sends the input message to the input player
     */
    public static void sendToPlayer(MessageImpl packet, @NotNull ServerPlayer player) {
        Constants.testLog("Attempting to send MessageImpl packet with class {} to a player",packet.getClass());
        if(canSendToPlayer(player,packet)) ServerPlayNetworking.send(player,packet.getRegistryName(),packet.encode());
        else LogUtil.logInternal(Level.ERROR,"Unable to send packet with class {} to a player! Was it " +
                "registered correctly?",packet.getClass());
    }

    private record PacketQueue<M extends MessageImpl>(Class<M> type, Function<FriendlyByteBuf, M> decoder,
                                                      boolean isToClient, ResourceLocation registryName) {

        private ResourceLocation register() {
                if (Objects.nonNull(this.registryName)) {
                    String modid = this.registryName.getNamespace();
                    if (modid.matches("minecraft")) {
                        LogUtil.logInternal(Level.ERROR, "Please do not register packets under the Minecraft" +
                                " namespace. {} will be skipped.", this.registryName);
                        return null;
                    } else
                        return this.isToClient ? registerToClient() : registerToServer();
                } else LogUtil.logInternal(Level.ERROR, "Cannot register packet to a null registry name!");
                return null;
            }

            private ResourceLocation registerToClient() {
                ClientPlayNetworking.registerGlobalReceiver(this.registryName, (minecraft, listener, buf, response) -> {
                    Class<? extends MessageImpl> regClass = REGISTERED_MESSAGES.get(this.registryName);
                    if (Objects.nonNull(regClass)) {
                        M message = this.decoder.apply(buf);
                        if (regClass == message.getClass()) message.handleClient(minecraft, listener, response);
                        else LogUtil.logInternal(Level.ERROR, "Client received message with registry name of {} " +
                                "that was expected to be decoded into class {} but was instead decoded into {}! Message " +
                                "handling will be skipped which may cause issues!", this.registryName, regClass, message.getClass());
                    } else LogUtil.logInternal(Level.ERROR, "Client received message with an unknown registry name " +
                            "of {}! Decoding will be skipped which may cause issues!", this.registryName);
                });
                return this.registryName;
            }

            private ResourceLocation registerToServer() {
                ServerPlayNetworking.registerGlobalReceiver(this.registryName, (server, player, listener, buf, response) -> {
                    Class<? extends MessageImpl> regClass = REGISTERED_MESSAGES.get(this.registryName);
                    if (Objects.nonNull(regClass)) {
                        M message = this.decoder.apply(buf);
                        if (regClass == message.getClass()) message.handleServer(server, player, listener, response);
                        else LogUtil.logInternal(Level.ERROR, "Server received message with registry name of {} " +
                                "that was expected to be decoded into class {} but was instead decoded into {}! Message " +
                                "handling will be skipped which may cause issues!", this.registryName, regClass, message.getClass());
                    } else LogUtil.logInternal(Level.ERROR, "Server received message with an unknown registry name " +
                            "of {}! Decoding will be skipped which may cause issues!", this.registryName);
                });
                return this.registryName;
            }
        }
}