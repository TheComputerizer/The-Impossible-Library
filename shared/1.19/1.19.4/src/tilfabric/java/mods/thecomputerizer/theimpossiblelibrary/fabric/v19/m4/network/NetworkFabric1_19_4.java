package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.network;

import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.fabric.network.FabricNetwork;
import net.minecraft.world.entity.player.Player;

public class NetworkFabric1_19_4 implements FabricNetwork<Object,Object> {
    
    @Override public void registerMessage(MessageDirectionInfo<Object> dir, int id) {
        MessageWrapperFabric1_19_4.register(dir.isToClient(),dir.isToServer());
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> getWrapper(Object dir) {
        return GenericUtils.cast(MessageWrapperFabric1_19_4.getPayloadInstance(dir));
    }
    
    @Override public <P,M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P p) {
        if(message instanceof MessageWrapperFabric1_19_4<?> msgv19 && p instanceof Player player) msgv19.send(player);
        else FabricNetwork.super.sendToPlayer(message,p);
    }
    
    @Override public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        if(message instanceof MessageWrapperFabric1_19_4<?> msgv19) msgv19.send();
        else FabricNetwork.super.sendToServer(message);
    }
}