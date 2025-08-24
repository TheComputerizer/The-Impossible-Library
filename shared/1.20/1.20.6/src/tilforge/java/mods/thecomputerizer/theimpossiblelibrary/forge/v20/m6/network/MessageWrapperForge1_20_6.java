package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;
import org.jetbrains.annotations.NotNull;

public class MessageWrapperForge1_20_6 extends MessageWrapperAPI<Player,Context> implements CustomPacketPayload {
    
    static final Type<MessageWrapperForge1_20_6> TYPE = new Type<>(TILRef.res("message_wrapper_forge").unwrap());
    
    public static MessageWrapperForge1_20_6 getInstance() {
        return new MessageWrapperForge1_20_6();
    }
    
    public static MessageWrapperForge1_20_6 getInstance(ByteBuf buf) {
        return new MessageWrapperForge1_20_6(buf);
    }
    
    public MessageWrapperForge1_20_6() {
        super();
    }
    
    public MessageWrapperForge1_20_6(ByteBuf buf) {
        super(buf);
    }
    
    @Override public @NotNull Type<MessageWrapperForge1_20_6> type() {
        return TYPE;
    }
}