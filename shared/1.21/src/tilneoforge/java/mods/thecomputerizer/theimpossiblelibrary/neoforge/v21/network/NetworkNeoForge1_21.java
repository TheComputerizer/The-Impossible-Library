package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.network.Network1_21;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.network.MessageWrapperNeoForge1_21.TYPE;
import static net.minecraft.network.protocol.PacketFlow.CLIENTBOUND;
import static net.minecraft.network.protocol.PacketFlow.SERVERBOUND;
import static net.neoforged.neoforge.network.registration.HandlerThread.NETWORK;

public class NetworkNeoForge1_21 extends Network1_21<Object,Object> {
  
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        TILRef.logInfo("Registering packet payloads");
        event.registrar(MODID)
                .executesOn(NETWORK)
                .playBidirectional(TYPE,streamCodec(),MessageWrapperNeoForge1_21::handle);
    }
    
    static <B extends ByteBuf> StreamCodec<B,MessageWrapperNeoForge1_21> streamCodec() {
        return StreamCodec.of((buf,payload) -> payload.encode(buf),
                              MessageWrapperNeoForge1_21::getInstance);
    }

    @Override public Object getDirFromName(String name) {
        return switch(name.toUpperCase()) {
            case "LOGIN_TO_SERVER", "PLAY_TO_SERVER" -> SERVERBOUND;
            default -> CLIENTBOUND;
        };
    }

    @Override public String getNameFromDir(Object dir) {
        return dir==CLIENTBOUND ? "PLAY_TO_CLIENT" : "PLAY_TO_SERVER";
    }

    @Override public Object getDirToClient() {
        return CLIENTBOUND;
    }

    @Override public Object getDirToClientLogin() {
        return CLIENTBOUND;
    }

    @Override public Object getDirToServer() {
        return SERVERBOUND;
    }

    @Override public Object getDirToServerLogin() {
        return SERVERBOUND;
    }
    
    @Override public Object getNetwork() {
        return null; //Nothing to register or get
    }

    @Override public @Nullable Object getOppositeDir(Object dir) {
        return ((PacketFlow)dir).getOpposite();
    }

    @Override public boolean isDirToClient(Object dir) {
        return dir==CLIENTBOUND;
    }

    @Override public boolean isDirLogin(Object dir) {
        return dir==SERVERBOUND;
    }
    
    /**
     * Messages are registered with RegisterPayloadHandlerEvent via registerPayloadClient and registerPayloadServer
     */
    @Override public void registerMessage(MessageDirectionInfo<Object> dir, int id) {}
    
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        PacketDistributor.sendToPlayer((ServerPlayer)player,(MessageWrapperNeoForge1_21)message);
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        PacketDistributor.sendToServer((MessageWrapperNeoForge1_21)message);
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(Object dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = GenericUtils.cast(MessageWrapperNeoForge1_21.getInstance());
        if(Objects.nonNull(wrapper)) wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SafeVarargs
    @Override public final <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = GenericUtils.cast(MessageWrapperNeoForge1_21.getInstance());
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir, messages);
        return wrapper;
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = GenericUtils.cast(MessageWrapperNeoForge1_21.getInstance());
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir,messages);
        return wrapper;
    }
}