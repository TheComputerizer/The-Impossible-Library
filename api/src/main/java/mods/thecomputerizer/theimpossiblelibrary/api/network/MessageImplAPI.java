package mods.thecomputerizer.theimpossiblelibrary.api.network;

import io.netty.buffer.ByteBuf;

/**
 * Any class that extends this is required to have a constructor with a single PacketBuffer as an input
 */
public interface MessageImplAPI<SIDE,PLAYER,CONTEXT> {

    MessageImplAPI<?,?,?> addPlayers(PLAYER[] players);
    MessageImplAPI<?,?,?> addPlayers(Iterable<PLAYER> players);
    void encode(ByteBuf buf);
    SIDE getSide();
    Object handle(CONTEXT ctx);
    void send();
}