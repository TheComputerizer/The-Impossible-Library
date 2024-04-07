package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class EventFieldWrapper<E,V> {

    private final Function<E,V> getter;
    private final BiConsumer<E,V> setter;
    private final V defVal;

    public EventFieldWrapper(Function<E,V> getter, V defVal) {
        this(getter,null,defVal);
    }

    public EventFieldWrapper(BiConsumer<E,V> setter, V defVal) {
        this(null,setter,defVal);
    }

    public EventFieldWrapper(@Nullable Function<E,V> getter, @Nullable BiConsumer<E,V> setter, V defVal) {
        this.getter = getter;
        this.setter = setter;
        this.defVal = defVal;
    }

    public V get(@Nullable E event) {
        return Objects.nonNull(event) && Objects.nonNull(this.getter) ? this.getter.apply(event) : this.defVal;
    }

    public void set(@Nullable E event, V val) {
        if(Objects.nonNull(event) && Objects.nonNull(this.setter)) this.setter.accept(event,val);
    }
}