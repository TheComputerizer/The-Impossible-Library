package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent.Context;

import static net.minecraftforge.network.NetworkDirection.LOGIN_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.LOGIN_TO_SERVER;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;

/**
 * It took me way too long to figure out that the wrapper class determines the network direction for decoding.
 */
public abstract class MessageWrapperForge1_18_2 extends MessageWrapperAPI<ServerPlayer,Context> {
    
    public static MessageWrapperForge1_18_2 getInstance(NetworkDirection dir) {
        boolean client = dir==LOGIN_TO_CLIENT || dir==PLAY_TO_CLIENT;
        boolean login = dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
        return login ? (client ? new ClientLogin() : new ServerLogin()) : (client ? new Client() : new Server());
    }
    
    public static MessageWrapperForge1_18_2 getInstance(NetworkDirection dir, ByteBuf buf) {
        boolean client = dir==LOGIN_TO_CLIENT || dir==PLAY_TO_CLIENT;
        boolean login = dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
        return login ? (client ? new ClientLogin(buf) : new ServerLogin(buf)) :
                (client ? new Client(buf) : new Server(buf));
    }
    
    public static Class<? extends MessageWrapperForge1_18_2> getClass(NetworkDirection dir) {
        boolean client = dir==LOGIN_TO_CLIENT || dir==PLAY_TO_CLIENT;
        boolean login = dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
        return login ? (client ? ClientLogin.class : ServerLogin.class) : (client ? Client.class : Server.class);
    }
    
    MessageWrapperForge1_18_2() {
        super();
    }
    
    MessageWrapperForge1_18_2(ByteBuf buf) {
        super(buf);
    }
    
    public static final class Client extends MessageWrapperForge1_18_2 {
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class ClientLogin extends MessageWrapperForge1_18_2 {
        
        ClientLogin() {
            super();
        }
        
        ClientLogin(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class Server extends MessageWrapperForge1_18_2 {
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class ServerLogin extends MessageWrapperForge1_18_2 {
        
        ServerLogin() {
            super();
        }
        
        ServerLogin(ByteBuf buf) {
            super(buf);
        }
    }
}