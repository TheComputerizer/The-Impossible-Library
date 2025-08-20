package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import java.util.Collection;

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
    
    public Collection<Class<?>> classes() {
        return getDirect("classes");
    }
    
    public String getModuleNameForClass(Class<?> c) {
        return accessClass(c).getModuleName();
    }
    
    public void removeClasses(Collection<Class<?>> classes) {
        classes().removeAll(classes);
    }
    
    public ClassLoader unwrap() {
        return (ClassLoader)this.access;
    }
}