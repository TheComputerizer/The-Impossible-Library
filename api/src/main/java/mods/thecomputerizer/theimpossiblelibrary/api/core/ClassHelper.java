package mods.thecomputerizer.theimpossiblelibrary.api.core;

import io.github.toolfactory.jvm.util.BufferHandler;
import lombok.SneakyThrows;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.apache.commons.lang3.StringUtils;
import org.burningwave.core.assembler.StaticComponentContainer.Configuration.Default;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Streams;

public class ClassHelper {
    
    private static boolean burningWaveInit;
    
    public static void addSource(Set<String> sources, Class<?> clazz) {
        URL url = getSourceURL(clazz);
        if(Objects.nonNull(url)) sources.add(url.toString());
        else TILRef.logError("Failed to add source for {}",clazz);
    }
    
    public static boolean addSourceTo(Class<?> c, ClassLoader to) {
        if(c.getClassLoader()==to) {
            TILRef.logError("Source for {} already exists on {}!",c,to);
            return false;
        }
        boolean added = false;
        URL source = getSourceURL(c);
        CoreAPI core = CoreAPI.getInstance();
        if(Objects.nonNull(core)) added = core.addURLToClassLoader(to,source);
        else if(to instanceof URLClassLoader) added = loadURL((URLClassLoader)to,source);
        else TILRef.logError("Failed to add source for {} to {}!",c,to);
        return added;
    }
    
    public static Map<?,?> burningWaveProperties() {
        Map<Object,Object> properties = new HashMap<>();
        properties.put("banner.hide","true");
        properties.put("managed-logger.repository.enabled","false");
        return properties;
    }
    
