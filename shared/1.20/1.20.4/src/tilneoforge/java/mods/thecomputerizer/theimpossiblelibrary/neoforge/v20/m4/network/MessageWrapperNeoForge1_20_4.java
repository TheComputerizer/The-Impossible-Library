package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class MessageWrapperNeoForge1_20_4 extends MessageWrapperAPI<ServerPlayer,IPayloadContext> implements CustomPacketPayload {
    
    static final ResourceLocation ID = TILRef.res("message_wrapper_neoforge").unwrap();
    
    public static MessageWrapperNeoForge1_20_4 getInstance() {
        return new MessageWrapperNeoForge1_20_4();
    }
    
    public static MessageWrapperNeoForge1_20_4 getInstance(ByteBuf buf) {
        return new MessageWrapperNeoForge1_20_4(buf);
    }
    
    private MessageWrapperNeoForge1_20_4() {
        super();
    }
    
    private MessageWrapperNeoForge1_20_4(ByteBuf buf) {
        super(buf);
    }
    
    @Override public @NotNull ResourceLocation id() {
        return ID;
    }
    
    @Override public void write(@NotNull FriendlyByteBuf buf) {
        encode(buf);
    }
}