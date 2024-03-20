package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;

import javax.annotation.Nullable;

public class MessageTest<CONTEXT> extends MessageAPI<CONTEXT> {


    /**
     * This is a bit janky but if the input ByteBuf is null it is assumed the message needs to be encoded and otherwise
     * it will assume the message it being decoded. This was done to avoid n
     */
    protected MessageTest(@Nullable ByteBuf buf) {
        super(buf);
    }

    @Override
    public void decode(ByteBuf buf) {

    }

    @Override
    public void encode(ByteBuf buf) {

    }

    @Override
    public void handle(CONTEXT ctx) {

    }
}
