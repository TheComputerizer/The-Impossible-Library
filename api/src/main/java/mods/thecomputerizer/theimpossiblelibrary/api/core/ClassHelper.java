package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Objects;
import java.util.function.BiFunction;

@SuppressWarnings("unused") public class ClassHelper {

    private static final MethodHandle CLASS_DEFINER = ReflectionHelper.findMethodHandle(ClassLoader.class,
            "defineClass",String.class,byte[].class,int.class,int.class);
    private static final MethodHandle CLASS_RESOLVER = ReflectionHelper.findMethodHandle(ClassLoader.class,
            "resolveClass",Class.class);
    private static final MethodHandle URL_LOADER = ReflectionHelper.findMethodHandle(URLClassLoader.class,
            "addURL", URL.class);

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
    @SuppressWarnings("DataFlowIssue")
    public static Class<?> defineClass(ClassLoader classLoader, String classpath, byte[] bytes) {
        return (Class<?>)CLASS_DEFINER.invoke(classLoader,classpath,bytes,0,bytes.length);
    }

    public static String descriptor(Class<?> clazz) {
        return Objects.nonNull(clazz) ? descriptor(clazz.getName()) : "";
    }

    public static String descriptor(String classpath) {
        return StringUtils.isNotBlank(classpath) ? "L"+internalName(classpath)+";" : "";
    }
    
    public static @Nullable String getResourcePath(@Nullable Class<?> clazz) {
        return Objects.nonNull(clazz) ? clazz.getName().replace('.','/')+".class" : null;
    }
    
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
    public static @Nullable Class<?> findClass(String name, boolean inititalize) {
        return findClass(name,inititalize,ClassHelper.class.getClassLoader());
    }

    /**
     * Finds a class from the input name via the input ClassLoader.
     * Set initialize to false if you don't want the Class to be loaded in case it doesn't exist.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name, boolean inititalize, ClassLoader classLoader) {
        if(StringUtils.isBlank(name)) {
            TILRef.logError("Cannot find class from null or blank name!");
            return null;
        }
        try {
            return Class.forName(name,inititalize,classLoader);
        } catch(ClassNotFoundException ex) {
            TILRef.logError("Unable to find class with name `{}` using ClassLoader of type `{}`",name,
                    classLoader.getClass().getName());
            return null;
        }
    }
    
    public static @Nullable Class<?>[] findClasses(String ... names) {
        return ArrayHelper.mapTo(names,Class.class,ClassHelper::findClass);
    }
    
    public static @Nullable Class<?>[] findClasses(ClassLoader classLoader, String ... names) {
        return ArrayHelper.mapTo(names,Class.class,name -> findClass(name,classLoader));
    }
    
    public static @Nullable Class<?>[] findClasses(boolean inititalize, String ... names) {
        return ArrayHelper.mapTo(names,Class.class,name -> findClass(name,inititalize));
    }
    
    public static @Nullable Class<?>[] findClasses(boolean inititalize, ClassLoader classLoader, String ... names) {
        return ArrayHelper.mapTo(names,Class.class,name -> findClass(name,inititalize,classLoader));
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Class<?> clazz, String simpleName) {
        return findClassFrom(Objects.nonNull(clazz) ? clazz.getPackage() : null,simpleName);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Package pkg, String simpleName) {
        return findClass(withPkgName(pkg,simpleName));
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Class<?> clazz, String simpleName, ClassLoader classLoader) {
        return findClassFrom(Objects.nonNull(clazz) ? clazz.getPackage() : null,simpleName,classLoader);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Package pkg, String simpleName, ClassLoader classLoader) {
        return findClass(withPkgName(pkg,simpleName),classLoader);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Class<?> clazz, String simpleName, boolean inititalize) {
        return findClassFrom(Objects.nonNull(clazz) ? clazz.getPackage() : null,simpleName,inititalize);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Package pkg, String simpleName, boolean inititalize) {
        return findClass(withPkgName(pkg,simpleName),inititalize);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Class<?> clazz, String simpleName, boolean inititalize,
                                                   ClassLoader classLoader) {
        return findClassFrom(Objects.nonNull(clazz) ? clazz.getPackage() : null,simpleName,inititalize,classLoader);
    }

    public static @Nullable Class<?> findClassFrom(@Nullable Package pkg, String simpleName, boolean inititalize,
                                                   ClassLoader classLoader) {
        return findClass(withPkgName(pkg,simpleName),inititalize,classLoader);
    }

    public static String internalName(Class<?> clazz) {
        return internalName(clazz.getName());
    }

    public static String internalName(String classpath) {
        return classpath.replace('.','/');
    }

    public static void loadClass(String classpath, byte[] bytes) {
        loadClass(ClassLoader.getSystemClassLoader(),classpath,bytes);
    }

    public static void loadClass(ClassLoader classLoader, String classpath, byte[] bytes) {
        if(Objects.nonNull(CLASS_DEFINER)) loadClass(classLoader,defineClass(classLoader,classpath,bytes));
        else TILRef.logError("Cannot load class `{}` since the CLASS_DEFINER handle failed to intialize",classpath);
    }

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
        if(Objects.nonNull(URL_LOADER)) {
            URL_LOADER.invoke(classLoader,url);
            return true;
        }
        else TILRef.logError("Cannot load URL `{}` since the URL_LOADER handle failed to intialize",url);
        return false;
    }

    public static String packageName(@Nullable Class<?> clazz) {
        return Objects.nonNull(clazz) ? clazz.getPackage().getName() : "";
    }
    
    /**
     * Defines and resolves a class from byteCode
     */
    @SneakyThrows
    @SuppressWarnings("DataFlowIssue")
    public static Class<?> resolveClass(ClassLoader classLoader, @Nullable Class<?> clazz) {
        if(Objects.nonNull(clazz)) CLASS_RESOLVER.invoke(classLoader,clazz);
        else TILRef.logFatal("Cannot resolve null defined class!");
        return clazz;
    }

    /**
     * Builds a signature via classes
     */
    public static String signature(Class<?> clazz, Class<?> ... parameters) {
        return signatureDesc(descriptor(clazz),ArrayHelper.mapTo(parameters,String.class,ClassHelper::descriptor));
    }

    /**
     * Builds a signature via classpaths
     */
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
    public static String signatureInternal(String name, String ... parameterNames) {
        if(StringUtils.isBlank(name)) return "";
        return signatureDesc("L"+name+";",ArrayHelper.mapTo(parameterNames,String.class,p -> "L"+p+";"));
    }
    
    public static void syncSourcesAndLoadClass(ClassLoader syncFrom, ClassLoader syncTo, String className) {
        syncSourcesForClass(syncFrom,syncTo,className,className);
    }
    
    public static void syncSourcesAndLoadClass(ClassLoader syncFrom, ClassLoader syncTo, String className,
            BiFunction<ClassLoader,URL,Boolean> urlLoader) {
        syncSourcesForClass(syncFrom,syncTo,className,urlLoader,className);
    }
    
    public static void syncSourcesForClass(ClassLoader syncFrom, ClassLoader syncTo, String className,
            @Nullable String ... classesToLoad) {
        syncSourcesForClass(syncFrom,syncTo,className, (loader,url) ->
                CoreAPI.getInstance().addURLToClassLoader(loader,url),classesToLoad);
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
            TILRef.logError("Failed to sync sources for {} from {} to {}!",className,syncFrom,syncTo,ex);
        }
    }

    /**
     * Returns the classpath of a class via another class in the same package and its simple name or the simple name
     * if the reference class is null
     */
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
