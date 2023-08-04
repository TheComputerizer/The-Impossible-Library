package mods.thecomputerizer.theimpossiblelibrary.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacketHandler<M extends MessageImpl> {

    void encode(M message, FriendlyByteBuf buf);
    M decode(FriendlyByteBuf buf);
    void handle(M message, Supplier<NetworkEvent.Context> ctxSupplier);
}