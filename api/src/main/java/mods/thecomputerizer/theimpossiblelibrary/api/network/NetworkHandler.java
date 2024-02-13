package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class NetworkHandler<R,D> {

    private final List<PacketQueue<D,? extends MessageImplAPI<?,?,?>>> PACKET_QUEUES = Collections.synchronizedList(new ArrayList<>());
    private final Map<R,Class<? extends MessageImplAPI<?,?,?>>> REGISTERED_MESSAGES = Collections.synchronizedMap(new HashMap<>());

    private static final class PacketQueue<DIRECTION,M extends MessageImplAPI<?,?,?>> {

        private final Class<M> type;
        private final Function<ByteBuf,M> decoder;
        private final DIRECTION dir;
        private final Supplier<? extends DefaultPacketHandler<DIRECTION,M>> customHandler;

        private PacketQueue(Class<M> type, Function<ByteBuf,M> decoder, DIRECTION dir) {
            this.type = type;
            this.decoder = decoder;
            this.dir = dir;
            this.customHandler = null;
        }

        private PacketQueue(Class<M> type, Supplier<? extends DefaultPacketHandler<DIRECTION,M>> handler, DIRECTION dir) {
            this.type = type;
            this.decoder = null;
            this.dir = dir;
            this.customHandler = handler;
        }

        private <N> void register(N network, int globalID) {
            IPacketHandler<DIRECTION,M> handler = Objects.nonNull(this.customHandler) ?
                    this.customHandler.get() : new DefaultPacketHandler<>(this.decoder);
            network.registerMessage(globalID,this.type,handler::encode,handler::decode,handler::handle,Optional.of(this.dir));
        }
    }
}
