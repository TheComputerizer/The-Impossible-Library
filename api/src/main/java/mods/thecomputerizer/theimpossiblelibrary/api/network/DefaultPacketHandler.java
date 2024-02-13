package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;

import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultPacketHandler<CONTEXT,M extends MessageImplAPI<?,?,?>> implements IPacketHandler<CONTEXT,M> {

    private final Function<ByteBuf,M> messageDecoder;

    public DefaultPacketHandler(Function<ByteBuf,M> messageDecoder) {
        this.messageDecoder = messageDecoder;
    }

    @Override
    public void encode(M message, ByteBuf buf) {
        message.encode(buf);
    }

    @Override
    public M decode(ByteBuf buf) {
        return this.messageDecoder.apply(buf);
    }

    @Override
    public void handle(M message, Supplier<CONTEXT> ctxSupplier) {
        CONTEXT ctx = ctxSupplier.get();
        message.handle(ctx);
        ctx.setPacketHandled(true);
    }
}