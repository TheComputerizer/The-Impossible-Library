package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.network.Network1_20;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;

import java.io.IOException;

public abstract class Network1_20_6<N,DIR> extends Network1_20<N,DIR> {
    
    @Override public CompoundTagAPI<?> readTag(ByteBuf buf) {
        try(ByteBufInputStream stream = new ByteBufInputStream(buf)) {
            TagHelper.getWrapped(NbtIo.read(stream,NbtAccounter.unlimitedHeap()));
        } catch(IOException ex) {
            TILRef.logError("Failed to write tag to buffer",ex);
        }
        return TagHelper.makeCompoundTag();
    }
}