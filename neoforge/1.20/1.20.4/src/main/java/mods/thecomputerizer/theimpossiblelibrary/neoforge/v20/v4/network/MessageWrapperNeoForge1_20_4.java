package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.server.level.ServerPlayer;

/**
 * It took me way too long to figure out that the wrapper class determines the network direction for decoding.
 */
public abstract class MessageWrapperNeoForge1_20_4 extends MessageWrapperAPI<ServerPlayer,Context> {
    
    public static MessageWrapperNeoForge1_20_4 getInstance(NetworkDirection dir) {
        boolean client = dir==LOGIN_TO_CLIENT || dir==PLAY_TO_CLIENT;
        boolean login = dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
        return login ? (client ? new ClientLogin() : new ServerLogin()) : (client ? new Client() : new Server());
    }
    
    public static MessageWrapperNeoForge1_20_4 getInstance(NetworkDirection dir, ByteBuf buf) {
        boolean client = dir==LOGIN_TO_CLIENT || dir==PLAY_TO_CLIENT;
        boolean login = dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
        return login ? (client ? new ClientLogin(buf) : new ServerLogin(buf)) :
                (client ? new Client(buf) : new Server(buf));
    }
    
    public static Class<? extends MessageWrapperNeoForge1_20_4> getClass(NetworkDirection dir) {
        boolean client = dir==LOGIN_TO_CLIENT || dir==PLAY_TO_CLIENT;
        boolean login = dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
        return login ? (client ? ClientLogin.class : ServerLogin.class) : (client ? Client.class : Server.class);
    }
    
    MessageWrapperNeoForge1_20_4() {
        super();
    }
    
    MessageWrapperNeoForge1_20_4(ByteBuf buf) {
        super(buf);
    }
    
    public static final class Client extends MessageWrapperNeoForge1_20_4 {
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class ClientLogin extends MessageWrapperNeoForge1_20_4 {
        
        ClientLogin() {
            super();
        }
        
        ClientLogin(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class Server extends MessageWrapperNeoForge1_20_4 {
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class ServerLogin extends MessageWrapperNeoForge1_20_4 {
        
        ServerLogin() {
            super();
        }
        
        ServerLogin(ByteBuf buf) {
            super(buf);
        }
    }
}