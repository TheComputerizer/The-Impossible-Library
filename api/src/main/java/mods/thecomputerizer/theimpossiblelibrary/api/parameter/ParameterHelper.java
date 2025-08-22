package mods.thecomputerizer.theimpossiblelibrary.api.parameter;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.parameter.primitive.*;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

@IndirectCallers
public class ParameterHelper {

    /**
     * Does not support collections
     */
    @SuppressWarnings("unchecked") @IndirectCallers
    public static <E> Parameter<E> parameterize(Class<?> type, E element) {
        if(Collection.class.isAssignableFrom(type))
            return (Parameter<E>)new ParameterList<>(String.class,(List<String>)element); //TODO Should this really be restricted to lists of strings?
        switch(type.getSimpleName()) {
            case "Boolean": return (Parameter<E>)new ParameterBool((Boolean)element);
            case "Byte": return (Parameter<E>)new ParameterByte((Byte)element);
            case "Double": return (Parameter<E>)new ParameterDouble((Double)element);
            case "Float": return (Parameter<E>)new ParameterFloat((Float)element);
            case "Integer": return (Parameter<E>)new ParameterInt((Integer)element);
            case "Long": return (Parameter<E>)new ParameterLong((Long)element);
            case "Short": return (Parameter<E>)new ParameterShort((Short)element);
            default: return (Parameter<E>)new ParameterString((String)element);
        }
    }

    public static @Nullable Parameter<?> parse(ByteBuf buf) {
        Class<Parameter<?>> c = ClassHelper.findExtensibleClass(NetworkHelper.readString(buf),Parameter.class);
        try {
            return Hacks.construct(c,buf);
        } catch(Throwable t) {
            TILRef.logError("Unable to invoke constructor {}(ByteBuff) for paremter!",c,t);
            return null;
        }
    }
}