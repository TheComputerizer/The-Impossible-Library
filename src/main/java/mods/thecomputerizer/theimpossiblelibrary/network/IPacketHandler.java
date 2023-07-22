package mods.thecomputerizer.theimpossiblelibrary.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacketHandler<M extends MessageImpl> {

    void encode(M message, PacketBuffer buf);
    M decode(PacketBuffer buf);
    void handle(M message, Supplier<NetworkEvent.Context> ctxSupplier);
}
