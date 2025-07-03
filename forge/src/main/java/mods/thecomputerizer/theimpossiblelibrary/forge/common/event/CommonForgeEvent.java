package mods.thecomputerizer.theimpossiblelibrary.forge.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreStateAccessor;

@SuppressWarnings("unused")
public interface CommonForgeEvent extends CoreStateAccessor {
    
    String ENTITY_GETTER = "getEntity";
    String LIVING_GETTER = "getEntity";
    String PLAYER_GETTER = "getEntity";
    String STACK_GETTER = "getItemStack";
    String WORLD_GETTER = V18_OR_EARLIER ? "getWorld" : "getLevel";
}