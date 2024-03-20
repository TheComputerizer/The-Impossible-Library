package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class MessageInfo<DIR,M extends MessageWrapperAPI<?,?>> {

    @Getter private final Class<M> msgClass;
    @Getter private final DIR direction;
    private final Function<ByteBuf, M> decoder;
    private final Supplier<? extends MessageHandlerDefault> customHandler;

    public MessageInfo(Class<M> clazz, DIR direction, Function<ByteBuf,M> decoder) {
        this.msgClass = clazz;
        this.decoder = decoder;
        this.direction = direction;
        this.customHandler = null;
    }

    public MessageInfo(Class<M> clazz, DIR direction, Supplier<? extends MessageHandlerDefault> handler) {
        this.msgClass = clazz;
        this.decoder = null;
        this.direction = direction;
        this.customHandler = handler;
    }

    public void register(int globalID) {
        MessageHandlerAPI handler = Objects.nonNull(this.customHandler) ?
                this.customHandler.get() : new MessageHandlerDefault(this.decoder);
        NetworkHelper.registerMessage(globalID,this.msgClass,handler::encode,handler::decode,handler::handle,
                Optional.of(this.direction));
    }
}
