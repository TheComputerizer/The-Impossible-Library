package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;

import java.util.function.Supplier;

public interface MessageHandlerAPI {

    <B extends ByteBuf,M extends MessageWrapperAPI<?,?>> void encode(M message, B buf);
    <B extends ByteBuf,M extends MessageWrapperAPI<?,?>> M decode(B buf);
    <M extends MessageWrapperAPI<?,?>> void handle(M message, Supplier<?> ctxSupplier);
}