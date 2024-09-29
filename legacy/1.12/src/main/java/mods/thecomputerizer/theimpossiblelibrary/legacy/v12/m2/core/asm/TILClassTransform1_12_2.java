package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.TILLoadingPlugin1_12_2;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.COMPUTE_FRAMES;

@IndirectCallers
public class TILClassTransform1_12_2 implements IClassTransformer {
    
    @Override public byte[] transform(String name, String transformedName, byte[] byteCode) {
        for(CoreEntryPoint core : TILLoadingPlugin1_12_2.getTransformers(name)) byteCode = transform(core,byteCode);
        return byteCode;
    }
    
    byte[] transform(CoreEntryPoint core, byte[] byteCode) {
        ClassNode node = new ClassNode();
        ClassReader reader = new ClassReader(byteCode);
        reader.accept(node,0);
        core.editClass(node);
        ClassWriter writer = new ClassWriter(COMPUTE_FRAMES);
        node.accept(writer);
        return writer.toByteArray();
    }
}
