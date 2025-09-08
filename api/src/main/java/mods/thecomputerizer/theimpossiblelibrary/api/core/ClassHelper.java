package mods.thecomputerizer.theimpossiblelibrary.api.core;

import io.github.toolfactory.jvm.util.BufferHandler;
import lombok.SneakyThrows;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Streams;

public class ClassHelper {
    
    static final Logger LOGGER = TILRef.createLogger("TIL ClassHelper");
    
    /**
     * Uses the URL of a class resources and its name to try and extract the original class path.
     * The className input here should be the relative path rather than the binary name of the class.
     */
    public static URL absoluteLocation(@Nullable URL url, String className) {
        String locationStr = absoluteLocationStr(url,className);
        return Objects.nonNull(locationStr) ? FileHelper.toURL(locationStr) : null;
    }
    
    //TODO This method should probably be rewritten
    public static @Nullable String absoluteLocationStr(@Nullable URL url, String className) {
        if(Objects.isNull(url)) {
            LOGGER.error("Cannot extract class path of null URL for {}!",className);
            return null;
        }
        String urlStr = url.toString().replace("%20"," ");
        String appended = (urlStr.startsWith("jar") ? "!/" : "/")+className;
        String ret = urlStr.substring(urlStr.indexOf("/"),urlStr.length()-appended.length());
        if(ret.contains(".jar") || Hacks.isJava8()) return ret;
        //Assume the location is a directory since it isn't a jar
        int index = ret.lastIndexOf("/");
        return index==-1 ? ret : ret.substring(0,index);
    }
    
    public static void addSource(Set<String> sources, Class<?> clazz) {
        URL url = getSourceURL(clazz);
        if(Objects.nonNull(url)) sources.add(url.toString());
        else LOGGER.error("Failed to add source for {}",clazz);
    }
    
