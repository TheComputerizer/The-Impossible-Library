package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;

/**
 * Core entrypoint API for early loading such as coremods
 */
@SuppressWarnings({"SameParameterValue","UnusedReturnValue"})
public abstract class CoreEntryPoint {
    
    private InsnList list;
    private LabelNode label;
    
    protected CoreEntryPoint beginList(InsnList list) {
        this.list = list;
        return this;
    }
    
    public List<String> classTargets() {
        return Collections.emptyList();
    }
    
    public ClassNode editClass(ClassNode classNode) {
        return classNode;
    }
    
    public InsnList endList() {
        InsnList ret = this.list;
        this.list = null;
        return ret;
    }
    
    @IndirectCallers public abstract String getCoreID();
    public abstract String getCoreName();
    
    protected final String getClassName(ClassNode node) {
        return getClassName(node,false);
    }
    
    @SuppressWarnings("SameParameterValue")
    protected final String getClassName(ClassNode node, boolean asBinary) {
        return CoreAPI.getInstance().mapClassName(node.name,asBinary);
    }
    
    @IndirectCallers
    protected final String getFieldName(ClassNode classNode, FieldNode node) {
        return getFieldName(classNode,node,false);
    }
    
    protected final String getFieldName(ClassNode classNode, FieldNode node, boolean asBinary) {
        return CoreAPI.getInstance().mapFieldName(classNode.name,node.name,node.desc,asBinary);
    }
    
    protected final String getMethodName(ClassNode classNode, MethodNode node) {
        return getMethodName(classNode,node,false);
    }
    
    protected final String getMethodName(ClassNode classNode, MethodNode node, boolean asBinary) {
        return CoreAPI.getInstance().mapMethodName(classNode.name,node.name,node.desc,asBinary);
    }
    
    public CoreEntryPoint insBasic(int opcode) {
        if(Objects.isNull(this.list)) TILRef.logError("Tried to insert basic instruction before calling beginList");
        else this.list.add(new InsnNode(opcode));
        return this;
    }
    
    public CoreEntryPoint insField(int opcode, String owner, String name, String desc) {
        if(Objects.isNull(this.list)) TILRef.logError("Tried to insert field instruction before calling beginList");
        else this.list.add(new FieldInsnNode(opcode,owner,name,desc));
        return this;
    }
    
    public CoreEntryPoint insIf(int opcode, Label label) {
        if(Objects.isNull(this.list)) TILRef.logError("Tried to insert if instruction before calling beginList");
        else if(Objects.isNull(label)) TILRef.logError("Tried to insert with null label");
        else {
            this.label = new LabelNode(label);
            this.list.add(new JumpInsnNode(opcode,this.label));
        }
        return this;
    }
    
    public CoreEntryPoint insInvokeInterface(String owner, String name) {
        return insInvokeInterface(owner,name,EMPTY_METHOD_DESC);
    }
    
    public CoreEntryPoint insInvokeInterface(String owner, String name, String desc) {
        return insMethod(INVOKEINTERFACE,owner,name,desc,true);
    }
    
    public CoreEntryPoint insInvokeSpecial(String owner, String name) {
        return insInvokeSpecial(owner,name,EMPTY_METHOD_DESC);
    }
    
    public CoreEntryPoint insInvokeSpecial(String owner, String name, String desc) {
        return insMethod(INVOKESPECIAL,owner,name,desc,false);
    }
    
    @IndirectCallers
    public CoreEntryPoint insInvokeStatic(String owner, String name) {
        return insInvokeStatic(owner,name,EMPTY_METHOD_DESC);
    }
    
    public CoreEntryPoint insInvokeStatic(String owner, String name, String desc) {
        return insMethod(INVOKESTATIC,owner,name,desc,false);
    }
    
    @IndirectCallers
    public CoreEntryPoint insInvokeVirtual(String owner, String name) {
        return insInvokeVirtual(owner,name,EMPTY_METHOD_DESC);
    }
    
    public CoreEntryPoint insInvokeVirtual(String owner, String name, String desc) {
        return insMethod(INVOKEVIRTUAL,owner,name,desc,false);
    }
    
    public CoreEntryPoint insLabel() {
        if(Objects.isNull(this.list)) TILRef.logError("Tried to insert label instruction before calling beginList");
        else if(Objects.isNull(this.label)) TILRef.logError("Tried to insert uninitialized label instruction");
        else this.list.add(this.label);
        this.label = null;
        return this;
    }
    
    @IndirectCallers
    public CoreEntryPoint insLDC(Object object) {
        if(Objects.isNull(this.list)) TILRef.logError("Tried to insert constant before calling beginList");
        else this.list.add(new LdcInsnNode(object));
        return this;
    }
    
    private CoreEntryPoint insMethod(int opcode, String owner, String name, String desc, boolean isInterface) {
        if(Objects.isNull(this.list)) TILRef.logError("Tried to insert method instruction before calling beginList");
        else this.list.add(new MethodInsnNode(opcode,owner,name,desc,isInterface));
        return this;
    }
    
    public CoreEntryPoint insThis() {
        return insVar(ALOAD,0);
    }
    
    public CoreEntryPoint insType(int opcode, String owner) {
        if(Objects.isNull(this.list)) TILRef.logError("Tried to insert type instruction before calling beginList");
        else this.list.add(new TypeInsnNode(opcode,owner));
        return this;
    }
    
    public CoreEntryPoint insVar(int opcode, int index) {
        if(Objects.isNull(this.list)) TILRef.logError("Tried to insert var instruction before calling beginList");
        else this.list.add(new VarInsnNode(opcode,index));
        return this;
    }
    
    protected boolean isTarget(ClassNode node) {
        for(String target : classTargets())
            if(getClassName(node).equals(toInternal(target))) return true;
        return false;
    }
    
    @IndirectCallers
    protected String toBinary(String name) {
        return name.replace('/','.');
    }
    
    protected String toDesc(String name) {
        return "L"+toInternal(name)+";";
    }
    
    protected String toInternal(String name) {
        return name.replace('.','/');
    }
    
    protected String toSignature(String outer, String inner) {
        return toDesc(outer+"<"+toDesc(inner)+">");
    }
    
    @Override public String toString() {
        return "CoreEntryPoint["+getCoreName()+"]";
    }
    
    @IndirectCallers
    public final @Nullable byte[] transform(byte[] byteCode) {
        return transform(byteCode,0);
    }
    
    public final @Nullable byte[] transform(byte[] byteCode, int writerFlags) {
        ClassNode node = ASMHelper.toClassNode(byteCode);
        editClass(node);
        return ASMHelper.toBytes(node,writerFlags);
    }
}