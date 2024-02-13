package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;

import java.util.function.Supplier;

public interface IPacketHandler {

    <B extends ByteBuf,M extends MessageImplAPI<?,?>> void encode(M message, B buf);
    <B extends ByteBuf,M extends MessageImplAPI<?,?>> M decode(B buf);
    <M extends MessageImplAPI<?,?>> void handle(M message, Supplier<?> ctxSupplier);
}