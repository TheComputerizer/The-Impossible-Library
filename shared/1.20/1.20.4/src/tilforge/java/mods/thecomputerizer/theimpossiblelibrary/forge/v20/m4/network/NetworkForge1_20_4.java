package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.network;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.network.Network1_20_4;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;

import static net.minecraftforge.network.NetworkDirection.*;
import static net.minecraftforge.network.PacketDistributor.PLAYER;
import static net.minecraftforge.network.PacketDistributor.SERVER;

public class NetworkForge1_20_4 extends Network1_20_4<SimpleChannel,NetworkDirection> {
    
    static SimpleChannel buildMessages(SimpleChannel channel) {
        MessageWrapperAPI.forEachSidedClass(msgCls -> buildMessage(channel,msgCls));
        return channel;
    }
    
    static <M extends MessageWrapperAPI<?,?>> void buildMessage(SimpleChannel channel, Class<M> msgCls) {
        channel.messageBuilder(msgCls)
                .encoder(MessageWrapperAPI::encode)
                .decoder(buf -> GenericUtils.cast(MessageWrapperAPI.decoder().apply(buf)))
                .consumerNetworkThread((BiConsumer<M,Context>)(msg,ctx) -> msg.handle(GenericUtils.cast(ctx)))
                .add();
    }

    private SimpleChannel network;

    @Override public NetworkDirection getDirFromName(String name) {
        return switch(name.toUpperCase()) {
            case "LOGIN_TO_CLIENT" -> LOGIN_TO_CLIENT;
            case "PLAY_TO_SERVER" -> PLAY_TO_SERVER;
            case "LOGIN_TO_SERVER" -> LOGIN_TO_SERVER;
            default -> PLAY_TO_CLIENT;
        };
    }

    @Override public String getNameFromDir(NetworkDirection dir) {
        return dir.name();
    }

    @Override public NetworkDirection getDirToClient() {
        return PLAY_TO_CLIENT;
    }

    @Override public NetworkDirection getDirToClientLogin() {
        return LOGIN_TO_CLIENT;
    }

    @Override public NetworkDirection getDirToServer() {
        return PLAY_TO_SERVER;
    }

    @Override public NetworkDirection getDirToServerLogin() {
        return LOGIN_TO_SERVER;
    }

    @Override public @Nullable NetworkDirection getOppositeDir(NetworkDirection dir) {
        return Objects.nonNull(dir) ? dir.reply() : null;
    }

    @Override public SimpleChannel getNetwork() {
        if(Objects.isNull(this.network)) {
            ResourceLocation name = TILRef.res("main_network").unwrap();
            this.network = buildMessages(ChannelBuilder.named(name)
                    .clientAcceptedVersions((status,version) -> true)
                    .serverAcceptedVersions((status,version) -> true)
                    .networkProtocolVersion(1).simpleChannel());
        }
        return this.network;
    }

    @Override public boolean isDirToClient(NetworkDirection dir) {
        return dir==PLAY_TO_CLIENT || dir==LOGIN_TO_CLIENT;
    }

    @Override public boolean isDirLogin(NetworkDirection dir) {
        return dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
    }

    @Override public void registerMessage(MessageDirectionInfo<NetworkDirection> dir, int id) {}
    
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().send(message,PLAYER.with((ServerPlayer)player));
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().send(message,SERVER.noArg());
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(NetworkDirection dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = MessageWrapperAPI.getInstance(dir);
        if(Objects.nonNull(wrapper)) wrapper.setMessage(dir,message);
        else TILRef.logError("Null message wrapper for dir {}",dir);
        return wrapper;
    }
    
    @SafeVarargs
    @Override public final <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir,
            MessageAPI<CTX>... messages) {
        MessageWrapperAPI<?,CTX> wrapper = MessageWrapperAPI.getInstance(dir);
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir,messages);
        else TILRef.logError("Null message wrapper for dir {}",dir);
        return wrapper;
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = MessageWrapperAPI.getInstance(dir);
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir,messages);
        else TILRef.logError("Null message wrapper for dir {}",dir);
        return wrapper;
    }
}