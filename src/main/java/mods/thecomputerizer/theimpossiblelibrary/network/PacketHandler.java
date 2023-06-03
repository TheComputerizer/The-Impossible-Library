package mods.thecomputerizer.theimpossiblelibrary.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler<M extends MessageImpl> implements IMessageHandler<M, IMessage> {
    @Override
    public IMessage onMessage(M message, MessageContext ctx) {
        return message.handle(ctx);
    }
}
