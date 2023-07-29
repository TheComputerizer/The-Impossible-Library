package mods.thecomputerizer.theimpossiblelibrary.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultPacketHandler<M extends MessageImpl> implements IPacketHandler<M> {

    private final Function<PacketBuffer,M> messageDecoder;

    public DefaultPacketHandler(Function<PacketBuffer,M> messageDecoder) {
        this.messageDecoder = messageDecoder;
    }

    @Override
    public void encode(M message, PacketBuffer buf) {
        message.encode(buf);
    }

    @Override
    public M decode(PacketBuffer buf) {
        return this.messageDecoder.apply(buf);
    }

    @Override
    public void handle(M message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        message.handle(ctx);
        ctx.setPacketHandled(true);
    }
}