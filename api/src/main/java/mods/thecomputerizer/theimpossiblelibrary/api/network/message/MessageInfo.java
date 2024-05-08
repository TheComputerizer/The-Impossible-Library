package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.util.function.Function;

public final class MessageInfo<M extends MessageAPI<?>> {

    @Getter private final MessageDirectionInfo<?> directionInfo;
    @Getter private final Class<M> msgClass;
    private final MessageHandlerAPI handler;

    public MessageInfo(Class<M> clazz, MessageDirectionInfo<?> directionInfo, Function<ByteBuf,M> decoder) {
        this.msgClass = clazz;
        this.directionInfo = directionInfo;
        this.handler = MessageHandlerAPI.getDefault(decoder);
    }

    public MessageInfo(Class<M> clazz, MessageDirectionInfo<?> directionInfo, MessageHandlerAPI handler) {
        this.msgClass = clazz;
        this.directionInfo = directionInfo;
        this.handler = handler;
    }

    public M decode(ByteBuf buf) {
        return this.handler.decode(buf);
    }

    public void encode(M message, ByteBuf buf) {
        this.handler.encode(message,buf);
    }

    @SuppressWarnings("unchecked")
    public <CTX> MessageAPI<CTX> handle(M message, CTX context) {
        return this.handler.handle((MessageAPI<CTX>)message,context);
    }
}
