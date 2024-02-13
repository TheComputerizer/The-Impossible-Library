package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;

import java.util.function.Supplier;

public interface IPacketHandler<CONTEXT,M extends MessageImplAPI<?,?,?>> {

    void encode(M message, ByteBuf buf);
    M decode(ByteBuf buf);
    void handle(M message, Supplier<CONTEXT> ctxSupplier);
}