package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageWrapper1_16_5 extends MessageWrapperAPI<ServerPlayerEntity,Context> {

    public MessageWrapper1_16_5() {
        super();
    }

    public MessageWrapper1_16_5(ByteBuf buf) {
        super(buf);
    }
}