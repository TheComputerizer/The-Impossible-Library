package mods.thecomputerizer.theimpossiblelibrary.legacy.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.MessageImplAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.resource.ResourceLocationLegacy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkLegacy implements NetworkAPI<SimpleNetworkWrapper> {

    private SimpleNetworkWrapper network;

    @Override
    public SimpleNetworkWrapper getNetwork() {
        if(Objects.isNull(this.network)) this.network = NetworkRegistry.INSTANCE.newSimpleChannel(TILRef.MODID);
        return this.network;
    }

    @Override
    public <B extends ByteBuf> ResourceLocationAPI<?> readResourceLocation(B buf) {
        return new ResourceLocationLegacy(new ResourceLocation(NetworkHelper.readString(buf)));
    }

    @Override
    public void registerMessage(Object handler) {

    }

    @Override
    public <SIDE, M extends MessageImplAPI<?, ?>> void registerMessage(int id, Class<M> clazz, SIDE side) {

    }

    @Override
    public <CONTEXT,B extends ByteBuf, M extends MessageImplAPI<?,?>> void registerMessage(
            int id, Class<M> clazz, BiConsumer<M,B> encoder, Function<B,M> decoder,
            BiConsumer<M,Supplier<CONTEXT>> handler, Optional<?> dir) {

    }

    @Override
    public ResourceLocationAPI<?> registerMessage(ResourceLocationAPI<?> resource, boolean toClient) {
        return null;
    }

    @Override
    public <P, M extends MessageImplAPI<?, ?>> void sendToPlayer(M message, P player) {
        getNetwork().sendTo((MessageImplLegacy)message,(EntityPlayerMP)player);
    }

    @Override
    public <M extends MessageImplAPI<?, ?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageImplLegacy)message);
    }
}
