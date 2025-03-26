package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.network.MessageWrapperFabric;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MessageWrapperFabric1_20_6 extends MessageWrapperFabric implements CustomPacketPayload {
    
    static final Type<MessageWrapperFabric1_20_6> TYPE = new Type<>(ID);
    static final Type<MessageWrapperFabric1_20_6> TYPE_CLIENT_LOGIN = new Type<>(ID_CLIENT_LOGIN);
    static final Type<MessageWrapperFabric1_20_6> TYPE_CLIENT_PLAY = new Type<>(ID_CLIENT_PLAY);
    static final Type<MessageWrapperFabric1_20_6> TYPE_SERVER_LOGIN = new Type<>(ID_SERVER_LOGIN);
    static final Type<MessageWrapperFabric1_20_6> TYPE_SERVER_PLAY = new Type<>(ID_SERVER_PLAY);
    static final Map<ResourceLocation,Type<MessageWrapperFabric1_20_6>> BY_LOCATION = Map.of(ID,TYPE,ID_CLIENT_LOGIN,
            TYPE_CLIENT_LOGIN,ID_CLIENT_PLAY,TYPE_CLIENT_PLAY, ID_SERVER_LOGIN,TYPE_SERVER_LOGIN,ID_SERVER_PLAY,
            TYPE_SERVER_PLAY);
    
    public static MessageWrapperFabric1_20_6 getInstance() {
        return new MessageWrapperFabric1_20_6(TYPE);
    }
    
    public static MessageWrapperFabric1_20_6 getInstance(ByteBuf buf) {
        return new MessageWrapperFabric1_20_6(TYPE,buf);
    }
    
    public static MessageWrapperFabric1_20_6 getInstance(NetworkAPI<?,?> network, Object dir) {
        return new MessageWrapperFabric1_20_6(getType(isClient(network,dir),isLogin(network,dir)));
    }
    
    public static MessageWrapperFabric1_20_6 getInstance(NetworkAPI<?,?> network, Object dir, ByteBuf buf) {
        return new MessageWrapperFabric1_20_6(getType(isClient(network,dir),isLogin(network,dir)),buf);
    }
    
    private static Type<MessageWrapperFabric1_20_6> getType(boolean client, boolean login) {
        return BY_LOCATION.get(getRegistryName(client,login));
    }
    
    private final Type<MessageWrapperFabric1_20_6> type;
    
    MessageWrapperFabric1_20_6(Type<MessageWrapperFabric1_20_6> type) {
        super(type.id());
        this.type = type;
    }
    
    MessageWrapperFabric1_20_6(Type<MessageWrapperFabric1_20_6> type, ByteBuf buf) {
        super(type.id(),buf);
        this.type = type;
    }
    
    @Override public @NotNull Type<MessageWrapperFabric1_20_6> type() {
        return this.type;
    }
}