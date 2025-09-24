package mods.thecomputerizer.theimpossiblelibrary.api.io;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.WrapperableMappable;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Patterns;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.burningwave.core.assembler.StaticComponentContainer.Streams;

/**
 * Read/write util methods with some addition-specific string stuff
 */
public class IOUtils {

    /**
     * case-insensitive
     */
    private static final WrapperableMappable<Class<?>,String> CLASS_ALIASES = new WrapperableMappable<>(new HashMap<>(),false);
    private static final Logger LOGGER = TILRef.createLogger("TIL I/O");

    public static void addBasicClassAliases(Class<?> ... classes) {
        for(Class<?> clazz : classes) addClassAliases(clazz);
    }

    public static void addClassAliases(Class<?> clazz, Object ... aliasArgs) {
        if(CLASS_ALIASES.containsKey(clazz)) return;
        List<Object> aliasArgList = new ArrayList<>(Arrays.asList(aliasArgs));
        aliasArgList.add(getClassNames(clazz));
        Set<String> aliasSet = new HashSet<>();
        for(Object arg : aliasArgList) {
            if(arg instanceof String) Misc.lowerCaseAddCollection(aliasSet,(String)arg);
            else if(arg instanceof Entry<?,?>) {
                Entry<?,?> argPair = (Entry<?,?>)arg;
                Misc.lowerCaseAddCollection(aliasSet,argPair.getKey().toString());
                Misc.lowerCaseAddCollection(aliasSet,argPair.getValue().toString());
            } else if(arg instanceof String[]) {
                for(String argArrElement : (String[]) arg)
                    Misc.lowerCaseAddCollection(aliasSet,argArrElement);
            } else Misc.lowerCaseAddCollection(aliasSet,arg.toString());
        }
        CLASS_ALIASES.putFast(clazz,aliasSet);
        LOGGER.debug("Added class aliases {} for class {}",TextHelper.compileCollection(aliasSet),clazz);
    }

    /**
     * case-insensitive
     */
    public static Class<?> getClassFromAlias(String alias) {
        return CLASS_ALIASES.getKeyOrDefault(w -> Patterns.matchesAny(alias,w),Object.class);
    }

    private static Entry<String,String> getClassNames(Class<?> clazz) {
        return new SimpleImmutableEntry<>(clazz.getName(),clazz.getSimpleName());
    }

    /**
     * Removes blank values from string collections
     */
    @SafeVarargs @IndirectCallers
    public static void lintCollections(Collection<String> ... collections) {
        for(Collection<String> c : collections) c.removeIf(TextHelper::isBlank);
    }
    
    @IndirectCallers
    public static void loadDefaults() {
        loadDefaultClassAliases();
    }

    private static void loadDefaultClassAliases() {
        LOGGER.info("Loading default class aliases");
        addBasicClassAliases(Byte.class,Character.class,Double.class,Float.class,Long.class,Short.class,String.class,
                Void.class);
        addClassAliases(Boolean.class,"bool");
        addClassAliases(Character.class,"char");
        addClassAliases(Integer.class,"int");
    }

    /**
     * It's assumed that the instance array is not empty, the index array is not empty, and the generic type is the
     * correct superclass level
     */
    @IndirectCallers
    public static <E> E[] mapArray(E[] instances, int[] indices) {
        assert instances.length>0 && indices.length>0;
        E[] mapped = GenericUtils.cast(Array.newInstance(instances[0].getClass(),indices.length));
        if(Objects.nonNull(mapped))
            for(int i=0;i<indices.length;i++) mapped[i] = instances[indices[i]];
        return mapped;
    }

    /**
     * 2D version of the mapper.
     */
    @IndirectCallers
    public static <E> E[][] mapGrid(E[] instances, int[][] indices) {
        assert instances.length>0 && indices.length>0 && indices[0].length>0;
        E[][] mapped = GenericUtils.cast(Array.newInstance(instances[0].getClass(),indices.length,indices[0].length));
        if(Objects.nonNull(mapped))
            for(int i=0;i<indices.length;i++)
                for(int j=0;j<indices[j].length;j++)
                    mapped[i][j] = instances[indices[i][j]];
        return mapped;
    }


    /**
     * 3D version of the mapper
     */
    @IndirectCallers
    public static <E> E[][][] mapBox(E[] instances, int[][][] indices) {
        assert instances.length>0 && indices.length>0 && indices[0].length>0 && indices[0][0].length>0;
        E[][][] mapped = GenericUtils.cast(Array.newInstance(instances[0].getClass(),indices.length,indices[0].length,indices[0][0].length));
        if(Objects.nonNull(mapped))
            for(int i=0;i<indices.length;i++)
                for(int j=0;j<indices[j].length;j++)
                    for(int k=0;k<indices[i][j].length;k++)
                        mapped[i][j][k] = instances[indices[i][j][k]];
        return mapped;
    }
    
    public static String streamToString(InputStream stream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for(int length;(length = stream.read(buffer))!=-1;) output.write(buffer,0,length);
        return output.toString(UTF_8.name());
    }
    
    @IndirectCallers
    public static InputStream stringToStream(String str) {
        return new ByteArrayInputStream(str.getBytes(UTF_8));
    }
    
    public static ByteBuffer toBuffer(URL url) {
        try {
            Streams.toByteBuffer(url.openStream());
        } catch(IOException ex) {
            LOGGER.error("Failed to open buffer for URL {}",url,ex);
        }
        return null;
    }
}
