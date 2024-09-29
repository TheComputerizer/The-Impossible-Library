package mods.thecomputerizer.theimpossiblelibrary.api.core;

import org.objectweb.asm.tree.ClassNode;

import java.util.Collections;
import java.util.List;

/**
 * Core entrypoint API for early loading such as coremods
 */
public abstract class CoreEntryPoint { //TODO See if any transformer methods can be mapped
    
    public List<String> classTargets() {
        return Collections.emptyList();
    }
    
    public ClassNode editClass(ClassNode classNode) {
        return classNode;
    }
    
    public abstract String getCoreID();
    public abstract String getCoreName();
    
    protected boolean isTarget(ClassNode node) {
        for(String target : classTargets())
            if(node.name.equals(target.replace('.','/'))) return true;
        return false;
    }
    
    @Override public String toString() {
        return "CoreEntryPoint["+getCoreName()+"]";
    }
}