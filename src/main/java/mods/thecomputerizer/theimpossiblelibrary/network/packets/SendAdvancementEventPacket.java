package mods.thecomputerizer.theimpossiblelibrary.network.packets;

import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;
import mods.thecomputerizer.theimpossiblelibrary.events.AdvancementEvents;
import mods.thecomputerizer.theimpossiblelibrary.network.MessageImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class SendAdvancementEventPacket extends MessageImpl {


    private final ResourceLocation advancementLocation;

    public SendAdvancementEventPacket(FriendlyByteBuf buf) {
        this.advancementLocation = buf.readResourceLocation();
    }

    public SendAdvancementEventPacket(Advancement advancement) {
        this.advancementLocation = advancement.getId();
    }

    @Override
    public EnvType getSide() {
        return EnvType.CLIENT;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return TheImpossibleLibrary.SEND_ADVANCEMENT_EVENT_PACKET;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.advancementLocation);
    }

    @Override
    public void handleClient(Minecraft minecraft, ClientPacketListener listener, PacketSender response) {
        AdvancementEvents.CLIENT_GRANTED.invoker().register(this.advancementLocation);
    }

    @Override
    public void handleServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender response) {

    }
}
