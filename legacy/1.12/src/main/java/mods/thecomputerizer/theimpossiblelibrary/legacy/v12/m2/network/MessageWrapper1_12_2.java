package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageWrapper1_12_2 extends MessageWrapperAPI<EntityPlayerMP,MessageContext> implements IMessage {

    public MessageWrapper1_12_2() {}

    @Override public void fromBytes(ByteBuf buf) {
        this.info = NetworkHandler.getDirectionInfo(NetworkHelper.readDir(buf));
        decode(buf);
    }

    @Override public void toBytes(ByteBuf buf) {
        encode(buf);
    }

    public static class Handler implements IMessageHandler<MessageWrapper1_12_2,IMessage> {

        @Override     public IMessage onMessage(MessageWrapper1_12_2 message, MessageContext ctx) {
            return (MessageWrapper1_12_2)message.handle(ctx);
        }
    }
}
