package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.TILLoadingPlugin1_12_2;
import net.minecraft.launchwrapper.IClassTransformer;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.COMPUTE_FRAMES;

@IndirectCallers
public class TILClassTransform1_12_2 implements IClassTransformer {
    
    @Override public byte[] transform(String name, String transformedName, byte[] byteCode) {
        for(CoreEntryPoint core : TILLoadingPlugin1_12_2.getTransformers(transformedName)) {
            TILRef.logInfo("Transforming class {}({})",name,transformedName);
            byteCode = core.transform(byteCode,COMPUTE_FRAMES);
        }
        return byteCode;
    }
}
