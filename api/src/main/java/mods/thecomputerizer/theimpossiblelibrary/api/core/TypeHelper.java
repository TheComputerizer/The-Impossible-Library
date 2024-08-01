package mods.thecomputerizer.theimpossiblelibrary.api.core;

import org.objectweb.asm.Type;

public class TypeHelper {
    
    public static Type getMethodType(Class<?> returnClass, Class<?> ... args) {
        Type[] argTypes = new Type[args.length];
        for(int i=0;i<argTypes.length;i++) argTypes[i] = Type.getType(args[i]);
        return Type.getMethodType(Type.getType(returnClass),argTypes);
    }
}