    @IndirectCallers
    public static boolean addSourceTo(Class<?> c, ClassLoader to) {
        if(c.getClassLoader()==to) {
            LOGGER.error("Source for {} already exists on {}!",c,to);
            return false;
        }
        boolean added = false;
        URL source = getSourceURL(c);
        CoreAPI core = CoreAPI.getInstance();
        if(Objects.nonNull(core)) added = core.addURLToClassLoader(to,source);
        else if(to instanceof URLClassLoader) added = loadURL((URLClassLoader)to,source);
        else LOGGER.error("Failed to add source for {} to {}!",c,to);
        return added;
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
    
    public static @Nullable Class<?> defineAndResolveClass(ClassLoader loader, String name, byte[] byteCode) {
        return resolveClass(loader,defineClass(loader,name,byteCode));
    }
    
    /**
     * Find the byteCode of a class at the given URL defines it on the given ClassLoader.
     * Returns null if no valud byteCode was found from the URL.
     */
    public static @Nullable Class<?> defineClass(ClassLoader loader, String name, URL url) {
        if(Objects.nonNull(url)) {
            TILDev.logInfo("Attempting to define class {} from URL {} on loader {}",name,url,loader);
            try {
                Hacks.checkBurningWaveInit();
                return Hacks.defineClass(loader, name, Streams.toByteBuffer(url.openStream()));
            } catch(IOException ex) {
                LOGGER.error("Failed to open stream from URL {}",url,ex);
            }
        } else LOGGER.error("Cannot define class at null URL on {}",loader);
        return null;
    }
    
    /**
     * Defines and resolves a class from byteCode
     */
    public static Class<?> defineClass(ClassLoader loader, String className, byte[] bytes) {
        return Hacks.defineClass(loader, className, Objects.nonNull(bytes) ? ByteBuffer.wrap(bytes) : null);
    }
    
    public static String descriptor(Class<?> clazz) {
        return Objects.nonNull(clazz) ? descriptor(clazz.getName()) : "";
    }

    public static String descriptor(String classpath) {
        return TextHelper.isNotBlank(classpath) ? "L"+internalName(classpath)+";" : "";
    }
    
    /**
     * Returns a class of the given name on the given ClassLoader.
     * Returns null without throwing any errors if the class does not exist.
     */
    public static Class<?> existsOn(String name, ClassLoader loader) {
        if(Objects.isNull(name) || name.isEmpty()) {
            LOGGER.warn("Tried to check if class with null or empty name exists on {}",loader);
            return null;
        }
        try {
            return Class.forName(name,false,loader);
        } catch(ClassNotFoundException ex) {
            if(DEV) LOGGER.debug("Class `{}` does not exist on {}",name,loader,ex);
            else LOGGER.debug("Class `{}` does not exist on {}",name,loader); //Ignore the stacktrace in prod
        }
        return null;
    }
    
    public static <T> T findAndInitialize(String className, Object ... args) {
        Class<?> target = findClass(className);
        return Objects.nonNull(target) ? GenericUtils.cast(initialize(target,args)) : null;
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
    
    @IndirectCallers
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
            LOGGER.error("Cannot find class from null or blank name!");
            return null;
        }
        try {
            Class<?> c = Class.forName(name,initialize,classLoader);
            if(forceLoader && c.getClassLoader()!=classLoader) moveClassTo(c,classLoader);
            return c;
        } catch(ClassNotFoundException ex) {
            LOGGER.error("Unable to find class with name `{}` using ClassLoader of type `{}`",name,
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
    
    public static @Nullable <T> Class<T> findExtensibleClass(String name, Class<?> superClass) {
        Class<T> clazz = GenericUtils.cast(findClass(name));
        return Objects.nonNull(clazz) && superClass.isAssignableFrom(clazz) ? clazz : null;
    }
    
    public static byte[] getClassBytes(Class<?> clazz) {
        Hacks.checkBurningWaveInit();
        return BufferHandler.toByteArray(Classes.getByteCode(clazz));
    }
    
    public static URL getJarResource(String path, String relativePath) {
        try {
            String prefix = path.startsWith("/") ? "jar:file:" : "jar:file:/";
            return new URL(prefix+path+"!/"+relativePath);
        } catch(Exception ex) {
            LOGGER.error("Failed to get entry {} from presumed jar file {}",relativePath,path,ex);
        }
        return null;
    }
    
    public static String getResourcePath(String className) {
        return className.replace('.','/')+".class";
    }
    
    @SuppressWarnings("LoggingSimilarMessage")
    public static @Nullable URL getSourceURL(@Nullable String className, ClassLoader loader) {
        if(Objects.nonNull(className) && !className.isEmpty()) {
            try {
                String relativePath = getResourcePath(className);
                return absoluteLocation(loader.getResource(relativePath),relativePath);
            } catch(Exception ex) {
                LOGGER.error("Caught exception trying to get source URL for {} on {}",className,loader,ex);
            }
        } else LOGGER.error("Cannot get source URL for null or empty class name!");
        return null;
    }
    
    public static @Nullable URL getSourceURL(@Nullable Class<?> clazz) {
        if(Objects.nonNull(clazz)) {
            ProtectionDomain pd = clazz.getProtectionDomain();
            if(Objects.nonNull(pd)) {
                CodeSource source = pd.getCodeSource();
                if(Objects.nonNull(source)) return source.getLocation();
                else LOGGER.error("Cannot get source URL for class with null CodeSource! {}",clazz);
            } else LOGGER.error("Cannot get source URL for class with null ProtectionDomain! {}",clazz);
        } else LOGGER.error("Cannot get source URL for null class!");
        return null;
    }
    
    @IndirectCallers
    public static @Nullable String getSourceURLStr(@Nullable String className, ClassLoader loader) {
        if(Objects.nonNull(className) && !className.isEmpty()) {
            try {
                String relativePath = getResourcePath(className);
                return absoluteLocationStr(loader.getResource(relativePath),relativePath);
            } catch(Exception ex) {
                LOGGER.error("Caught exception trying to get source URL for {} on {}",className,loader,ex);
            }
        } else LOGGER.error("Cannot get source URL for null or empty class name!");
        return null;
    }
    
    public static <T> @Nullable T initialize(@Nullable Class<T> clazz, Object ... args) {
        if(Objects.nonNull(clazz)) {
            try {
                Hacks.checkBurningWaveInit();
                return Constructors.newInstanceOf(clazz,args);
            } catch(Exception ex) {
                LOGGER.error("Failed to initialize {}",clazz,ex);
            }
        } else LOGGER.error("Cannot initialize null class");
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
        else LOGGER.error("Tried to load null class to {}",classLoader);
    }
    
    public static void loadURL(ClassLoader loader, Class<?> clazz) {
        URL source = getSourceURL(clazz);
        if(!loadURL((URLClassLoader)loader,source))
            LOGGER.error("Failed to load source for {} (URL={})",clazz,source);
    }

    @SneakyThrows
    public static boolean loadURL(URLClassLoader classLoader, URL url) {
        TILDev.logDebug("Attempting to load URL `{}` with ClassLoader `{}`",url,classLoader);
        Hacks.invokeDirect(classLoader,"addURL",url);
        return true;
    }
    
    public static void moveClassTo(Class<?> c, ClassLoader target) {
        ClassLoader from = c.getClassLoader();
        if(from==target) {
            TILDev.logDebug("Not moving {} since it was already from {}",c,target);
            return;
        }
        Hacks.setFieldDirect(c,"classLoader",target);
        Hacks.removeCollectionFieldValue("classes",c,s -> Hacks.getFieldDirect(from,s));
        Hacks.addToCollectionField("classes",c,s -> Hacks.getFieldDirect(target,s));
    }
    
    @IndirectCallers
    public static <T> T newGenericProxy(Class<T> type, String namedMethod, String intermediaryMethod,
            Function<Object[],Object> handler) {
        return newGenericProxy(type,Hacks.isNamedEnv() ? namedMethod : intermediaryMethod,handler);
    }
    
    public static <T> T newGenericProxy(Class<T> type, String methodName, Function<Object[],Object> handler) {
        return newGenericProxy(type,method -> methodName.equals(method.getName()),handler);
    }
    
    public static <T> T newGenericProxy(Class<T> type, Function<Method,Boolean> methodMatcher,
            Function<Object[],Object> argsHandler) {
        return newProxy(type,(proxy,method,args) -> {
            switch(method.getName()) {
                case "equals": return args.length>0 && proxy==args[0];
                case "hashCode": return 0;
                default: {
                    LOGGER.info("Invoking generic proxy method {}",method.getName());
                    return methodMatcher.apply(method) ? argsHandler.apply(args) : null;
                }
            }
        });
    }
    
    public static <T> T newProxy(Class<T> type, InvocationHandler handler) {
        return newProxy(type.getClassLoader(),type,handler);
    }
    
    public static <T> T newProxy(ClassLoader loader, Class<T> type, InvocationHandler handler) {
        return newProxy(loader,handler,new Class<?>[]{type});
    }
    
    public static <T> T newProxy(ClassLoader loader, InvocationHandler handler, Class<?> ... types) {
        return GenericUtils.cast(Proxy.newProxyInstance(loader,types,handler));
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
            LOGGER.fatal("Cannot resolve null defined class! (ClassLoader = {})",classLoader);
            return null;
        }
        Hacks.checkBurningWaveInit();
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
        if(TextHelper.isBlank(desc)) return "";
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
        if(TextHelper.isBlank(name)) return "";
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
            LOGGER.error("Tried to sync {} to its own loader",clazz);
            return clazz;
        }
        return resolveClass(loader,defineClass(loader,clazz.getName(),getClassBytes(clazz)));
    }
    
    public static void syncSourcesForClass(ClassLoader syncFrom, ClassLoader syncTo, String className,
            BiFunction<ClassLoader,URL,Boolean> urlLoader, @Nullable String ... classesToLoad) {
        try {
            LOGGER.debug("Attempting to sync class loaders for {} ({} -> {})",className,syncFrom,syncTo);
            URL url = getSourceURL(syncFrom.loadClass(className));
            if(Objects.nonNull(url)) {
                LOGGER.debug("Syncing URL {}",url);
                if(!urlLoader.apply(syncTo,url))
                    LOGGER.error("Failed to sync sources for {} from {} to {}!",className,syncFrom,syncTo);
                else if(Objects.nonNull(classesToLoad))
                    for(String classToLoad : classesToLoad) findClass(classToLoad,syncTo);
            } else LOGGER.debug("Not syncing null URL");
        } catch(ClassNotFoundException ex) {
            ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
            if(Misc.equalsAny(systemLoader,syncFrom,syncTo))
                LOGGER.error("Failed to sync sources for {} from {} to {}!",className,syncFrom,syncTo,ex);
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
        return Objects.nonNull(pkg) && TextHelper.isNotBlank(pkg.getName()) ? pkg.getName()+"."+simpleName : simpleName;
    }
}