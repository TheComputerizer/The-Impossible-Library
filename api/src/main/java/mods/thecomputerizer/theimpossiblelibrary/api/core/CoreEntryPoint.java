package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Collections;
import java.util.List;

/**
 * Core entrypoint API for early loading such as coremods
 */
public abstract class CoreEntryPoint {
    
    public List<String> classTargets() {
        return Collections.emptyList();
    }
    
    public ClassNode editClass(ClassNode classNode) {
        return classNode;
    }
    
    @IndirectCallers
    public abstract String getCoreID();
    public abstract String getCoreName();
    
    protected final String getClassName(ClassNode node) {
        return CoreAPI.getInstance().mapClassName(node.name).replace('.','/');
    }
    
    @IndirectCallers
    protected final String getFieldName(ClassNode classNode, FieldNode node) {
        return CoreAPI.getInstance().mapFieldName(classNode.name,node.name,node.desc);
    }
    
    protected final String getMethodName(ClassNode classNode, MethodNode node) {
        return CoreAPI.getInstance().mapMethodName(classNode.name,node.name,node.desc);
    }
    
    protected boolean isTarget(ClassNode node) {
        for(String target : classTargets())
            if(getClassName(node).equals(target.replace('.','/'))) return true;
        return false;
    }
    
    @Override public String toString() {
        return "CoreEntryPoint["+getCoreName()+"]";
    }
}