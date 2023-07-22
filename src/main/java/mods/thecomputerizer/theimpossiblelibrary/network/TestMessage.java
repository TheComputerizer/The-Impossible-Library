package mods.thecomputerizer.theimpossiblelibrary.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class TestMessage extends MessageImpl {

    public TestMessage(PacketBuffer buf) {

    }

    @Override
    public Dist getSide() {
        return Dist.CLIENT;
    }

    @Override
    public void encode(PacketBuffer buf) {}

    @Override
    public void handle(NetworkEvent.Context ctx) {}
}
