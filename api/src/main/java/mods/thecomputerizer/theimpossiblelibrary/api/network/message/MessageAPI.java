package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;

public abstract class MessageAPI<CTX> {

    public abstract void encode(ByteBuf buf);
    public abstract MessageAPI<CTX> handle(CTX ctx);
}