package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;

import java.io.IOException;

import static net.minecraft.nbt.NBTSizeTracker.UNLIMITED;

public abstract class Network1_16_5<N,DIR> implements NetworkAPI<N,DIR> {

    @Override public ResourceLocationAPI<?> readResourceLocation(ByteBuf buf) {
        return ResourceHelper.getResource(NetworkHelper.readString(buf));
    }
    
    @Override public CompoundTagAPI<?> readTag(ByteBuf buf) {
        try(ByteBufInputStream stream = new ByteBufInputStream(buf)) {
            TagHelper.getWrapped(CompressedStreamTools.read(stream,UNLIMITED));
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
        return TagHelper.makeCompoundTag();
    }
    
    @Override public void writeTag(ByteBuf buf, CompoundTagAPI<?> tag) {
        try(ByteBufOutputStream stream = new ByteBufOutputStream(buf)) {
            CompressedStreamTools.write((CompoundNBT)tag.getWrapped(), stream);
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
    }
}