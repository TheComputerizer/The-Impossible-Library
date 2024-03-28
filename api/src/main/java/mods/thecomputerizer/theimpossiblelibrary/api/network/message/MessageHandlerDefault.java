package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;

import java.util.function.Function;

public class MessageHandlerDefault implements MessageHandlerAPI {

    private final Function<ByteBuf,? extends MessageAPI<?>> messageDecoder;

    public <M extends MessageAPI<?>> MessageHandlerDefault(Function<ByteBuf,M> messageDecoder) {
        this.messageDecoder = messageDecoder;
    }

    @Override
    public <M extends MessageAPI<?>> void encode(M message, ByteBuf buf) {
        message.encode(buf);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MessageAPI<?>> M decode(ByteBuf buf) {
        return (M)this.messageDecoder.apply(buf);
    }

    @Override
    public <CTX,M extends MessageAPI<CTX>> MessageAPI<CTX> handle(M message, CTX context) {
        return message.handle(context);
    }
}