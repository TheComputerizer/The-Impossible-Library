package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleDescriptorAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleReferenceAccess;

import java.net.URI;

/**
 Simple holder class for a ModuleReference and its corresponding Module
 */
public record ModuleReferenceHolder(ModuleAccess module, ModuleReferenceAccess reference) {
    
    public static ModuleReferenceHolder of(ModuleAccess module, ModuleReferenceAccess reference) {
        return new ModuleReferenceHolder(module,reference);
    }
    
    @IndirectCallers
    public ModuleDescriptorAccess getDescriptor() {
        return this.reference.descriptor();
    }
    
    @IndirectCallers
    public URI getLocation() {
        return this.reference.location();
    }
    
    public String getName() {
        return this.module.getName();
    }
}