package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.network;

import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.fabric.network.FabricNetwork;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.network.MessageWrapper1_20_6;
import net.minecraft.resources.ResourceLocation;

public class NetworkFabric1_20_6 implements FabricNetwork<Object,Object> {
    
    @Override public ResourceLocation getRegistryName(MessageWrapperAPI<?,?> message) {
        return message instanceof MessageWrapper1_20_6<?> msgv20 ?
                msgv20.type().id() : FabricNetwork.super.getRegistryName(message);
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> getWrapper(Object dir) {
        return GenericUtils.cast(MessageWrapper1_20_6.getPayloadInstance(dir));
    }

    @Override public void registerMessage(MessageDirectionInfo<Object> dir, int id) {
        registerMessageCustomPayload(dir,MessageWrapper1_20_6::streamCodec);
    }
}