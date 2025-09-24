package mods.thecomputerizer.theimpossiblelibrary.api.core;

import io.github.toolfactory.jvm.util.BufferHandler;
import lombok.SneakyThrows;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.io.IOUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

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
            Hacks.checkBurningWaveInit();
            ByteBuffer buffer = IOUtils.toBuffer(url);
            if(Objects.nonNull(buffer)) return Hacks.defineClass(loader,name,buffer);
            LOGGER.error("Cannot define class after buffer retrieval failure: {}",name);
            return null;
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
    
    public static @Nullable <T> Class<T> findExtensibleClass(String name, Class<?> superClass) {
        Class<T> clazz = GenericUtils.cast(Hacks.findClass(name));
        return Objects.nonNull(clazz) && superClass.isAssignableFrom(clazz) ? clazz : null;
    }
    
    public static byte[] getClassBytes(@Nullable Class<?> clazz) {
        if(Objects.isNull(clazz)) {
            LOGGER.error("Cannot get bytecode for null class!");
            return null;
        }
        ByteBuffer byteCode = Hacks.getByteCode(clazz);
        if(Objects.isNull(byteCode)) {
            LOGGER.error("Bytecode not found for {}!",clazz);
            return new byte[]{};
        }
        return BufferHandler.toByteArray(byteCode);
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
    
    @IndirectCallers
    public static <T> T newGenericProxy(ClassLoader loader, String typeName, String namedMethod,
            String intermediaryMethod, Function<Object[],Object> handler) {
        return newGenericProxy(loader,typeName,Hacks.isNamedEnv() ? namedMethod : intermediaryMethod,handler);
    }
    
    @IndirectCallers
    public static <T> T newGenericProxy(String typeName, String namedMethod, String intermediaryMethod,
            Function<Object[],Object> handler) {
        return newGenericProxy(typeName,Hacks.isNamedEnv() ? namedMethod : intermediaryMethod,handler);
    }
    
    @IndirectCallers
    public static <T> T newGenericProxy(Class<T> type, String namedMethod, String intermediaryMethod,
            Function<Object[],Object> handler) {
        return newGenericProxy(type,Hacks.isNamedEnv() ? namedMethod : intermediaryMethod,handler);
    }
    
    public static <T> T newGenericProxy(String typeName, String methodName, Function<Object[],Object> handler) {
        return newGenericProxy(Hacks.contextClassLoader(),typeName,methodName,handler);
    }
    
    public static <T> T newGenericProxy(ClassLoader loader, String typeName, String methodName, Function<Object[],Object> handler) {
        return newGenericProxy(loader,typeName,method -> methodName.equals(method.getName()),handler);
    }
    
    public static <T> T newGenericProxy(Class<T> type, String methodName, Function<Object[],Object> handler) {
        return newGenericProxy(type,method -> methodName.equals(method.getName()),handler);
    }
    
    @IndirectCallers
    public static <T> T newGenericProxy(ClassLoader loader, String typeName, BiFunction<String,Object[],Object> handler) {
        return newProxy(typeName,proxyInvoker(handler));
    }
    
    @IndirectCallers
    public static <T> T newGenericProxy(String typeName, BiFunction<String,Object[],Object> handler) {
        return newProxy(typeName,proxyInvoker(handler));
    }
    
    @IndirectCallers
    public static <T> T newGenericProxy(Class<T> type, BiFunction<String,Object[],Object> handler) {
        return newProxy(type,proxyInvoker(handler));
    }
    
    public static <T> T newGenericProxy(ClassLoader loader, String typeName, Function<Method,Boolean> methodMatcher,
            Function<Object[],Object> argsHandler) {
        return newProxy(loader,typeName,proxyInvoker(methodMatcher,argsHandler));
    }
    
    @IndirectCallers
    public static <T> T newGenericProxy(String typeName, Function<Method,Boolean> methodMatcher,
            Function<Object[],Object> argsHandler) {
        return newProxy(typeName,proxyInvoker(methodMatcher,argsHandler));
    }
    
    public static <T> T newGenericProxy(Class<T> type, Function<Method,Boolean> methodMatcher,
            Function<Object[],Object> argsHandler) {
        return newProxy(type,proxyInvoker(methodMatcher,argsHandler));
    }
    
    @IndirectCallers
    public static <T> T newProxy(String typeName, InvocationHandler handler) {
        return newProxy(Hacks.contextClassLoader(),typeName,handler);
    }
    
    public static <T> T newProxy(Class<T> type, InvocationHandler handler) {
        return newProxy(type.getClassLoader(),handler,type);
    }
    
    public static <T> T newProxy(ClassLoader loader, String typeName, InvocationHandler handler) {
        return newProxy(loader,handler,new Object[]{typeName});
    }
    
    @IndirectCallers
    public static <T> T newProxy(ClassLoader loader, Class<T> type, InvocationHandler handler) {
        return newProxy(loader,handler,new Class<?>[]{type});
    }
    
    public static <T> T newProxy(ClassLoader loader, InvocationHandler handler, Object ... typesOrNames) {
        if(Objects.isNull(typesOrNames) || typesOrNames.length==0) return proxyFail(2);
        Class<?>[] types = new Class<?>[typesOrNames.length];
        for(int i=0;i<types.length;i++) {
            Object typeOrName = typesOrNames[i];
            if(Objects.isNull(typeOrName)) return proxyFail(1,(Object)null);
            if(typeOrName instanceof Class<?>) {
                types[i] = (Class<?>)typeOrName;
                continue;
            }
            String typeName = String.valueOf(typeOrName);
            Class<?> type = Hacks.findClass(typeName);
            if(Objects.isNull(type)) return proxyFail(1,typeName);
            types[i] = type;
        }
        return newProxy(loader,handler,types);
    }
    
    public static <T> T newProxy(ClassLoader loader, InvocationHandler handler, Class<?> ... types) {
        return Objects.nonNull(types) && types.length>0 ?
                GenericUtils.cast(Proxy.newProxyInstance(loader,types,handler)) : proxyFail(2);
    }
    
    @IndirectCallers
    public static String packageName(@Nullable Class<?> clazz) {
        return Objects.nonNull(clazz) ? clazz.getPackage().getName() : "";
    }
    
    private static <T> T proxyFail(int failType, Object ... args) {
        switch(failType) {
            case 1: LOGGER.error("Cannot create proxy! Class not found: {}",args);
            case 2: LOGGER.error("Cannot create proxy with null or empty types array");
        }
        return null;
    }
    
    public static InvocationHandler proxyInvoker(Function<Method,Boolean> matcher, Function<Object[],Object> handler) {
        return proxyInvoker(true,matcher,handler);
    }
    
    public static InvocationHandler proxyInvoker(boolean handleDefault, Function<Method,Boolean> matcher,
            Function<Object[],Object> handler) {
        return proxyInvokerWrappedHandle(handleDefault,(method,args) ->
                matcher.apply(method) ? handler.apply(args) : null);
    }
    
    public static InvocationHandler proxyInvoker(BiFunction<String,Object[],Object> handler) {
        return proxyInvoker(true,handler);
    }
    
    public static InvocationHandler proxyInvoker(boolean handleDefault, BiFunction<String,Object[],Object> handler) {
        return proxyInvokerWrappedHandle(handleDefault,
                (method,args) -> handler.apply(method.getName(),args));
    }
    
    @IndirectCallers
    public static InvocationHandler proxyInvokerWrappedHandle(final BiFunction<Method,Object[],Object> handler) {
        return proxyInvokerWrappedHandle(true,handler);
    }
    
    public static InvocationHandler proxyInvokerWrappedHandle(boolean handleDefault,
            final BiFunction<Method,Object[],Object> handler) {
        return handleDefault ? (instance,method,args) -> {
            switch(method.getName()) {
                case "equals": return args.length>0 && instance==args[0];
                case "hashCode": return 0;
                case "toString": return instance.toString();
                default: return handler.apply(method,args);
            }
        } : (instance,method,args) -> handler.apply(method,args);
    }
    
    /**
     * Defines and resolves a class from byteCode
     */
    @SuppressWarnings("UnusedReturnValue")
    @SneakyThrows
    public static Class<?> resolveClass(ClassLoader loader, @Nullable Class<?> clazz) {
        if(Objects.isNull(clazz)) {
            LOGGER.fatal("Cannot resolve null defined class! (ClassLoader = {})",loader);
            return null;
        }
        return Hacks.checkBurningWaveInitAndCall("loadOrDefineClass",clazz,loader);
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
                    for(String classToLoad : classesToLoad) Hacks.findClass(classToLoad,syncTo);
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