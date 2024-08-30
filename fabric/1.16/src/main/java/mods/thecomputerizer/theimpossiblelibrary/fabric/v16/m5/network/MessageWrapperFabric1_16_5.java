package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import static net.minecraftforge.fml.network.NetworkDirection.LOGIN_TO_CLIENT;
import static net.minecraftforge.fml.network.NetworkDirection.LOGIN_TO_SERVER;
import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;

/**
 * It took me way too long to figure out that the wrapper class determines the network direction for decoding.
 */
public abstract class MessageWrapperFabric1_16_5 extends MessageWrapperAPI<ServerPlayerEntity,Context> {
    
    public static MessageWrapperFabric1_16_5 getInstance(NetworkDirection dir) {
        boolean client = dir==LOGIN_TO_CLIENT || dir==PLAY_TO_CLIENT;
        boolean login = dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
        return login ? (client ? new ClientLogin() : new ServerLogin()) : (client ? new Client() : new Server());
    }
    
    public static MessageWrapperFabric1_16_5 getInstance(NetworkDirection dir, ByteBuf buf) {
        boolean client = dir==LOGIN_TO_CLIENT || dir==PLAY_TO_CLIENT;
        boolean login = dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
        return login ? (client ? new ClientLogin(buf) : new ServerLogin(buf)) :
                (client ? new Client(buf) : new Server(buf));
    }
    
    public static Class<? extends MessageWrapperFabric1_16_5> getClass(NetworkDirection dir) {
        boolean client = dir==LOGIN_TO_CLIENT || dir==PLAY_TO_CLIENT;
        boolean login = dir==LOGIN_TO_CLIENT || dir==LOGIN_TO_SERVER;
        return login ? (client ? ClientLogin.class : ServerLogin.class) : (client ? Client.class : Server.class);
    }
    
    MessageWrapperFabric1_16_5() {
        super();
    }
    
    MessageWrapperFabric1_16_5(ByteBuf buf) {
        super(buf);
    }
    
    public static final class Client extends MessageWrapperFabric1_16_5 {
        
        Client() {
            super();
        }
        
        Client(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class ClientLogin extends MessageWrapperFabric1_16_5 {
        
        ClientLogin() {
            super();
        }
        
        ClientLogin(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class Server extends MessageWrapperFabric1_16_5 {
        
        Server() {
            super();
        }
        
        Server(ByteBuf buf) {
            super(buf);
        }
    }
    
    public static final class ServerLogin extends MessageWrapperFabric1_16_5 {
        
        ServerLogin() {
            super();
        }
        
        ServerLogin(ByteBuf buf) {
            super(buf);
        }
    }
}