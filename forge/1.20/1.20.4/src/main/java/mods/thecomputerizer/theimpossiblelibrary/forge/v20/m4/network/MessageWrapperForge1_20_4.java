package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;

public class MessageWrapperForge1_20_4 extends MessageWrapperAPI<ServerPlayer,Context> {
    
    static final ResourceLocation ID = TILRef.res("message_wrapper_forge").unwrap();
    
    public static MessageWrapperForge1_20_4 getInstance() {
        return new MessageWrapperForge1_20_4();
    }
    
    public static MessageWrapperForge1_20_4 getInstance(ByteBuf buf) {
        return new MessageWrapperForge1_20_4(buf);
    }
    
    private MessageWrapperForge1_20_4() {
        super();
    }
    
    private MessageWrapperForge1_20_4(ByteBuf buf) {
        super(buf);
    }
}