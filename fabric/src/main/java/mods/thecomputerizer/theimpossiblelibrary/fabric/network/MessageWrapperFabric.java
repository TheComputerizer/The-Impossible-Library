package mods.thecomputerizer.theimpossiblelibrary.fabric.network;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@Getter
public class MessageWrapperFabric extends MessageWrapperAPI<ServerPlayer,PacketSender> {
    
    protected static final String ID_PATH = "message_wrapper_fabric";
    public static final ResourceLocation ID = TILRef.res(ID_PATH).unwrap();
    protected static final ResourceLocation ID_CLIENT_LOGIN = TILRef.res(ID_PATH+"_client_login").unwrap();
    protected static final ResourceLocation ID_CLIENT_PLAY = TILRef.res(ID_PATH+"_client_play").unwrap();
    protected static final ResourceLocation ID_SERVER_LOGIN = TILRef.res(ID_PATH+"_server_login").unwrap();
    protected static final ResourceLocation ID_SERVER_PLAY = TILRef.res(ID_PATH+"_server_play").unwrap();
    
    public static MessageWrapperFabric getInstance() {
        return new MessageWrapperFabric(ID);
    }
    
    public static MessageWrapperFabric getInstance(ByteBuf buf) {
        return new MessageWrapperFabric(ID,buf);
    }
    
    public static MessageWrapperFabric getInstance(NetworkAPI<?,?> network, Object dir) {
        return new MessageWrapperFabric(getRegistryName(isClient(network,dir),isLogin(network,dir)));
    }
    
    public static MessageWrapperFabric getInstance(NetworkAPI<?,?> network, Object dir, ByteBuf buf) {
        return new MessageWrapperFabric(getRegistryName(isClient(network,dir),isLogin(network,dir)),buf);
    }
    
    @SuppressWarnings("unchecked")
    protected static boolean isClient(NetworkAPI<?,?> network, Object dir) {
        return ((NetworkAPI<?,Object>)network).isDirToClient(dir);
    }
    
    @SuppressWarnings("unchecked")
    protected static boolean isLogin(NetworkAPI<?,?> network, Object dir) {
        return ((NetworkAPI<?,Object>)network).isDirLogin(dir);
    }
    
    public static ResourceLocation getRegistryName(boolean client, boolean login) {
        return login ? (client ? ID_CLIENT_LOGIN : ID_SERVER_LOGIN) : (client ? ID_CLIENT_PLAY : ID_SERVER_PLAY);
    }
    
    protected final ResourceLocation registryName;
    
    protected MessageWrapperFabric(ResourceLocation registryName) {
        super();
        this.registryName = registryName;
    }
    
    protected MessageWrapperFabric(ResourceLocation registryName, ByteBuf buf) {
        super(buf);
        this.registryName = registryName;
    }
}