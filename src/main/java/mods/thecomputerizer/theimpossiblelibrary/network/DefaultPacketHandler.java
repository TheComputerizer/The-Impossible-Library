package mods.thecomputerizer.theimpossiblelibrary.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultPacketHandler<M extends MessageImpl> implements IPacketHandler<M> {

    private final Function<FriendlyByteBuf,M> messageDecoder;

    public DefaultPacketHandler(Function<FriendlyByteBuf,M> messageDecoder) {
        this.messageDecoder = messageDecoder;
    }

    @Override
    public void encode(M message, FriendlyByteBuf buf) {
        message.encode(buf);
    }

    @Override
    public M decode(FriendlyByteBuf buf) {
        return this.messageDecoder.apply(buf);
    }

    @Override
    public void handle(M message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        message.handle(ctx);
        ctx.setPacketHandled(true);
    }
}