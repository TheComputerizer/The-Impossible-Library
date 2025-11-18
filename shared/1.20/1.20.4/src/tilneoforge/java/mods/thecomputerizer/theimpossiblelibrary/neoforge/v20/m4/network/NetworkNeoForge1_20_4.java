package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.network;

import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.network.MessageWrapper1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.network.MessageWrapper1_20_4.Client;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.network.MessageWrapper1_20_4.Server;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.network.Network1_20_4;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPlayPayloadHandler;
import net.neoforged.neoforge.network.registration.IDirectionAwarePayloadHandlerBuilder;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static net.minecraft.network.protocol.PacketFlow.CLIENTBOUND;
import static net.minecraft.network.protocol.PacketFlow.SERVERBOUND;
import static net.neoforged.neoforge.network.PacketDistributor.PLAYER;
import static net.neoforged.neoforge.network.PacketDistributor.SERVER;

/**
 * Neoforge packets are a pain
 */
public class NetworkNeoForge1_20_4 extends Network1_20_4<Object,Object> {
  
    @SuppressWarnings("unchecked")
    public static void registerPayloads(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(MODID);
        registerPayload(registrar,Client.class);
        registerPayload(registrar,Server.class);
    }
    
    static <M extends MessageWrapper1_20_4<IPayloadContext>> void registerPayload(IPayloadRegistrar registrar,
            Class<M> msgCls) {
        final PacketFlow dir = MessageWrapper1_20_4.getClassFlow(msgCls);
        final Function<FriendlyByteBuf,M> decoder = MessageWrapper1_20_4.streamDecoder(dir);
        registrar.play(MessageWrapper1_20_4.getClassID(msgCls),decoder::apply,registerSidedPayloadHandler(dir));
    }
    
    static <M extends MessageWrapper1_20_4<IPayloadContext>> IPlayPayloadHandler<M> registerPayloadHandler() {
        return (msg,ctx) -> {
            MessageWrapperAPI<?,IPayloadContext> reply = msg.handle(ctx);
            if(reply instanceof MessageWrapper1_20_4<?> neoReply) ctx.replyHandler().send(neoReply);
        };
    }
    
    static <M extends MessageWrapper1_20_4<IPayloadContext>> Consumer<IDirectionAwarePayloadHandlerBuilder<M,IPlayPayloadHandler<M>>> registerSidedPayloadHandler(Object dir) {
        return sidedHandler -> {
            if(CLIENTBOUND==dir) sidedHandler.client(registerPayloadHandler());
            else if(SERVERBOUND==dir) sidedHandler.server(registerPayloadHandler());
        };
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
        PLAYER.with((ServerPlayer)player).send((MessageWrapper1_20_4<?>)message);
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        SERVER.noArg().send((MessageWrapper1_20_4<?>)message);
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(Object dir, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = GenericUtils.cast(MessageWrapper1_20_4.getPayloadInstance(dir));
        if(Objects.nonNull(wrapper)) wrapper.setMessage(dir,message);
        return wrapper;
    }
    
    @SafeVarargs
    @Override public final <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = GenericUtils.cast(MessageWrapper1_20_4.getPayloadInstance(dir));
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir,messages);
        return wrapper;
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Object dir, Collection<MessageAPI<CTX>> messages) {
        MessageWrapperAPI<?,CTX> wrapper = GenericUtils.cast(MessageWrapper1_20_4.getPayloadInstance(dir));
        if(Objects.nonNull(wrapper)) wrapper.setMessages(dir,messages);
        return wrapper;
    }
}