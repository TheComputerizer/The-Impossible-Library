package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;

import javax.annotation.Nullable;

public abstract class MessageAPI<CONTEXT> {

    /**
     * This is a bit janky but if the input ByteBuf is null it is assumed the message needs to be encoded and otherwise
     * it will assume the message it being decoded. This was done to avoid n
     */
    protected MessageAPI(@Nullable ByteBuf buf) {
        decode(buf);
    }

    public abstract void decode(ByteBuf buf);
    public abstract void encode(ByteBuf buf);
    public abstract void handle(CONTEXT ctx);
}