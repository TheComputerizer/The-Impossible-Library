package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.asm.ModWriterNeoForge;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import java.util.List;
import java.util.Map.Entry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.JAVA21;
import static org.objectweb.asm.Type.VOID_TYPE;

public class ModWriterNeoForge1_21 extends ModWriterNeoForge {
    
    public ModWriterNeoForge1_21(CoreAPI core, MultiVersionModInfo info) {
        super(core,info,JAVA21);
    }
    
    @Override protected Type getEventMethod(String className) {
        if(className.contains("FMLServer")) {
            className = className.replace("FML","");
            return TypeHelper.method(VOID_TYPE,TypeHelper.neoforge("event/server/"+className));
        }
        return super.getEventMethod(className);
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
}