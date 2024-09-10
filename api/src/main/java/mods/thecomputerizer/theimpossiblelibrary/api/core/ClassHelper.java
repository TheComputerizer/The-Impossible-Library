package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.SneakyThrows;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

public class ClassHelper {
    
    public static void addSource(Set<String> sources, Class<?> clazz) {
        URL url = getSourceURL(clazz);
        if(Objects.nonNull(url)) sources.add(url.toString());
        else TILRef.logError("Failed to add source for {}",clazz);
    }

    /**
     * Returns the full name of the class of the object or an empty string if the object is null.
     */
    public static String className(@Nullable Object obj) {
        return className(Objects.nonNull(obj) ? obj.getClass() : null,false);
    }

    /**
     * Returns the full name of the class or an empty string if the class is null.
     */
    public static String className(@Nullable Class<?> clazz) {
        return className(clazz,false);
    }

    /**
     * Returns either the full or simple name of the class of the object or an empty string if the object is null.
     */
    public static String className(@Nullable Object obj, boolean simple) {
        return className(Objects.nonNull(obj) ? obj.getClass() : null,simple);
    }

    /**
     * Returns either the full or simple name of the class or an empty string if the class is null.
     */
    public static String className(@Nullable Class<?> clazz, boolean simple) {
        return Objects.nonNull(clazz) ? (simple ? clazz.getSimpleName() : clazz.getName()) : "";
    }
    
    /**
     * Defines and resolves a class from byteCode
     */
    @SneakyThrows
    public static Class<?> defineClass(ClassLoader classLoader, String classpath, byte[] bytes) {
        return (Class<?>)ReflectionHelper.invokeMethod(ClassLoader.class,"defineClass",classLoader,new Class<?>[]{
                String.class,byte[].class,int.class,int.class},classpath,bytes,0,bytes.length);
    }

    public static String descriptor(Class<?> clazz) {
        return Objects.nonNull(clazz) ? descriptor(clazz.getName()) : "";
    }

    public static String descriptor(String classpath) {
        return StringUtils.isNotBlank(classpath) ? "L"+internalName(classpath)+";" : "";
    }
    
    @IndirectCallers
    public static @Nullable String getResourcePath(@Nullable Class<?> clazz) {
        return Objects.nonNull(clazz) ? clazz.getName().replace('.','/')+".class" : null;
    }
    
    @IndirectCallers
    public static @Nullable URL getSourceURL(@Nullable String className, ClassLoader loader) {
        if(StringUtils.isNotBlank(className)) {
            try {
                return getSourceURL(loader.loadClass(className));
            } catch(ClassNotFoundException ex) {
                TILRef.logError("Failed to find class {} on loader {}", className, loader, ex);
            }
        } else TILRef.logError("Cannot get source URL for blank class name");
        return null;
    }
    
    public static @Nullable URL getSourceURL(@Nullable Class<?> clazz) {
        if(Objects.nonNull(clazz)) {
            ProtectionDomain domain = clazz.getProtectionDomain();
            if(Objects.nonNull(domain)) {
                CodeSource source = domain.getCodeSource();
                if(Objects.nonNull(source)) return source.getLocation();
                TILRef.logError("Cannot get URL for null CodeSource of {}",clazz);
            }
            TILRef.logError("Cannot get URL for null ProtectionDomain of {}",clazz);
        }
        TILRef.logError("Cannot get URL for null class");
        return null;
    }

