package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static net.minecraftforge.fml.network.NetworkDirection.*;
import static org.apache.http.params.CoreProtocolPNames.PROTOCOL_VERSION;

public class Network1_16_5 implements NetworkAPI<SimpleChannel,NetworkDirection> {

    private SimpleChannel network;

    @Override
    public NetworkDirection getDirFromName(String name) {
        switch(name.toUpperCase()) {
            case "LOGIN_TO_CLIENT" : return LOGIN_TO_CLIENT;
            case "PLAY_TO_SERVER" : return PLAY_TO_SERVER;
            case "LOGIN_TO_SERVER" : return LOGIN_TO_SERVER;
            default: return PLAY_TO_CLIENT;
        }
    }

    @Override
    public String getNameFromDir(NetworkDirection dir) {
        return dir.name();
    }

    @Override
    public NetworkDirection getDirToClient() {
        return PLAY_TO_CLIENT;
    }

    @Override
    public NetworkDirection getDirToClientLogin() {
        return LOGIN_TO_CLIENT;
    }

    @Override
    public NetworkDirection getDirToServer() {
        return PLAY_TO_SERVER;
    }

    @Override
    public NetworkDirection getDirToServerLogin() {
        return LOGIN_TO_SERVER;
    }

    @Override
    public @Nullable NetworkDirection getOppositeDir(NetworkDirection dir) {
        switch(dir) {
            case PLAY_TO_CLIENT: return PLAY_TO_SERVER;
            case PLAY_TO_SERVER: return PLAY_TO_CLIENT;
            case LOGIN_TO_CLIENT: return LOGIN_TO_SERVER;
            case LOGIN_TO_SERVER: return LOGIN_TO_CLIENT;
            default: return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(NetworkDirection dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapper1_16_5();
        wrapper.setMessage(dir,message);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapper1_16_5();
        wrapper.setMessages(dir,messages);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapper1_16_5();
        wrapper.setMessages(dir,messages);
        return wrapper;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SimpleChannel getNetwork() {
        if(Objects.isNull(this.network))
            this.network = NetworkRegistry.ChannelBuilder.named((ResourceLocation)TILRef.res("main_network").getInstance())
                .clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
        return this.network;
    }

    @Override
    public boolean isDirToClient(NetworkDirection dir) {
        return dir==PLAY_TO_CLIENT || dir==LOGIN_TO_CLIENT;
    }

    @Override
    public boolean isDirLogin(NetworkDirection dir) {
        return dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
    }

    @Override
    public ResourceLocationAPI<?> readResourceLocation(ByteBuf buf) {
        return new ResourceLocation1_16_5(new ResourceLocation(NetworkHelper.readString(buf)));
    }
    
    @Override public CompoundTagAPI<?> readTag(ByteBuf buf) {
        try(ByteBufInputStream stream = new ByteBufInputStream(buf)) {
            TagHelper.getWrapped(CompressedStreamTools.read(stream,NBTSizeTracker.UNLIMITED));
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
        return TagHelper.makeCompoundTag();
    }

    @Override
    public void registerMessage(MessageDirectionInfo<NetworkDirection> dir, int id) {
        getNetwork().registerMessage(id,MessageWrapper1_16_5.class,MessageWrapperAPI::encode,MessageWrapper1_16_5::new,
                (message,supplier) -> message.handle(supplier.get()),Optional.of(dir.getDirection()));
    }

    @Override
    public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player),(MessageWrapper1_16_5)message);
    }

    @Override
    public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageWrapper1_16_5)message);
    }
    
    @Override public void writeTag(ByteBuf buf, CompoundTagAPI<?> tag) {
        try(ByteBufOutputStream stream = new ByteBufOutputStream(buf)) {
            CompressedStreamTools.write((CompoundNBT)tag.getWrapped(), stream);
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
    }
}
