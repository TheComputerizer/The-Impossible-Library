package mods.thecomputerizer.theimpossiblelibrary.shared.v21.network;

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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;

import java.io.IOException;

public abstract class Network1_21<N,DIR> implements NetworkAPI<N,DIR> {

    @Override public ResourceLocationAPI<?> readResourceLocation(ByteBuf buf) {
        return ResourceHelper.getResource(NetworkHelper.readString(buf));
    }
    
    @Override public CompoundTagAPI<?> readTag(ByteBuf buf) {
        try(ByteBufInputStream stream = new ByteBufInputStream(buf)) {
            TagHelper.getWrapped(NbtIo.read(stream, NbtAccounter.unlimitedHeap()));
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
        return TagHelper.makeCompoundTag();
    }
    
    @Override public void writeTag(ByteBuf buf, CompoundTagAPI<?> tag) {
        try(ByteBufOutputStream stream = new ByteBufOutputStream(buf)) {
            NbtIo.write((CompoundTag)tag.getWrapped(), stream);
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
    }
}