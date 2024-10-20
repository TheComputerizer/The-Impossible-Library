package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Type.VOID_TYPE;

public class TypeHelper {
    
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
    
    public static Type method(Class<?> returnClass) {
        return method(returnClass,new Type[]{});
    }
    
    public static Type method(Type returnType) {
        return method(returnType,new Type[]{});
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
    
    public static String methodDesc(Class<?> returnClass) {
        return method(returnClass).getDescriptor();
    }
    
    public static String methodDesc(Type returnType) {
        return method(returnType).getDescriptor();
    }
    
    @IndirectCallers
    public static String methodDesc(Class<?> returnClass, Class<?> ... args) {
        return method(returnClass,args).getDescriptor();
    }
    
    @IndirectCallers
    public static String methodDesc(Class<?> returnClass, Type ... args) {
        return method(returnClass,args).getDescriptor();
    }
    
    public static String methodDesc(Type returnType, Class<?> ... args) {
        return method(returnType,args).getDescriptor();
    }
    
    @IndirectCallers
    public static String methodDesc(Type returnType, Type ... args) {
        return method(returnType,args).getDescriptor();
    }
    
    @IndirectCallers
    public static Type voidMethod(Class<?> ... args) {
        return method(VOID_TYPE,args);
    }
    
    @IndirectCallers
    public static Type voidMethod(Type ... args) {
        return method(VOID_TYPE,args);
    }
    
    @IndirectCallers
    public static String voidMethodDesc(Class<?> ... args) {
        return method(VOID_TYPE,args).getDescriptor();
    }
    
    public static String voidMethodDesc(Type ... args) {
        return method(VOID_TYPE,args).getDescriptor();
    }
    
    public static Type minecraft(String path) {
        return get("net/minecraft/"+path);
    }
}