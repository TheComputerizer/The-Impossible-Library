package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.network;

import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.fabric.network.FabricNetwork;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.network.MessageWrapper1_21;
import net.minecraft.resources.ResourceLocation;

public class NetworkFabric1_21 implements FabricNetwork<Object,Object> {

    @Override public ResourceLocation getRegistryName(MessageWrapperAPI<?,?> message) {
        return message instanceof MessageWrapper1_21<?> msgv21 ?
                msgv21.type().id() : FabricNetwork.super.getRegistryName(message);
    }
    
    @Override public <CTX> MessageWrapperAPI<?,CTX> getWrapper(Object dir) {
        return GenericUtils.cast(MessageWrapper1_21.getPayloadInstance(dir));
    }
    
    @Override public void registerMessage(MessageDirectionInfo<Object> dir, boolean warnDuplicate) {
        registerMessageCustomPayload(dir,MessageWrapper1_21::streamCodec);
    }
}