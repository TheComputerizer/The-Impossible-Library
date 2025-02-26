package mods.thecomputerizer.theimpossiblelibrary.forge.v19.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.asm.ModWriterForge;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.JAVA17;
import static org.objectweb.asm.Type.VOID_TYPE;

public class ModWriterForge1_19 extends ModWriterForge {
    
    public ModWriterForge1_19(CoreAPI core, MultiVersionModInfo info) {
        super(core,info,JAVA17);
    }
    
    @Override protected Type getEventMethod(String className) {
        if(className.contains("FMLServer")) {
            className = className.replace("FML","");
            return TypeHelper.method(VOID_TYPE,TypeHelper.forge("event/server/"+className));
        }
        return super.getEventMethod(className);
    }
    
    @Override protected void writeMod(ClassWriter writer, List<Pair<String,byte[]>> classBytes) {
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
}