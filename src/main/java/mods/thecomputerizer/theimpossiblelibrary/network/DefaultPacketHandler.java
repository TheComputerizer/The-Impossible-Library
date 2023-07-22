package mods.thecomputerizer.theimpossiblelibrary.network;

import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.Level;

import java.util.function.Supplier;

public class DefaultPacketHandler<M extends MessageImpl> implements IPacketHandler<M> {

    private final Class<M> messageClass;

    public DefaultPacketHandler(Class<M> messageClass) {
        this.messageClass = messageClass;
    }

    @Override
    public void encode(M message, PacketBuffer buf) {
        message.encode(buf);
    }

    @Override
    public M decode(PacketBuffer buf) {
        try {
            return this.messageClass.getConstructor(PacketBuffer.class).newInstance(buf);
        } catch (Exception ex) {
            LogUtil.logInternal(Level.ERROR,"Unable to decode packet registered with class {}! Does it have " +
                    "the right constructor?",this.messageClass,ex);
        }
        return null;
    }

    @Override
    public void handle(M message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        message.handle(ctx);
        ctx.setPacketHandled(true);
    }
}