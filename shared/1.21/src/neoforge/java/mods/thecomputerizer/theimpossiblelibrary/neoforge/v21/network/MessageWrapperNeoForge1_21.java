package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class MessageWrapperNeoForge1_21 extends MessageWrapperAPI<ServerPlayer,IPayloadContext> implements CustomPacketPayload {
    
    static Type<MessageWrapperNeoForge1_21> TYPE = new Type<>(TILRef.res("message_wrapper_neoforge").unwrap());
    
    public static MessageWrapperNeoForge1_21 getInstance() {
        return new MessageWrapperNeoForge1_21();
    }
    
    public static MessageWrapperNeoForge1_21 getInstance(ByteBuf buf) {
        return new MessageWrapperNeoForge1_21(buf);
    }
    
    private MessageWrapperNeoForge1_21() {
        super();
    }
    
    private MessageWrapperNeoForge1_21(ByteBuf buf) {
        super(buf);
    }
    
    @Override public @NotNull Type<MessageWrapperNeoForge1_21> type() {
        return TYPE;
    }
}