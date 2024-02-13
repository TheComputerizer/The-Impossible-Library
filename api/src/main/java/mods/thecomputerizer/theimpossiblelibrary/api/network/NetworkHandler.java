package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkHandler {

    private static final List<PacketQueue<?,? extends MessageImplAPI<?,?>>> PACKET_QUEUES = Collections.synchronizedList(new ArrayList<>());
    private static final List<Class<? extends MessageImplAPI<?,?>>> REGISTERED_MESSAGES = Collections.synchronizedList(new ArrayList<>());

    public static void init() {
        REGISTERED_MESSAGES.clear();
        int id = 0;
        for(PacketQueue<?,? extends MessageImplAPI<?,?>> packetQueue : PACKET_QUEUES) {
            packetQueue.register(NetworkHelper.getNetwork(),id);
            REGISTERED_MESSAGES.add(packetQueue.type);
            id++;
        }
    }

    private static final class PacketQueue<DIRECTION,M extends MessageImplAPI<?,?>> {

        private final Class<M> type;
        private final Function<ByteBuf,M> decoder;
        private final DIRECTION dir;
        private final Supplier<? extends DefaultPacketHandler> customHandler;

        private PacketQueue(Class<M> type, Function<ByteBuf,M> decoder, DIRECTION dir) {
            this.type = type;
            this.decoder = decoder;
            this.dir = dir;
            this.customHandler = null;
        }

        private PacketQueue(Class<M> type, Supplier<? extends DefaultPacketHandler> handler, DIRECTION dir) {
            this.type = type;
            this.decoder = null;
            this.dir = dir;
            this.customHandler = handler;
        }

        private <N> void register(N network, int globalID) {
            IPacketHandler handler = Objects.nonNull(this.customHandler) ?
                    this.customHandler.get() : new DefaultPacketHandler(this.decoder);
            NetworkHelper.registerMessage(globalID,this.type,handler::encode,handler::decode,handler::handle,
                    Optional.of(this.dir));
        }
    }
}
