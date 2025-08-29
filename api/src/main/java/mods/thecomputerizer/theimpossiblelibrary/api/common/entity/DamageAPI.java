package mods.thecomputerizer.theimpossiblelibrary.api.common.entity;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;

import java.util.function.Function;

@Getter
public abstract class DamageAPI<S> extends AbstractWrapped<S> {
    
    private final EntityAPI<?,?> entity;
    @Setter private float amount;

    protected DamageAPI(Object source, Function<S,Object> entityExtractor, float amount) {
        super(source);
        this.entity = WrapperHelper.wrapEntity(source,entityExtractor);
        this.amount = amount;
    }

    public abstract String getName();
}