    public static void checkBurningWaveInit() {
        if(!burningWaveInit) {
            try {
                Default.add(burningWaveProperties());
            } catch(Throwable t) {
                TILRef.logError("Failed to set default burningwave properties??",t);
            }
            burningWaveInit = true;
        }
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
     * Find the byteCode of a class at the given URL defines it on the given ClassLoader.
     * Returns null if no valud byteCode was found from the URL.
     */
    public static @Nullable Class<?> defineClass(ClassLoader loader, String name, URL url) {
        if(Objects.nonNull(url)) {
            TILDev.logInfo("Attempting to define class {} from URL {} on loader {}",name,url,loader);
            try {
                checkBurningWaveInit();
                return defineClass(loader,name,Streams.toByteBuffer(url.openStream()));
            } catch(IOException ex) {
                TILRef.logError("Failed to open stream from URL {}",url,ex);
            }
        } else TILRef.logError("Cannot define class at null URL on {}",loader);
        return null;
    }
    
    /**
     * Defines and resolves a class from byteCode
     */
    public static Class<?> defineClass(ClassLoader loader, String className, @Nullable byte[] bytes) {
        return defineClass(loader,className,Objects.nonNull(bytes) ? ByteBuffer.wrap(bytes) : null);
    }
    
    /**
     * Defines and resolves a class from byteCode
     */
    public static Class<?> defineClass(ClassLoader loader, String name, @Nullable ByteBuffer buffer) {
        if(Objects.isNull(buffer))
            throw new NullPointerException("Tried to define class with null ByteBuffer: "+name);
        try {
            checkBurningWaveInit();
            return ClassLoaders.loadOrDefineByByteCode(buffer,loader);
        } catch(Throwable t) {
            TILRef.logError("Failed to define class {} on {}",name,loader);
        }
        return null;
    }

    public static String descriptor(Class<?> clazz) {
        return Objects.nonNull(clazz) ? descriptor(clazz.getName()) : "";
    }

    public static String descriptor(String classpath) {
        return StringUtils.isNotBlank(classpath) ? "L"+internalName(classpath)+";" : "";
    }
    
    /**
     * Returns a class of the given name on the given ClassLoader.
     * Returns null without throwing any errors if the class does not exist.
     */
    public static Class<?> existsOn(String name, ClassLoader loader) {
        if(Objects.isNull(name) || name.isEmpty()) {
            TILRef.logWarn("Tried to check if class with null or empty name exists on {}",loader);
            return null;
        }
        try {
            return Class.forName(name,false,loader);
        } catch(ClassNotFoundException ex) {
            TILDev.logDebug("Class `{}` does not exist on {}",name,loader);
        }
        return null;
    }
    
    /**
     * Uses the URL of a class resources and its name to try and extract the original class path.
     * The className input here should be the relative path rather than the binary name of the class.
     */
    public static URL extractClassPath(@Nullable URL url, String className) {
        if(Objects.isNull(url)) {
            TILRef.logError("Cannot extract class path of null URL for {}!",className);
            return null;
        }
        String urlStr = url.toString().replace("%20"," ");
        String appended = (urlStr.startsWith("jar") ? "!/" : "/")+className;
        String classpath = urlStr.substring(urlStr.indexOf('/'),urlStr.length()-appended.length());
        URI uri = new File(classpath).toURI();
        try {
            return uri.toURL();
        } catch(Exception ex) {
            TILRef.logError("Failed to extract class path from {}",url,ex);
        }
        return null;
    }

    /**
     * Finds a class from the input name via the context ClassLoader.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name) {
        return findClass(name,true,Thread.currentThread().getContextClassLoader(),false);
    }

    /**
     * Finds a class from the input name via the input ClassLoader.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name, ClassLoader classLoader) {
        return findClass(name,true,classLoader,false);
    }

    /**
     * Finds a class from the input name via the context ClassLoader.
     * Set initialize to false if you don't want the Class to be loaded in case it doesn't exist.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name, boolean initialize) {
        return findClass(name,initialize,Thread.currentThread().getContextClassLoader(),false);
    }
    
    public static @Nullable Class<?> findClass(String name, boolean initialize, ClassLoader classLoader) {
        return findClass(name,initialize,classLoader,false);
    }

    /**
     * Finds a class from the input name via the input ClassLoader.
     * If the class is found on a different ClassLoader and forceLoader is true it will be defined on the given loader
     * Set initialize to false if you don't want the Class to be loaded in case it doesn't exist.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name, boolean initialize, ClassLoader classLoader,
            boolean forceLoader) {
        if(Objects.isNull(name) || name.isEmpty()) {
            TILRef.logError("Cannot find class from null or blank name!");
            return null;
        }
        try {
            Class<?> c = Class.forName(name,initialize,classLoader);
            if(forceLoader && c.getClassLoader()!=classLoader) moveClassTo(c,classLoader);
            return c;
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
        return ArrayHelper.mapTo(names,Class.class,name -> findClass(name,initialize,classLoader,false));
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
        return findClass(withPkgName(pkg,simpleName),initialize,classLoader,false);
    }
    
    public static byte[] getClassBytes(Class<?> clazz) {
        checkBurningWaveInit();
        return BufferHandler.toByteArray(Classes.getByteCode(clazz));
    }
    
    public static URL getJarResource(String path, String relativePath) {
        try {
            String prefix = path.startsWith("/") ? "jar:file:" : "jar:file:/";
            return new URL(prefix+path+"!/"+relativePath);
        } catch(Exception ex) {
            TILRef.logError("Failed to get entry {} from presumed jar file {}",relativePath,path,ex);
        }
        return null;
    }
    
    public static String getResourcePath(String className) {
        return className.replace('.','/')+".class";
    }
    
    @IndirectCallers
    public static @Nullable URL getSourceURL(@Nullable String className, ClassLoader loader) {
        if(Objects.nonNull(className) && !className.isEmpty()) {
            try {
                String relativePath = getResourcePath(className);
                return extractClassPath(loader.getResource(relativePath),relativePath);
            } catch(Exception ex) {
                TILRef.logError("Caught exception trying to get source URL for {} on {}",className,loader,ex);
            }
        } else TILRef.logError("Cannot get source URL for null or empty class name!");
        return null;
    }
    
    public static @Nullable URL getSourceURL(@Nullable Class<?> clazz) {
        if(Objects.nonNull(clazz)) {
            ProtectionDomain pd = clazz.getProtectionDomain();
            if(Objects.nonNull(pd)) {
                CodeSource source = pd.getCodeSource();
                if(Objects.nonNull(source)) return source.getLocation();
                else TILRef.logError("Cannot get source URL for class with null CodeSource! {}",clazz);
            } else TILRef.logError("Cannot get source URL for class with null ProtectionDomain! {}",clazz);
        } else TILRef.logError("Cannot get source URL for null class!");
        return null;
    }
    
    public static <T> @Nullable T initialize(@Nullable Class<T> clazz, Object ... args) {
        if(Objects.nonNull(clazz)) {
            try {
                checkBurningWaveInit();
                return Constructors.newInstanceOf(clazz,args);
            } catch(Exception ex) {
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
    public static void loadClass(ClassLoader classLoader, @Nullable Class<?> clazz) {
        if(Objects.nonNull(clazz)) classLoader.loadClass(clazz.getName());
        else TILRef.logError("Tried to load null class to {}",classLoader);
    }

    @SneakyThrows
    public static boolean loadURL(URLClassLoader classLoader, URL url) {
        TILDev.logDebug("Attempting to load URL `{}` with ClassLoader `{}`",url,classLoader);
        ReflectionHelper.invokeMethod(URLClassLoader.class,"addURL",classLoader,new Class<?>[]{URL.class},url);
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public static void moveClassTo(Class<?> c, ClassLoader target) {
        ClassLoader from = c.getClassLoader();
        if(from==target) {
            TILDev.logDebug("Not moving {} since it was already from {}",c,target);
            return;
        }
        Fields.set(c,"classLoader",target);
        ((Collection<Class<?>>)Fields.get(from,"classes")).remove(c);
        ((Collection<Class<?>>)Fields.get(target,"classes")).add(c);
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
        if(Objects.isNull(clazz)) {
            TILRef.logFatal("Cannot resolve null defined class! {}");
            return null;
        }
        checkBurningWaveInit();
        return ClassLoaders.loadOrDefine(clazz,classLoader);
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
    
    @IndirectCallers
    public static void syncSourcesAndLoadClass(ClassLoader syncFrom, ClassLoader syncTo, String className,
            BiFunction<ClassLoader,URL,Boolean> urlLoader) {
        syncSourcesForClass(syncFrom,syncTo,className,urlLoader,className);
    }
    
    @SneakyThrows
    public static Class<?> syncDirect(ClassLoader loader, Class<?> clazz) {
        TILDev.logInfo("Attempting direct sync of {} to {}",clazz,loader);
        if(loader==clazz.getClassLoader()) {
            TILRef.logError("Tried to sync {} to its own loader",clazz);
            return clazz;
        }
        return resolveClass(loader,defineClass(loader,clazz.getName(),getClassBytes(clazz)));
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
     * Returns the full name of a class via another class in the same package and its simple name or the simple name
     * if the reference class is null
     */
    @IndirectCallers
    public static String withPkgName(@Nullable Class<?> ref, String simpleName) {
        return withPkgName(Objects.nonNull(ref) ? ref.getPackage() : null,simpleName);
    }

    /**
     * Returns the full name of a class via another class in the same package and its simple name or the simple name
     * if the reference class is null
     */
    public static String withPkgName(@Nullable Package pkg, String simpleName) {
        simpleName = Objects.nonNull(simpleName) ? simpleName.replace(" ","").replace('/','.') : null;
        return Objects.nonNull(pkg) && StringUtils.isNotBlank(pkg.getName()) ? pkg.getName()+"."+simpleName : simpleName;
    }
}
