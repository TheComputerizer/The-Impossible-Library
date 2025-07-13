package mods.thecomputerizer.theimpossiblelibrary.forge.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;

import java.util.Objects;

public class ForgeNetworkHelper {
    
    @IndirectCallers
    public static <M extends MessageAPI<?>> void requestDecoder(MessageDirectionInfo<?> dirInfo, Class<M> msgClass,
            ByteBuf buf) {
        if(Objects.nonNull(msgClass)) {
            if(Objects.nonNull(dirInfo)) dirInfo.add(msgClass);
            else TILRef.logError("Cannot add decoder to null MessageDirectionInfo instance!");
        } else TILRef.logError("Cannot add null message class to MessageDirectionInfo instance!");
    }
}