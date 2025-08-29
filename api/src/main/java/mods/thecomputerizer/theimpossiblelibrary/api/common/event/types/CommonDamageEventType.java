package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface CommonDamageEventType<E> {
    
    E getSource();
    
    default float getDamageAmount() {
        DamageAPI<?> api = getDamageAPI();
        return Objects.nonNull(api) ? api.getAmount() : 0f;
    }
    
    default @Nullable DamageAPI<?> getDamageAPI() {
        E source = getSource();
        if(Objects.isNull(source)) return null;
        EventFieldWrapper<E,DamageAPI<?>> damageField = getDamageField();
        return Objects.nonNull(damageField) ? damageField.get(getSource()) : null;
    }
    
    @Nullable EventFieldWrapper<E,DamageAPI<?>> getDamageField();
    
    default void setDamageAmount(float amount) {
        EventFieldWrapper<E,DamageAPI<?>> damageField = getDamageField();
        if(Objects.isNull(damageField)) return;
        E source = getSource();
        if(Objects.isNull(source)) return;
        DamageAPI<?> api = damageField.get(source);
        if(Objects.nonNull(api)) damageField.set(source, WrapperHelper.wrapDamage(api.getWrapped(),amount));
    }
}