package mods.thecomputerizer.theimpossiblelibrary.legacy.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.network.MessageWrapperLegacy.Handler;
import mods.thecomputerizer.theimpossiblelibrary.legacy.resource.ResourceLocationLegacy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collection;
import java.util.Objects;

import static net.minecraftforge.fml.relauncher.Side.*;

public class NetworkLegacy implements NetworkAPI<SimpleNetworkWrapper,Side> { //TODO registerMessage

    private SimpleNetworkWrapper network;

    @Override
    public Side getDirFromName(String name) {
        return name.equalsIgnoreCase("SERVER") ? SERVER : CLIENT;
    }

    @Override
    public String getNameFromDir(Side side) {
        return side.name();
    }

    @Override
    public Side getDirToClient() {
        return CLIENT;
    }

    @Override
    public Side getDirToClientLogin() {
        return CLIENT;
    }

    @Override
    public Side getDirToServer() {
        return SERVER;
    }

    @Override
    public Side getDirToServerLogin() {
        return SERVER;
    }

    @Override
    public Side getOppositeDir(Side side) {
        return side==CLIENT ? SERVER : CLIENT;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessage(Side side, MessageAPI<CTX> message) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapperLegacy();
        wrapper.setMessage(side,message);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Side side, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapperLegacy();
        wrapper.setMessages(side,messages);
        return wrapper;
    }

    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Side side, Collection<MessageAPI<CTX>> messages) {
        @SuppressWarnings("unchecked") MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapperLegacy();
        wrapper.setMessages(side,messages);
        return wrapper;
    }

    @Override
    public SimpleNetworkWrapper getNetwork() {
        if(Objects.isNull(this.network)) this.network = NetworkRegistry.INSTANCE.newSimpleChannel(TILRef.MODID);
        return this.network;
    }

    @Override
    public boolean isDirToClient(Side side) {
        return side.isClient();
    }

    @Override
    public boolean isDirLogin(Side d) {
        return true;
    }

    @Override
    public <B extends ByteBuf> ResourceLocationAPI<?> readResourceLocation(B buf) {
        return new ResourceLocationLegacy(new ResourceLocation(NetworkHelper.readString(buf)));
    }

    @Override
    public void registerMessage(MessageDirectionInfo<Side> dir, int id) {
        getNetwork().registerMessage(new Handler(),MessageWrapperLegacy.class,id,dir.getDirection());
    }

    @Override
    public <P, M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().sendTo((MessageWrapperLegacy)message,(EntityPlayerMP)player);
    }

    @Override
    public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageWrapperLegacy)message);
    }
}
