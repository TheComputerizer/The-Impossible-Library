package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;

import java.util.function.Function;
import java.util.function.Supplier;

public class MessageHandlerDefault implements MessageHandlerAPI {

    private final Function<ByteBuf,? extends MessageWrapperAPI<?,?>> messageDecoder;

    public <M extends MessageWrapperAPI<?,?>> MessageHandlerDefault(Function<ByteBuf,M> messageDecoder) {
        this.messageDecoder = messageDecoder;
    }

    @Override
    public <B extends ByteBuf,M extends MessageWrapperAPI<?,?>> void encode(M message, B buf) {
        message.encodeMessages(buf);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B extends ByteBuf,M extends MessageWrapperAPI<?,?>> M decode(B buf) {
        return (M)this.messageDecoder.apply(buf);
    }

    @Override
    public <M extends MessageWrapperAPI<?,?>> void handle(M message, Supplier<?> ctxSupplier) {
        handleCasted(message,ctxSupplier);
    }

    @SuppressWarnings("unchecked")
    private <CTX,M extends MessageWrapperAPI<?,?>,MC extends MessageWrapperAPI<?,CTX>> void handleCasted(
            M message, Supplier<CTX> ctxSupplier) {
        ((MC)message).handleMessages(ctxSupplier.get());
    }
}