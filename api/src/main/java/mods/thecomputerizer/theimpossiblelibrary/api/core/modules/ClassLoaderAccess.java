package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

/**
 * java.lang.ClassLoader
 */
public class ClassLoaderAccess extends AbstractModuleSystemAccessor {
    
    protected ClassLoaderAccess(ClassLoader loader, Object accessorOrLogger) {
        super(loader,accessorOrLogger);
    }
    
    public ClassAccess accessClass(Class<?> c) {
        return getClassAccess(c);
    }
    
    @IndirectCallers
    public void addClassAccesses(Collection<ClassAccess> addThese) {
        Collection<Class<?>> classes = classes(false);
        for(ClassAccess c : addThese) classes.add(c.unwrap());
    }
    
    public void addClasses(Collection<Class<?>> addThese) {
        Collection<Class<?>> classes = classes(false);
        classes.addAll(addThese);
    }
    
    public void addPackages(Map<String,Object> pkgMap, @Nullable ModuleAccess module) {
        Map<String,Object> packages = packages();
        for(Entry<String,Object> entry : pkgMap.entrySet()) {
            Object pkg = entry.getValue();
            if(Objects.nonNull(pkg)) {
                if(Objects.nonNull(module)) Hacks.setFieldDirect(pkg,"module",module.access);
                packages.put(entry.getKey(),pkg);
            }
        }
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
    
    public Object getPackage(String pkgName) {
        return packages().get(pkgName);
    }
    
    public void inheritClasses(ModuleAccess module, String moduleName, ClassLoaderAccess ... loaders) {
        inheritClasses(module,new String[]{moduleName},loaders);
    }
    
    /**
     * We need to account for the fact that classes in this package might be getting moved.
     * As such, some API calls are subverted due to the API classes potentially being moved
     */
    public void inheritClasses(ModuleAccess module, String[] moduleNames, ClassLoaderAccess ... loaders) {
        String moduleName = module.getName();
        for(ClassAccess c : classesAccess()) c.setModuleIfIn(module,moduleName);
        for(ClassLoaderAccess loader : loaders) {
            Set<Class<?>> classes = loader.moveModuleClasses(module,moduleNames);
            addClasses(classes);
            loader.removeClasses(classes);
        }
    }
    
    /**
     * We need to account for the fact that classes in this package might be getting moved.
     * As such, some API calls are subverted due to the API classes potentially being moved
     */
    public Set<Class<?>> moveModuleClasses(ModuleAccess module, String ... moduleNames) {
        Set<Class<?>> movedClasses = new HashSet<>();
        for(ClassAccess c : classesAccess())
            if(c.inModule(moduleNames)) movedClasses.add(c.unwrap());
        if(!movedClasses.isEmpty()) {
            ClassLoader loader = unwrap();
            Object moduleObj = module.access();
            for(Class<?> c : movedClasses) {
                Fields.setDirect(c,"classLoader",loader);
                Fields.setDirect(c,"module",moduleObj);
            }
        }
        return movedClasses;
    }
    
    public void moveModuleClassesTo(ModuleAccess module, ClassLoaderAccess target) {
        moveModuleClassesTo(module,target.unwrap(),target);
    }
    
    @IndirectCallers
    public void moveModuleClassesTo(ModuleAccess module, ClassLoader target) {
        ClassLoaderAccess targetAccess = ModuleSystemAccessor.getClassLoader(target,this);
        moveModuleClassesTo(module,target,targetAccess);
    }
    
    public void moveModuleClassesTo(ModuleAccess module, ClassLoader target, ClassLoaderAccess targetAccess) {
        Set<Class<?>> movedClasses = new HashSet<>();
        Map<String,Object> movedPackages = new HashMap<>();
        for(ClassAccess c : classesAccess()) {
            if(c.inModule(module.getName())) {
                c.setClassLoader(target);
                movedClasses.add(c.unwrap());
                String pkgName = c.pkgName();
                Object pkg = getPackage(pkgName);
                if(Objects.nonNull(pkg)) movedPackages.put(pkgName,pkg);
            }
        }
        targetAccess.addClasses(movedClasses);
        targetAccess.addPackages(movedPackages,module);
        removeClasses(movedClasses);
        Map<String,Object> packages = packages();
        for(String pkgName : movedPackages.keySet()) packages.remove(pkgName);
    }
    
    public Map<String,Object> packages() {
        Map<String,Object> pkgMap = getDirect("packages");
        return Objects.nonNull(pkgMap) ? pkgMap : Collections.emptyMap();
    }
    
    @IndirectCallers
    public void removeClasses(Collection<Class<?>> classes) {
        classes(false).removeAll(classes);
    }
    
    @IndirectCallers
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