    /**
     * Finds a class from the input name via the ClassLoader instance of this class.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name) {
        return findClass(name,true,ClassHelper.class.getClassLoader());
    }

    /**
     * Finds a class from the input name via the input ClassLoader.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name, ClassLoader classLoader) {
        return findClass(name,true,classLoader);
    }

    /**
     * Finds a class from the input name via the ClassLoader instance of this class.
     * Set initialize to false if you don't want the Class to be loaded in case it doesn't exist.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name, boolean initialize) {
        return findClass(name,initialize,ClassHelper.class.getClassLoader());
    }

    /**
     * Finds a class from the input name via the input ClassLoader.
     * Set initialize to false if you don't want the Class to be loaded in case it doesn't exist.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name, boolean initialize, ClassLoader classLoader) {
        if(Objects.isNull(name) || name.isEmpty()) {
            TILRef.logError("Cannot find class from null or blank name!");
            return null;
        }
        try {
            return Class.forName(name,initialize,classLoader);
        } catch(ClassNotFoundException ex) {
            TILRef.logError("Unable to find class with name `{}` using ClassLoader of type `{}`",name,
                    classLoader.getClass().getName(),ex);
            return null;
        }
    }
    
    @IndirectCallers
    public static @Nullable Class<?>[] findClasses(String ... names) {
        return ArrayHelper.mapTo(names,Class.class,ClassHelper::findClass);
    }
    
    @IndirectCallers
    public static @Nullable Class<?>[] findClasses(ClassLoader classLoader, String ... names) {
        return ArrayHelper.mapTo(names,Class.class,name -> findClass(name,classLoader));
    }
    
    @IndirectCallers
    public static @Nullable Class<?>[] findClasses(boolean initialize, String ... names) {
        return ArrayHelper.mapTo(names,Class.class,name -> findClass(name,initialize));
    }
    
    @IndirectCallers
    public static @Nullable Class<?>[] findClasses(boolean initialize, ClassLoader classLoader, String ... names) {
        return ArrayHelper.mapTo(names,Class.class,name -> findClass(name,initialize,classLoader));
    }
    
    @IndirectCallers
    public static @Nullable Class<?> findClassFrom(@Nullable Class<?> clazz, String simpleName) {
        return findClassFrom(Objects.nonNull(clazz) ? clazz.getPackage() : null,simpleName);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Package pkg, String simpleName) {
        return findClass(withPkgName(pkg,simpleName));
    }
    
    @IndirectCallers
    public static @Nullable Class<?> findClassFrom(@Nullable Class<?> clazz, String simpleName, ClassLoader classLoader) {
        return findClassFrom(Objects.nonNull(clazz) ? clazz.getPackage() : null,simpleName,classLoader);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Package pkg, String simpleName, ClassLoader classLoader) {
        return findClass(withPkgName(pkg,simpleName),classLoader);
    }
    
    @IndirectCallers
    public static @Nullable Class<?> findClassFrom(@Nullable Class<?> clazz, String simpleName, boolean initialize) {
        return findClassFrom(Objects.nonNull(clazz) ? clazz.getPackage() : null,simpleName,initialize);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Package pkg, String simpleName, boolean initialize) {
        return findClass(withPkgName(pkg,simpleName),initialize);
    }
    
    @IndirectCallers
    public static @Nullable Class<?> findClassFrom(@Nullable Class<?> clazz, String simpleName, boolean initialize,
                                                   ClassLoader classLoader) {
        return findClassFrom(Objects.nonNull(clazz) ? clazz.getPackage() : null,simpleName,initialize,classLoader);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Package pkg, String simpleName, boolean initialize,
                                                   ClassLoader classLoader) {
        return findClass(withPkgName(pkg,simpleName),initialize,classLoader);
    }
    
    public static <T> @Nullable T initialize(@Nullable Class<T> clazz) {
        if(Objects.nonNull(clazz)) {
            try {
                return clazz.newInstance();
            } catch(InstantiationException | IllegalAccessException ex) {
                TILRef.logError("Failed to initialize {}",clazz,ex);
            }
        } else TILRef.logError("Cannot initialize null class");
        return null;
    }
    
    @IndirectCallers
    public static String internalName(Class<?> clazz) {
        return internalName(clazz.getName());
    }

    public static String internalName(String classpath) {
        return classpath.replace('.','/');
    }
    
    @IndirectCallers
    public static void loadClass(String classpath, byte[] bytes) {
        loadClass(ClassLoader.getSystemClassLoader(),classpath,bytes);
    }

    public static void loadClass(ClassLoader classLoader, String classpath, byte[] bytes) {
        loadClass(classLoader,defineClass(classLoader,classpath,bytes));
    }
    
    @IndirectCallers
    public static void loadClass(Class<?> clazz) {
        loadClass(ClassLoader.getSystemClassLoader(),clazz);
    }

    @SneakyThrows
    public static void loadClass(ClassLoader classLoader, Class<?> clazz) {
        classLoader.loadClass(clazz.getName());
    }

    @SneakyThrows
    public static boolean loadURL(URLClassLoader classLoader, URL url) {
        TILRef.logTrace("Attempting to load URL `{}` with ClassLoader `{}`",url,classLoader);
        ReflectionHelper.invokeMethod(URLClassLoader.class,"addURL",classLoader,new Class<?>[]{URL.class},url);
        return true;
    }
    
    @IndirectCallers
    public static String packageName(@Nullable Class<?> clazz) {
        return Objects.nonNull(clazz) ? clazz.getPackage().getName() : "";
    }
    
    /**
     * Defines and resolves a class from byteCode
     */
    @SuppressWarnings("UnusedReturnValue")
    @SneakyThrows
    public static Class<?> resolveClass(ClassLoader classLoader, @Nullable Class<?> clazz) {
        if(Objects.nonNull(clazz))
            ReflectionHelper.invokeMethod(ClassLoader.class,"resolveClass",classLoader,new Class<?>[]{
                    Class.class},clazz);
        else TILRef.logFatal("Cannot resolve null defined class!");
        return clazz;
    }

