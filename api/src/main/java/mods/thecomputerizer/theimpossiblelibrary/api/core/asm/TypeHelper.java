package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import org.objectweb.asm.Type;

@SuppressWarnings("unused") public class TypeHelper {
    
    public static Type fml(String path) {
        return forge("fml/"+path);
    }
    
    public static Type forge(String path) {
        return get("net/minecraftforge/"+path);
    }
    
    public static Type fromBinary(String binaryClasspath) {
        return get(binaryClasspath.replace('.','/'));
    }
    
    public static Type get(String className) {
        return Type.getType("L"+className+";");
    }
    
    public static Type inner(Type type, String name) {
        return fromBinary(type.getClassName()+"$"+name);
    }
    
    public static Type lang(String path) {
        return get("java/lang/"+path);
    }
    
    public static Type method(Class<?> returnClass, Class<?> ... args) {
        return method(Type.getType(returnClass),args);
    }
    
    public static Type method(Class<?> returnClass, Type ... args) {
        return method(Type.getType(returnClass),args);
    }
    
    public static Type method(Type returnType, Class<?> ... args) {
        Type[] argTypes = new Type[args.length];
        for(int i=0;i<argTypes.length;i++) argTypes[i] = Type.getType(args[i]);
        return method(returnType,argTypes);
    }
    
    public static Type method(Type returnType, Type ... args) {
        return Type.getMethodType(returnType,args);
    }
    
    public static Type minecraft(String path) {
        return get("net/minecraft/"+path);
    }
}