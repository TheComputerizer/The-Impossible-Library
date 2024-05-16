package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageWrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.network.MessageWrapper1_12_2.Handler;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static net.minecraftforge.fml.relauncher.Side.*;

public class Network1_12_2 implements NetworkAPI<SimpleNetworkWrapper,Side> { //TODO registerMessage

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
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapper1_12_2();
        wrapper.setMessage(side,message);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Side side, MessageAPI<CTX> ... messages) {
        MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapper1_12_2();
        wrapper.setMessages(side,messages);
        return wrapper;
    }

    @Override
    public <CTX> MessageWrapperAPI<?,CTX> wrapMessages(Side side, Collection<MessageAPI<CTX>> messages) {
        @SuppressWarnings("unchecked") MessageWrapperAPI<?,CTX> wrapper = (MessageWrapperAPI<?,CTX>)new MessageWrapper1_12_2();
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
    public ResourceLocationAPI<?> readResourceLocation(ByteBuf buf) {
        return new ResourceLocation1_12_2(new ResourceLocation(NetworkHelper.readString(buf)));
    }
    
    @Override public CompoundTagAPI<?> readTag(ByteBuf buf) {
        try(ByteBufInputStream stream = new ByteBufInputStream(buf)) {
            TagHelper.getWrapped(CompressedStreamTools.read(stream,NBTSizeTracker.INFINITE));
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
        return TagHelper.makeCompoundTag();
    }

    @Override
    public void registerMessage(MessageDirectionInfo<Side> dir, int id) {
        getNetwork().registerMessage(new Handler(),MessageWrapper1_12_2.class,id,dir.getDirection());
    }

    @Override
    public <P, M extends MessageWrapperAPI<?,?>> void sendToPlayer(M message, P player) {
        getNetwork().sendTo((MessageWrapper1_12_2)message,(EntityPlayerMP)player);
    }

    @Override
    public <M extends MessageWrapperAPI<?,?>> void sendToServer(M message) {
        getNetwork().sendToServer((MessageWrapper1_12_2)message);
    }
    
    @Override public void writeTag(ByteBuf buf, CompoundTagAPI<?> tag) {
        try(ByteBufOutputStream stream = new ByteBufOutputStream(buf)) {
            CompressedStreamTools.write((NBTTagCompound)tag.getWrapped(),stream);
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
    }
}
