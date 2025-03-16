package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.network.Network1_21;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static net.minecraft.network.protocol.PacketFlow.CLIENTBOUND;
import static net.minecraft.network.protocol.PacketFlow.SERVERBOUND;

/**
 * Fabric doesn't have mod specific network channels or network direction API classes...
 */
public class NetworkNeoForge1_21 extends Network1_21<Object,Object> {
    
    public static void registerPayloadClient(RegisterPayloadHandlersEvent event) {
        registerPayload(event,CLIENTBOUND);
    }
    
    public static void registerPayloadServer(RegisterPayloadHandlersEvent event) {
        registerPayload(event,SERVERBOUND);
    }
    
    static void registerPayload(RegisterPayloadHandlersEvent event, Object dir) {
        MessageWrapperNeoForge1_21 wrapper = MessageWrapperNeoForge1_21.getInstance(dir,false);
        PayloadRegistrar registrar = event.registrar(MODID);
        if(dir==CLIENTBOUND) registrar.commonToClient(wrapper.type(),streamCodec(dir),MessageWrapperNeoForge1_21::handle);
        else registrar.commonToServer(wrapper.type(),streamCodec(dir),MessageWrapperNeoForge1_21::handle);
    }
    
    static <B extends ByteBuf> StreamCodec<B,MessageWrapperNeoForge1_21> streamCodec(Object dir) {
        StreamEncoder<B,MessageWrapperNeoForge1_21> encoder = (buf,payload) -> payload.encode(buf);
        StreamDecoder<B,MessageWrapperNeoForge1_21> decoder = buf -> MessageWrapperNeoForge1_21.getInstance(dir,false,buf);
        return StreamCodec.of(encoder,decoder);
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
     * Messages are registered with RegisterPayloadHandlerEvent via registerPayloadClient & registerPayloadServer
     */
    @Override public void registerMessage(MessageDirectionInfo<Object> dir, int id) {}
    
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        PacketDistributor.sendToPlayer((ServerPlayer)player,(MessageWrapperNeoForge1_21)message);
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        PacketDistributor.sendToServer((MessageWrapperNeoForge1_21)message);
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(Object dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperNeoForge1_21.getInstance(dir,false);
        wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperNeoForge1_21.getInstance(dir,false);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)MessageWrapperNeoForge1_21.getInstance(dir,false);
        wrapper.setMessages(dir,messages);
        return wrapper;
    }
}
