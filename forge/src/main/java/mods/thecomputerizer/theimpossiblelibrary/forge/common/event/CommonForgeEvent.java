package mods.thecomputerizer.theimpossiblelibrary.forge.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreStateAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import java.util.function.Function;

public interface CommonForgeEvent extends CoreStateAccessor {
    
    String ENTITY_GETTER = "getEntity";
    String LIVING_GETTER = "getEntity";
    String PLAYER_GETTER = "getEntity";
    String STACK_GETTER = "getItemStack";
    String WORLD_GETTER = V18_OR_EARLIER ? "getWorld" : "getLevel";
    
    default <E> Function<E,Object> entityGetter() {
        return Hacks.invoke(this,"getter",ENTITY_GETTER);
    }
    
    @IndirectCallers
    default <E> Function<E,Object> livingGetter() {
        return Hacks.invoke(this,"getter",LIVING_GETTER);
    }

    default <E> Function<E,Object> playerGetter() {
        return Hacks.invoke(this,"getter",PLAYER_GETTER);
    }
    
    default <E> Function<E,?> stackGetter() {
        return Misc.safeFunction(event -> Hacks.invoke(this,"getter",event,STACK_GETTER));
    }
    
    default <E> Function<E,?> worldGetter() {
        return Misc.safeFunction(event -> Hacks.invoke(this,"getter",event,WORLD_GETTER));
    }
}