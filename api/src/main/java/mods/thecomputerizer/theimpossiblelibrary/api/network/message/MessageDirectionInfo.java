package mods.thecomputerizer.theimpossiblelibrary.api.network.message;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
@Getter
public class MessageDirectionInfo<DIR> {

    private final DIR direction;
    private final Set<MessageInfo<?>> infoSet;

    public MessageDirectionInfo(DIR direction) {
        this.direction = direction;
        this.infoSet = new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    public <M extends MessageAPI<?>> @Nullable M decode(Class<?> clazz, ByteBuf buf) {
        try {
            MessageInfo<M> info = (MessageInfo<M>)getMessageInfo((Class<M>)clazz);
            return Objects.nonNull(info) ? info.decode(buf) : null;
        } catch(ClassCastException ex) {
            TILRef.logError("Unable to decode message of class `{}`",clazz);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <M extends MessageAPI<?>> void encode(M message, ByteBuf buf) {
        try {
            MessageInfo<M> info = (MessageInfo<M>)getMessageInfo(message);
            if(Objects.nonNull(info)) info.encode(message,buf);
        } catch(ClassCastException ex) {
            TILRef.logError("Unable to encode message of class `{}`", ClassHelper.className(message));
        }
    }

    @SuppressWarnings("unchecked")
    public <CTX,M extends MessageAPI<CTX>> @Nullable MessageAPI<CTX> handle(M message, CTX context) {
        try {
            MessageInfo<M> info = (MessageInfo<M>)getMessageInfo(message);
            return Objects.nonNull(info) ? info.handle(message,context) : null;
        } catch(ClassCastException ex) {
            TILRef.logError("Unable to handle message of class `{}`", ClassHelper.className(message));
            return null;
        }
    }

    public <M extends MessageAPI<?>> @Nullable MessageInfo<?> getMessageInfo(M message) {
        return getMessageInfo(message.getClass());
    }

    public <M extends MessageAPI<?>> @Nullable MessageInfo<?> getMessageInfo(Class<M> msgClass) {
        for(MessageInfo<?> info : this.infoSet)
            if(msgClass==info.getMsgClass()) return info;
        TILDev.logInfo("Unable to find registered message for {}!",msgClass);
        return null;
    }

    public boolean isLogin() {
        return Objects.nonNull(this.direction) && NetworkHelper.isDirLogin(this.direction);
    }

    public boolean isToClient() {
        return Objects.nonNull(this.direction) && NetworkHelper.isDirToClient(this.direction);
    }

    public boolean isLoginToClient() {
        return isLogin() && isToClient();
    }

    public boolean isToServer() {
        return Objects.nonNull(this.direction) && !NetworkHelper.isDirToClient(this.direction);
    }

    public boolean isLoginToServer() {
        return isLogin() && isToServer();
    }
}