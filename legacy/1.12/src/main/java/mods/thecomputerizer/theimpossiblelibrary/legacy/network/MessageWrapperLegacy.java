package mods.thecomputerizer.theimpossiblelibrary.legacy.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageWrapperLegacy extends MessageWrapperAPI<EntityPlayerMP,MessageContext> implements IMessage {

    public MessageWrapperLegacy() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        decode(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        encode(buf);
    }

    public static class Handler implements IMessageHandler<MessageWrapperLegacy,IMessage> {

        @Override
        public IMessage onMessage(MessageWrapperLegacy message, MessageContext ctx) {
            return (MessageWrapperLegacy)message.handle(ctx);
        }
    }
}
