package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * java.lang.ClassLoader
 */
public class ClassLoaderAccess extends AbstractModuleSystemAccessor {
    
    ClassLoaderAccess(ClassLoader loader, Object accessorOrLogger) {
        super(loader,accessorOrLogger);
    }
    
    public ClassAccess accessClass(Class<?> c) {
        return getClassAccess(c);
    }
    
    public void addClasses(Collection<ClassAccess> addThese) {
        Collection<Class<?>> classes = classes(false);
        for(ClassAccess c : addThese) classes.add(c.unwrap());
    }
    
    public Collection<Class<?>> classes() {
        return classes(true);
    }
    
    public Collection<Class<?>> classes(boolean copy) {
        Collection<Class<?>> classes = getDirect("classes");
        if(Objects.isNull(classes)) return Collections.emptySet();
        return copy ? new HashSet<>(classes) : classes;
    }
    
    public Collection<ClassAccess> classesAccess() {
        return classes().stream().map(this::accessClass).collect(Collectors.toSet());
    }
    
    @IndirectCallers
    public String getModuleNameForClass(Class<?> c) {
        return accessClass(c).getModuleName();
    }
    
    @IndirectCallers
    public void inheritClasses(ModuleReferenceHolder ref, String moduleName, ClassLoaderAccess ... loaders) {
        inheritClasses(ref,new String[]{moduleName},loaders);
    }
    
    public void inheritClasses(ModuleAccess module, String moduleName, ClassLoaderAccess ... loaders) {
        inheritClasses(module,new String[]{moduleName},loaders);
    }
    
    public void inheritClasses(ModuleReferenceHolder ref, String[] moduleNames, ClassLoaderAccess ... loaders) {
        inheritClasses(ref.getModule(),moduleNames,loaders);
    }
    
    public void inheritClasses(ModuleAccess module, String[] moduleNames, ClassLoaderAccess ... loaders) {
        String moduleName = module.getName();
        for(ClassAccess c : classesAccess()) c.setModuleIfIn(module,moduleName);
        for(ClassLoaderAccess loader : loaders) {
            Set<ClassAccess> classes = loader.moveModuleClasses(module,moduleNames);
            addClasses(classes);
            loader.removeWrappedClasses(classes);
        }
    }
    
    public Set<ClassAccess> moveModuleClasses(ModuleAccess module, String ... moduleNames) {
        Set<ClassAccess> movedClasses = new HashSet<>();
        for(ClassAccess c : classesAccess())
            if(c.moveIfInModule(this,module,moduleNames)) movedClasses.add(c);
        return movedClasses;
    }
    
    @IndirectCallers
    public void removeClasses(Collection<Class<?>> classes) {
        classes(false).removeAll(classes);
    }
    
    public void removeWrappedClasses(Collection<ClassAccess> removeThese) {
        Collection<Class<?>> classes = classes(false);
        for(ClassAccess c : removeThese) classes.remove(c.unwrap());
    }
    
    @IndirectCallers
    public void setClassModules(ModuleAccess module, String moduleName) {
        for(ClassAccess c : classesAccess()) c.setModuleIfIn(module,moduleName);
    }
    
    public ClassLoader unwrap() {
        return (ClassLoader)this.access;
    }
}