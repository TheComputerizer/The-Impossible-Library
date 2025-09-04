package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.File;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Type.VOID_TYPE;

@SuppressWarnings({"PointlessBitwiseExpression","SpellCheckingInspection","unused"})
public class ASMRef {

    public static final int ABSTRACT = ACC_ABSTRACT;
    public static final int ALOAD = Opcodes.ALOAD;
    public static final int ASM4 = Opcodes.ASM4;
    public static final int ASM5 = Opcodes.ASM5;    // Assume ASM5 is the earliest version used
    public static final int ASM6 = 6<<16|0<<8;
    public static final int ASM7 = 7<<16|0<<8;
    public static final int ASM8 = 8<<16|0<<8;
    public static final int ASM9 = 9<<16|0<<8;
    public static final int ASTORE = Opcodes.ASTORE;
    public static final Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
    public static final int BRIDGE = ACC_BRIDGE;
    public static final Type BYTE_TYPE = Type.BYTE_TYPE;
    public static final int CHECKCAST = Opcodes.CHECKCAST;
    public static final Type CLASS_TYPE = Type.getType(Class.class);
    public static final String CLASS_TYPE_NAME = CLASS_TYPE.getInternalName();
    public static final int COMPUTE_FRAMES = ClassWriter.COMPUTE_FRAMES;
    public static final int COMPUTE_MAXS = ClassWriter.COMPUTE_MAXS;
    public static final Type DOUBLE_TYPE = Type.DOUBLE_TYPE;
    public static final int DUP = Opcodes.DUP;
    public static final Type EMPTY_BOOLEAN_METHOD = Type.getMethodType(BOOLEAN_TYPE);
    public static final String EMPTY_BOOLEAN_METHOD_DESC = EMPTY_BOOLEAN_METHOD.getDescriptor();
    public static final Type EMPTY_METHOD = Type.getMethodType(VOID_TYPE);
    public static final String EMPTY_METHOD_DESC = EMPTY_METHOD.getDescriptor();
    public static final Type FILE_TYPE = Type.getType(File.class);
    public static final String FILE_TYPE_NAME = FILE_TYPE.getInternalName(); //Internal name
    public static final int FINAL = ACC_FINAL;
    public static final Type FLOAT_TYPE = Type.FLOAT_TYPE;
    public static final int FRAME_APPEND = F_APPEND;
    public static final int FRAME_CHOP = F_CHOP;
    public static final int FRAME_FULL = F_FULL;
    public static final int FRAME_NEW = F_NEW;
    public static final int FRAME_SAME = F_SAME;
    public static final int FRAME_SAME1 = F_SAME1;
    public static final int GETFIELD = Opcodes.GETFIELD;
    public static final int GETSTATIC = Opcodes.GETSTATIC;
    public static final int GREATER_THAN = IFGT;
    public static final int GREATER_OR_EQUAL = IFGE;
    public static final int HANDLE_INVOKEINTERFACE = H_INVOKEINTERFACE;
    public static final int HANDLE_INVOKESPECIAL = H_INVOKESPECIAL;
    public static final int HANDLE_INVOKESTATIC = H_INVOKESTATIC;
    public static final int HANDLE_INVOKEVIRTUAL = H_INVOKEVIRTUAL;
    public static final int IF_EQUAL = IFEQ;
    public static final int IF_NOT_EQUAL = IFNE;
    public static final int IF_NOT_NULL = IFNONNULL;
    public static final int IF_NULL = IFNULL;
    public static final int ILOAD = Opcodes.ILOAD;
    public static final Type INT_TYPE = Type.INT_TYPE;
    public static final int INTERFACE = ACC_INTERFACE;
    public static final int INVOKEDYNAMIC = Opcodes.INVOKEDYNAMIC;
    public static final int INVOKEINTERFACE = Opcodes.INVOKEINTERFACE;
    public static final int INVOKESPECIAL = Opcodes.INVOKESPECIAL;
    public static final int INVOKESTATIC = Opcodes.INVOKESTATIC;
    public static final int INVOKEVIRTUAL = Opcodes.INVOKEVIRTUAL;
    public static final int JAVA8 = Opcodes.V1_8;
    public static final int JAVA17 = 0<<16|61;      // ASM5 does not have Java 17 or 21 constants
    public static final int JAVA21 = 0<<16|65;
    public static final int LDC = Opcodes.LDC;
    public static final Type LONG_TYPE = Type.LONG_TYPE;
    public static final int LESS_THAN = IFLT;
    public static final int LESS_OR_EQUAL = IFLE;
    public static final int NATIVE = ACC_NATIVE;
    public static final int NEW = Opcodes.NEW;
    public static final int NOT_EQUAL = IFNE;
    public static final int NOT_NULL = IFNONNULL;
    public static final Type OBJECT_TYPE = Type.getType(Object.class);
    public static final String OBJECT_TYPE_NAME = OBJECT_TYPE.getInternalName(); //Internal name
    public static final int PRIVATE = ACC_PRIVATE;
    public static final int PRIVATE_ABSTRACT = ACC_PRIVATE+ACC_ABSTRACT;
    public static final int PRIVATE_ABSTRACT_INTERFACE = ACC_PRIVATE+ACC_ABSTRACT+ACC_INTERFACE;
    public static final int PRIVATE_FINAL = ACC_PRIVATE+ACC_FINAL;
    public static final int PRIVATE_STATIC = ACC_PRIVATE+ACC_STATIC;
    public static final int PRIVATE_STATIC_FINAL = ACC_PRIVATE+ACC_STATIC+ACC_FINAL;
    public static final int PROTECTED = ACC_PROTECTED;
    public static final int PROTECTED_ABSTRACT = ACC_PROTECTED+ACC_ABSTRACT;
    public static final int PROTECTED_ABSTRACT_INTERFACE = ACC_PROTECTED+ACC_ABSTRACT+ACC_INTERFACE;
    public static final int PROTECTED_FINAL = ACC_PROTECTED+ACC_FINAL;
    public static final int PROTECTED_STATIC = ACC_PROTECTED+ACC_STATIC;
    public static final int PROTECTED_STATIC_FINAL = ACC_PROTECTED+ACC_STATIC+ACC_FINAL;
    public static final int PUBLIC = ACC_PUBLIC;
    public static final int PUBLIC_ABSTRACT = ACC_PUBLIC+ACC_ABSTRACT;
    public static final int PUBLIC_ABSTRACT_INTERFACE = ACC_PUBLIC+ACC_ABSTRACT+ACC_INTERFACE;
    public static final int PUBLIC_FINAL = ACC_PUBLIC+ACC_FINAL;
    public static final int PUBLIC_STATIC = ACC_PUBLIC+ACC_STATIC;
    public static final int PUBLIC_STATIC_FINAL = ACC_PUBLIC+ACC_STATIC+ACC_FINAL;
    public static final int PUTFIELD = Opcodes.PUTFIELD;
    public static final int PUTSTATIC = Opcodes.PUTSTATIC;
    public static final int RETURN = Opcodes.RETURN;
    public static final int RETURN_DOUBLE = DRETURN;
    public static final int RETURN_FLOAT = FRETURN;
    public static final int RETURN_INT_OR_BOOL = IRETURN; //Booleans are compiled into int values
    public static final int RETURN_LONG = LRETURN;
    public static final int RETURN_OBJ = ARETURN;
    public static final Type SHORT_TYPE = Type.SHORT_TYPE;
    public static final int STATIC = ACC_STATIC;
    public static final int STRICT = ACC_STRICT;
    public static final Type STRING_TYPE = Type.getType(String.class);
    public static final int SYNCHRONIZED = ACC_SYNCHRONIZED;
    public static final int SYNTHETIC = ACC_SYNTHETIC;
    public static final int VARARGS = ACC_VARARGS;
    public static final int VOLATILE = ACC_VOLATILE;
    public static final Type VOID_OBJECT_METHOD = TypeHelper.voidMethod(OBJECT_TYPE);
    public static final String VOID_OBJECT_METHOD_DESC = VOID_OBJECT_METHOD.getDescriptor();
}