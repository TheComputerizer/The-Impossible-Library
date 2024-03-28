package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageWrapperForge extends MessageWrapperAPI<ServerPlayerEntity,Context> {

    public MessageWrapperForge() {
        super();
    }

    public MessageWrapperForge(ByteBuf buf) {
        super(buf);
    }
}