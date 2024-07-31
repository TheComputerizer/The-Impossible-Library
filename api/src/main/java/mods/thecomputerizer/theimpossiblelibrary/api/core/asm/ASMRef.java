package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.File;

import static org.objectweb.asm.Type.VOID_TYPE;

@SuppressWarnings("PointlessBitwiseExpression")
public class ASMRef {

    public static final int ABSTRACT = Opcodes.ACC_ABSTRACT;
    public static final int ALOAD = Opcodes.ALOAD;
    public static final int ASM4 = Opcodes.ASM4;
    public static final int ASM5 = Opcodes.ASM5;    // Assume ASM5 is the earlist version used
    public static final int ASM6 = 6<<16|0<<8;
    public static final int ASM7 = 7<<16|0<<8;
    public static final int ASM8 = 8<<16|0<<8;
    public static final int ASM9 = 9<<16|0<<8;
    public static final int BRIDGE = Opcodes.ACC_BRIDGE;
    public static final int CHECKCAST = Opcodes.CHECKCAST;
    public static final Type CLASS_TYPE = Type.getType(Class.class);
    public static final int COMPUTE_FRAMES = ClassWriter.COMPUTE_FRAMES;
    public static final int COMPUTE_MAXS = ClassWriter.COMPUTE_MAXS;
    public static final int DUP = Opcodes.DUP;
    public static final Type FILE_TYPE = Type.getType(File.class);
    public static final int FINAL = Opcodes.ACC_FINAL;
    public static final int GETFIELD = Opcodes.GETFIELD;
    public static final int GETSTATIC = Opcodes.GETSTATIC;
    public static final int INTERFACE = Opcodes.ACC_INTERFACE;
    public static final int INVOKESPECIAL = Opcodes.INVOKESPECIAL;
    public static final int INVOKESTATIC = Opcodes.INVOKESTATIC;
    public static final int INVOKEVIRTUAL = Opcodes.INVOKEVIRTUAL;
    public static final int JAVA8 = Opcodes.V1_8;
    public static final int JAVA17 = 0<<16|61;      // ASM5 does not have Java 17 or 21 constants
    public static final int JAVA21 = 0<<16|65;
    public static final int LDC = Opcodes.LDC;
    public static final int NATIVE = Opcodes.ACC_NATIVE;
    public static final int NEW = Opcodes.NEW;
    public static final Type OBJECT_TYPE = Type.getType(Object.class);
    public static final int PRIVATE = Opcodes.ACC_PRIVATE;
    public static final int PRIVATE_ABSTRACT = Opcodes.ACC_PRIVATE+Opcodes.ACC_ABSTRACT;
    public static final int PRIVATE_ABSTRACT_INTERFACE = Opcodes.ACC_PRIVATE+Opcodes.ACC_ABSTRACT+Opcodes.ACC_INTERFACE;
    public static final int PRIVATE_FINAL = Opcodes.ACC_PRIVATE+Opcodes.ACC_FINAL;
    public static final int PRIVATE_STATIC = Opcodes.ACC_PRIVATE+Opcodes.ACC_STATIC;
    public static final int PRIVATE_STATIC_FINAL = Opcodes.ACC_PRIVATE+Opcodes.ACC_STATIC+Opcodes.ACC_FINAL;
    public static final int PROTECTED = Opcodes.ACC_PROTECTED;
    public static final int PROTECTED_ABSTRACT = Opcodes.ACC_PROTECTED+Opcodes.ACC_ABSTRACT;
    public static final int PROTECTED_ABSTRACT_INTERFACE = Opcodes.ACC_PROTECTED+Opcodes.ACC_ABSTRACT+Opcodes.ACC_INTERFACE;
    public static final int PROTECTED_FINAL = Opcodes.ACC_PROTECTED+Opcodes.ACC_FINAL;
    public static final int PROTECTED_STATIC = Opcodes.ACC_PROTECTED+Opcodes.ACC_STATIC;
    public static final int PROTECTED_STATIC_FINAL = Opcodes.ACC_PROTECTED+Opcodes.ACC_STATIC+Opcodes.ACC_FINAL;
    public static final int PUBLIC = Opcodes.ACC_PUBLIC;
    public static final int PUBLIC_ABSTRACT = Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT;
    public static final int PUBLIC_ABSTRACT_INTERFACE = Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT+Opcodes.ACC_INTERFACE;
    public static final int PUBLIC_FINAL = Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL;
    public static final int PUBLIC_STATIC = Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC;
    public static final int PUBLIC_STATIC_FINAL = Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC+Opcodes.ACC_FINAL;
    public static final int PUTFIELD = Opcodes.PUTFIELD;
    public static final int PUTSTATIC = Opcodes.PUTSTATIC;
    public static final int RETURN = Opcodes.RETURN;
    public static final int STATIC = Opcodes.ACC_STATIC;
    public static final int STRICT = Opcodes.ACC_STRICT;
    public static final Type STRING_TYPE = Type.getType(String.class);
    public static final int SYNCHRONIZED = Opcodes.ACC_SYNCHRONIZED;
    public static final int SYNTHETIC = Opcodes.ACC_SYNTHETIC;
    public static final int VARARGS = Opcodes.ACC_VARARGS;
    public static final Type VOID_EMPTY_METHOD = Type.getMethodType(VOID_TYPE);
    public static final int VOLATILE = Opcodes.ACC_VOLATILE;
}