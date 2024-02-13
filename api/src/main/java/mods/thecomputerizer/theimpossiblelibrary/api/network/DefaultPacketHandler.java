package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;

import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultPacketHandler implements IPacketHandler {

    private final Function<ByteBuf,? extends MessageImplAPI<?,?>> messageDecoder;

    public <M extends MessageImplAPI<?,?>> DefaultPacketHandler(Function<ByteBuf,M> messageDecoder) {
        this.messageDecoder = messageDecoder;
    }

    @Override
    public <B extends ByteBuf,M extends MessageImplAPI<?,?>> void encode(M message, B buf) {
        message.encode(buf);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B extends ByteBuf,M extends MessageImplAPI<?,?>> M decode(B buf) {
        return (M)this.messageDecoder.apply(buf);
    }

    @Override
    public <M extends MessageImplAPI<?,?>> void handle(M message, Supplier<?> ctxSupplier) {
        message.handle(ctxSupplier.get());
    }
}