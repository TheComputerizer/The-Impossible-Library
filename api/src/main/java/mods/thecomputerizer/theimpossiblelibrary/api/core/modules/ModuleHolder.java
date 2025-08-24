package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Interface for abstracting module modification calls and a few common helpers
 */
public interface ModuleHolder {
    
    void cloneModule(String moduleName, String newModuleName);
    
    @IndirectCallers
    default void cloneModuleFully(String moduleName, String newModuleName) {
        cloneModuleFully(moduleName,newModuleName,true);
    }
    
    default void cloneModuleFully(String moduleName, String newModuleName, boolean includeThis) {
        for(ModuleHolder holder : getAllReferents())
            if(this!=holder || includeThis) holder.cloneModule(moduleName, newModuleName);
    }
    
    default void collectLayeredReferents(Collection<ModuleHolder> referents, ModuleHolder referent) {
        for(ModuleHolder childReferent : referent.getAllReferents()) {
            if(referent==childReferent) continue;
            if(!referents.contains(childReferent)) referents.add(childReferent);
            collectLayeredReferents(referents,childReferent);
        }
    }
    
    /**
     * Get all ModuleHolder referents (including this)
     */
    Collection<ModuleHolder> getAllReferents();
    
    /**
     * Get all ModuleHolder referents (including this)
     * If layered is true, the referents of all collected referents will be added recursively
     */
    default Collection<ModuleHolder> getAllReferents(boolean layered) {
        if(!layered) return getAllReferents();
        Set<ModuleHolder> referents = new HashSet<>(getAllReferents());
        for(ModuleHolder referent : referents) collectLayeredReferents(referents,referent);
        return Collections.unmodifiableSet(referents);
    }
    
    /**
     * Shortcut for getAllReferents(true)
     */
    @IndirectCallers
    default Collection<ModuleHolder> getLayeredReferents() {
        return getAllReferents(true);
    }
    
    /**
     * Remove the module from the fields of this instance only
     */
    void removeModule(String moduleName);
    
    /**
     * Remove the module from all ModuleHolder referents (including this)
     */
    default void removeModuleFully(String moduleName) {
        removeModuleFully(moduleName,true);
    }
    
    /**
     * Remove the module from all ModuleHolder referents (including this if includeThis is true)
     */
    default void removeModuleFully(String moduleName, boolean includeThis) {
        for(ModuleHolder holder : getAllReferents())
            if(this!=holder || includeThis) holder.removeModule(moduleName);
    }
}