package mods.thecomputerizer.theimpossiblelibrary.forge.v21.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.asm.ModWriterForge;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;

public class ModWriterForge1_21 extends ModWriterForge {
    
    public ModWriterForge1_21(CoreAPI core, MultiVersionModInfo info) {
        super(core,info);
    }
    
    @Override protected MethodVisitor getConstructor(ClassVisitor visitor) {
        return ASMHelper.getConstructor(visitor,PUBLIC,new Type[]{JAVA_LOADING_CONTEXT});
    }
    
    /**
     * Sets the extraData field of CommonEntryPoint to the FMLJavaModLoadingContext passed into the constructor of the
     * written class so that it is internally accessible
     */
    @Override protected final void writeConstructor(ClassVisitor visitor) {
        final String extraDataDesc = TypeHelper.voidMethodDesc(OBJECT_TYPE);
        writeConstructor(visitor,constructor -> {
            constructor.visitVarInsn(ALOAD,0);
            constructor.visitFieldInsn(GETFIELD,this.modTypeInternal,"entryPoint",this.entryPointDesc);
            constructor.visitVarInsn(ALOAD,1); //Load FMLJavaModLoadingContext parameter
            constructor.visitMethodInsn(INVOKEVIRTUAL,this.entryPointInternal,"setExtraData",extraDataDesc,false);
        });
    }
}