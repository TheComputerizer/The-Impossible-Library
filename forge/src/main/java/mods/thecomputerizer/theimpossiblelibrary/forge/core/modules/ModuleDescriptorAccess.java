package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * java.lang.module.ModuleDescriptor
 */
public class ModuleDescriptorAccess extends AbstractModuleSystemAccessor {
    
    ModuleDescriptorAccess(Object moduleDescriptor, Object accessorOrLogger) {
        super(moduleDescriptor,accessorOrLogger);
    }
    
    @IndirectCallers
    public void addPackages(Collection<String> addThese) {
        Set<String> packages = new HashSet<>(packages());
        packages.addAll(addThese);
        setPackages(packages);
    }
    
    void addSet(Set<Object> set, String fieldName) {
        if(Objects.isNull(set) || set.isEmpty()) return;
        Set<Object> values = new HashSet<>(getSet(fieldName));
        values.addAll(set);
        setDirect(fieldName,values);
    }
    
    <T> Set<T> getSet(String fieldName) {
        return getDirect(fieldName);
    }
    
    public void inheritFrom(ModuleDescriptorAccess moduleDescriptor) {
        for(String fieldName : new String[]{"exports","opens","packages","provides","requires","uses"})
            inheritSet(moduleDescriptor,fieldName);
    }
    
    public void inheritSet(ModuleDescriptorAccess moduleDescriptor, String fieldName) {
        addSet(moduleDescriptor.getSet(fieldName),fieldName);
    }
    
    public String name() {
        return invokeDirect("name");
    }
    
    @IndirectCallers
    public ModuleReferenceAccess newModuleFinderReference(ModFileInfoAccess fileInfo) {
        SecureJarAccess secureJar = fileInfo.secureJar();
        return secureJar.newModuleFinder().getModuleReference(secureJar.name(),this);
    }
    
    public Set<String> packages() {
        return getSet("packages");
    }
    
    public void setName(String name) {
        setDirect("name",name);
    }
    
    public void setPackages(Set<String> packages) {
        setDirect("packages",packages);
    }
}