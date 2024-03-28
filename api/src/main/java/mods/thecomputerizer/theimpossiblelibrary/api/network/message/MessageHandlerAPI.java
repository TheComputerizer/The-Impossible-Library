package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;

import java.util.function.Function;

public interface MessageHandlerAPI {

    static <M extends MessageAPI<?>> MessageHandlerAPI getDefault(Function<ByteBuf,M> decoder) {
        return new MessageHandlerDefault(decoder);
    }

    <M extends MessageAPI<?>> M decode(ByteBuf buf);
    <M extends MessageAPI<?>> void encode(M message, ByteBuf buf);
    <CTX,M extends MessageAPI<CTX>> MessageAPI<CTX> handle(M message, CTX context);
}