    /**
     * Builds a signature via classes
     */
    @IndirectCallers
    public static String signature(Class<?> clazz, Class<?> ... parameters) {
        return signatureDesc(descriptor(clazz),ArrayHelper.mapTo(parameters,String.class,ClassHelper::descriptor));
    }

    /**
     * Builds a signature via classpaths
     */
    @IndirectCallers
    public static String signatureClasspath(String classpath, String ... parameterPaths) {
        return signatureDesc(descriptor(classpath),ArrayHelper.mapTo(parameterPaths,String.class,ClassHelper::descriptor));
    }

    /**
     * Builds a signature via class descriptors
     */
    public static String signatureDesc(String desc, String ... parameterDescs) {
        if(StringUtils.isBlank(desc)) return "";
        StringBuilder builder = new StringBuilder(desc.substring(0,desc.length()-1)).append("<");
        if(ArrayHelper.isNotEmpty(parameterDescs))
            for(String parameter : parameterDescs) builder.append(parameter);
        return builder.append(">;").toString();
    }

    /**
     * Builds a signature via internal names
     */
    @IndirectCallers
    public static String signatureInternal(String name, String ... parameterNames) {
        if(StringUtils.isBlank(name)) return "";
        return signatureDesc("L"+name+";",ArrayHelper.mapTo(parameterNames,String.class,p -> "L"+p+";"));
    }
    
    public static void syncSourcesAndLoadClass(ClassLoader syncFrom, ClassLoader syncTo, String className) {
        syncSourcesForClass(syncFrom,syncTo,className,className);
    }
    
    @IndirectCallers
    public static void syncSourcesAndLoadClass(ClassLoader syncFrom, ClassLoader syncTo, String className,
            BiFunction<ClassLoader,URL,Boolean> urlLoader) {
        syncSourcesForClass(syncFrom,syncTo,className,urlLoader,className);
    }
    
    public static void syncSourcesForClass(ClassLoader syncFrom, ClassLoader syncTo, String className,
            @Nullable String ... classesToLoad) {
        syncSourcesForClass(syncFrom,syncTo,className,(loader,url) ->
                CoreAPI.getInstance(syncFrom).addURLToClassLoader(loader,url),classesToLoad);
    }
    
    
    public static void syncSourcesForClass(ClassLoader syncFrom, ClassLoader syncTo, String className,
            BiFunction<ClassLoader,URL,Boolean> urlLoader, @Nullable String ... classesToLoad) {
        try {
            TILRef.logDebug("Attempting to sync class loaders for {} ({} -> {})",className,syncFrom,syncTo);
            URL url = getSourceURL(syncFrom.loadClass(className));
            if(Objects.nonNull(url)) {
                TILRef.logDebug("Syncing URL {}",url);
                if(!urlLoader.apply(syncTo,url))
                    TILRef.logError("Failed to sync sources for {} from {} to {}!",className,syncFrom,syncTo);
                else if(Objects.nonNull(classesToLoad))
                    for(String classToLoad : classesToLoad) findClass(classToLoad,syncTo);
            } else TILRef.logDebug("Not syncing null URL");
        } catch(ClassNotFoundException ex) {
            ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
            if(Misc.equalsAny(systemLoader,syncFrom,syncTo))
                TILRef.logError("Failed to sync sources for {} from {} to {}!",className,syncFrom,syncTo,ex);
            else syncSourcesForClass(systemLoader,syncTo,className,urlLoader,classesToLoad);
        }
    }

    /**
     * Returns the classpath of a class via another class in the same package and its simple name or the simple name
     * if the reference class is null
     */
    @IndirectCallers
    public static String withPkgName(@Nullable Class<?> ref, String simpleName) {
        return withPkgName(Objects.nonNull(ref) ? ref.getPackage() : null,simpleName);
    }

    /**
     * Returns the classpath of a class via another class in the same package and its simple name or the simple name
     * if the reference class is null
     */
    public static String withPkgName(@Nullable Package pkg, String simpleName) {
        simpleName = Objects.nonNull(simpleName) ? simpleName.replace(" ","").replace('/','.') : null;
        return Objects.nonNull(pkg) && StringUtils.isNotBlank(pkg.getName()) ? pkg.getName()+"."+simpleName : simpleName;
    }
}
