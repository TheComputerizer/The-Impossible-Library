package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.network;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.network.Network1_16_5;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static net.minecraftforge.fml.network.NetworkDirection.*;
import static net.minecraftforge.fml.network.PacketDistributor.PLAYER;

public class NetworkForge1_16_5 extends Network1_16_5<SimpleChannel,NetworkDirection> {

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

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SimpleChannel getNetwork() {
        if(Objects.isNull(this.network))
            this.network = ChannelBuilder.named((ResourceLocation)TILRef.res("main_network").getInstance())
                    .clientAcceptedVersions(version -> true)
                    .serverAcceptedVersions(version -> true)
                    .networkProtocolVersion(TILRef::getNetworkVersion)
                    .simpleChannel();
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

    //TODO I'm pretty sure the login directions need an extra flag to be set
    @SuppressWarnings("unchecked") @Override
    public void registerMessage(MessageDirectionInfo<NetworkDirection> dir, int id) {
        getNetwork().registerMessage(id,(Class<MessageWrapperForge1_16_5>)MessageWrapperForge1_16_5.getClass(dir.getDirection()),
                MessageWrapperAPI::encode, buf -> MessageWrapperForge1_16_5.getInstance(dir.getDirection(), buf),
                (message,supplier) -> { //Response handler
                    Context context = supplier.get();
                    MessageWrapperForge1_16_5 wrapper = (MessageWrapperForge1_16_5)message.handle(context);
                    if(Objects.nonNull(wrapper)) {
                        if(!dir.isToClient()) wrapper.setPlayer(context.getSender());
                        wrapper.send();
                    }
                },Optional.of(dir.getDirection()));
    }
    
    //TODO Does not support login direction
    @Override
    public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().send(PLAYER.with(() -> (ServerPlayerEntity)player),(MessageWrapperForge1_16_5)message);
    }
    
    //TODO Does not support login direction
    @Override
    public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageWrapperForge1_16_5)message);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(NetworkDirection dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_16_5.getInstance(dir);
        wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_16_5.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(NetworkDirection dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperForge1_16_5.getInstance(dir);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
}
