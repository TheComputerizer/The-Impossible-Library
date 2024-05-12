package mods.thecomputerizer.theimpossiblelibrary.api.registry.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;

import javax.annotation.Nullable;

public abstract class EntityBuilderAPI extends RegistryEntryBuilder<EntityAPI<?,?>> {
    
    protected EntityBuilderAPI(@Nullable EntityBuilderAPI parent) {}
}