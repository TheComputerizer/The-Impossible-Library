package mods.thecomputerizer.theimpossiblelibrary.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class MessageImpl implements IMessage {

    public MessageImpl() {

    }

    public abstract IMessage handle(MessageContext ctx);

    public abstract Side getSide();
}
