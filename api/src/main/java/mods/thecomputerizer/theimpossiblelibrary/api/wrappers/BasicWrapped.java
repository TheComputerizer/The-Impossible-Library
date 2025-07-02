package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@IndirectCallers
public class BasicWrapped<W> extends AbstractWrapped<W> {
    
    public static <U> U cast(@Nullable Object obj) {
        return Objects.nonNull(obj) ? new BasicWrapped<>(obj).unwrap() : null;
    }
    
    public BasicWrapped(W wrapped) {
        super(wrapped);
    }
}