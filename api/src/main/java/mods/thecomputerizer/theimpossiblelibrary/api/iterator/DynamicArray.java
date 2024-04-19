package mods.thecomputerizer.theimpossiblelibrary.api.iterator;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.io.IOUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Patterns;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;


@Getter
public class DynamicArray {

    private final int bracketCount;
    private final String className;
    private final Class<?> typeClass;
    private final boolean isOptional;

    public DynamicArray(String unparsed) {
        this(StringUtils.countMatches(unparsed,'['),unparsed.replaceAll(Patterns.ARRAY_DEF.pattern(),""));
    }

    public DynamicArray(int bracketCount, String type) {
        this.bracketCount = bracketCount;
        this.isOptional = type.startsWith("@");
        if(this.isOptional) type = type.substring(1);
        if(bracketCount>0 && type.startsWith("L")) {
            type = type.substring(1);
            if(type.endsWith(";")) type = type.substring(0,type.length()-1);
        }
        this.className = IOUtils.getClassFromAlias(type).getName();
        this.typeClass = makeTypeClass();
    }

    public DynamicArray(int bracketCount, Class<?> clazz) {
        String name = clazz.getName();
        this.bracketCount = bracketCount<0 ? StringUtils.countMatches(name,'[') : bracketCount;
        name = name.replaceAll(Patterns.ARRAY_DEF.pattern(),"");
        this.isOptional = false;
        if(name.startsWith("L")) {
            name = name.substring(1);
            if(name.endsWith(";")) name = name.substring(0,name.length()-1);
        }
        this.className = name;
        this.typeClass = makeTypeClass();
    }

    public Class<?> getBaseClass() {
        Class<?> clazz;
        try {
            clazz = Class.forName(this.className);
        } catch(ClassNotFoundException ex) {
            TILRef.logError("Failed to get base class of dynamic array",ex);
            clazz = Object.class;
        }
        return clazz;
    }

    private Class<?> makeTypeClass() {
        Class<?> clazz = getBaseClass();
        if(this.bracketCount==0) return clazz;
        int[] dimensions = new int[this.bracketCount];
        Arrays.fill(dimensions,1);
        Object ref = ArrayHelper.createMulti(clazz,dimensions);
        return Objects.nonNull(ref) ? ref.getClass() : Object.class;
    }

    @Override
    public boolean equals(Object o) {
        if(getClass()==o.getClass()) {
            DynamicArray d = (DynamicArray)o;
            return this.typeClass==d.typeClass && this.bracketCount==d.bracketCount;
        }
        return false;
    }

    @Override
    public String toString() {
        return (this.isOptional ? "@" : "")+this.typeClass.getName();
    }
}
