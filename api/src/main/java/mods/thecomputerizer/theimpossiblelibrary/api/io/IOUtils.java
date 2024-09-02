package mods.thecomputerizer.theimpossiblelibrary.api.io;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.WrapperableMappable;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Patterns;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Read/write util methods with some addition specific string stuff
 */
@SuppressWarnings("unchecked")
public class IOUtils {

    /**
     * case-insensitive
     */
    private static final WrapperableMappable<Class<?>,String> CLASS_ALIASES = new WrapperableMappable<>(new HashMap<>(),false);

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
            else if(arg instanceof Pair<?,?>) {
                Pair<?,?> argPair = (Pair<?,?>)arg;
                Misc.lowerCaseAddCollection(aliasSet,argPair.getLeft().toString());
                Misc.lowerCaseAddCollection(aliasSet,argPair.getRight().toString());
            } else if(arg instanceof String[]) {
                for(String argArrElement : (String[]) arg)
                    Misc.lowerCaseAddCollection(aliasSet,argArrElement);
            } else Misc.lowerCaseAddCollection(aliasSet,arg.toString());
        }
        CLASS_ALIASES.putFast(clazz,aliasSet);
        TILRef.logDebug("Added class aliases {} for class {}",TextHelper.compileCollection(aliasSet),clazz);
    }

    /**
     * case-insensitive
     */
    public static Class<?> getClassFromAlias(String alias) {
        return CLASS_ALIASES.getKeyOrDefault(w -> Patterns.matchesAny(alias,w),Object.class);
    }

    private static Pair<String,String> getClassNames(Class<?> clazz) {
        return new ImmutablePair<>(clazz.getName(),clazz.getSimpleName());
    }

    /**
     * Removes blank values from string collections
     */
    @IndirectCallers
    public static void lintCollections(Collection<String> ... collections) {
        for(Collection<String> c : collections) c.removeIf(StringUtils::isBlank);
    }
    
    @IndirectCallers
    public static void loadDefaults() {
        loadDefaultClassAliases();
    }

    private static void loadDefaultClassAliases() {
        TILRef.logInfo("Loading default class aliases");
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
        E[] mapped = (E[])Array.newInstance(instances[0].getClass(),indices.length);
        for(int i=0; i<indices.length; i++) mapped[i] = instances[indices[i]];
        return mapped;
    }

    /**
     * 2D version of the mapper.
     */
    @IndirectCallers
    public static <E> E[][] mapGrid(E[] instances, int[][] indices) {
        assert instances.length>0 && indices.length>0 && indices[0].length>0;
        E[][] mapped = (E[][])Array.newInstance(instances[0].getClass(),indices.length,indices[0].length);
        for(int i=0; i<indices.length; i++)
            for(int j=0; j<indices[j].length; j++)
                mapped[i][j] = instances[indices[i][j]];
        return mapped;
    }


    /**
     * 3D version of the mapper
     */
    @IndirectCallers
    public static <E> E[][][] mapBox(E[] instances, int[][][] indices) {
        assert instances.length>0 && indices.length>0 && indices[0].length>0 && indices[0][0].length>0;
        E[][][] mapped = (E[][][])Array.newInstance(instances[0].getClass(),indices.length,indices[0].length,indices[0][0].length);
        for(int i=0; i<indices.length; i++)
            for(int j=0; j<indices[j].length; j++)
                for(int k=0; k<indices[i][j].length; k++)
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
}
