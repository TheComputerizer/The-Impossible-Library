package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public interface RegistryEventFabric extends CommonFabricEvent {
    
    Registry<?> getRegistry();
    
    default void registerEntry(RegistryEntryAPI<?> entry) {
        registerEntry(getRegistry(),entry.getRegistryName(),entry.unwrap());
    }
    
    @IndirectCallers
    default <E> void registerEntry(ResourceLocationAPI<?> name, E entry) {
        registerEntry(getRegistry(),name,entry);
    }
    
    @IndirectCallers
    default void registerEntry(Registry<?> registry, RegistryEntryAPI<?> entry) {
        registerEntry(registry,entry.getRegistryName(),entry.unwrap());
    }
    
    @SuppressWarnings("unchecked")
    default <E> void registerEntry(Registry<?> registry, ResourceLocationAPI<?> name, E entry) {
        Registry.register((Registry<? super E>)registry,(ResourceLocation)name.unwrap(),entry);
    }
}