package mods.thecomputerizer.theimpossiblelibrary.forge.v21.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.asm.ModWriterForge;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.List;
import java.util.Map.Entry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.VOID_TYPE;

public class ModWriterForge1_21 extends ModWriterForge {
    
    public ModWriterForge1_21(CoreAPI core, MultiVersionModInfo info) {
        super(core,info,JAVA21);
    }
    
    @Override protected MethodVisitor getConstructor(ClassVisitor visitor) {
        return ASMHelper.getConstructor(visitor,PUBLIC,new Type[]{getOptionalContructorType()});
    }
    
    @Override protected Type getEventMethod(String className) {
        if(className.contains("FMLServer")) {
            className = className.replace("FML","");
            return TypeHelper.method(VOID_TYPE,TypeHelper.forge("event/server/"+className));
        }
        return super.getEventMethod(className);
    }
    
    protected Type getOptionalContructorType() {
        return JAVA_LOADING_CONTEXT;
    }
    
    @Override protected void writeMod(ClassWriter writer, List<Entry<String,byte[]>> classBytes) {
        super.writeMod(writer,classBytes);
        writeInnerClass(addInnerEventSubscriber(writer,this.info.getModID(),true,true,
                false,"LoaderClient","clientSetup"),classBytes);
        writeInnerClass(addInnerEventSubscriber(writer,this.info.getModID(),true,true,true,
                                "LoaderCommon","commonSetup","interModEnqueue",
                                "interModProcess","loadComplete"),classBytes);
        writeInnerClass(addInnerEventSubscriber(writer,this.info.getModID(),true,false,true,
                                "LoaderServer","dedicatedServerSetup"),classBytes);
        writeInnerClass(addInnerEventSubscriber(writer,this.info.getModID(),false,true,true,
                                "ServerLifecycle","serverAboutToStart","serverStarting",
                                "serverStarted","serverStopping","serverStopped"),classBytes);
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