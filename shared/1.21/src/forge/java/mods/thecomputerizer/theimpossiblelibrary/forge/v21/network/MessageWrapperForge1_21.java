package mods.thecomputerizer.theimpossiblelibrary.forge.v21.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;
import org.jetbrains.annotations.NotNull;

public class MessageWrapperForge1_21 extends MessageWrapperAPI<Player,Context> implements CustomPacketPayload {
    
    static final Type<MessageWrapperForge1_21> TYPE = new Type<>(TILRef.res("message_wrapper_forge").unwrap());
    
    public static MessageWrapperForge1_21 getInstance() {
        return new MessageWrapperForge1_21();
    }
    
    public static MessageWrapperForge1_21 getInstance(ByteBuf buf) {
        return new MessageWrapperForge1_21(buf);
    }
    
    public MessageWrapperForge1_21() {
        super();
    }
    
    public MessageWrapperForge1_21(ByteBuf buf) {
        super(buf);
    }
    
    @Override public @NotNull Type<MessageWrapperForge1_21> type() {
        return TYPE;
    }
}