package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.asm.ModWriterNeoForge1_20;
import org.objectweb.asm.Type;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.JAVA21;

public class ModWriterNeoForge1_20_6 extends ModWriterNeoForge1_20 {
    
    protected static final Type EVENT_SUBSCRIBER = TypeHelper.neofml("common/EventBusSubscriber");
    protected static final Type EVENT_SUBSCRIBER_BUS = TypeHelper.neofml("common/EventBusSubscriber$Bus");
    
    public ModWriterNeoForge1_20_6(CoreAPI core, MultiVersionModInfo info) {
        super(core,info,JAVA21);
    }
    
    @Override protected Type getEventSubscriberBusType() {
        return EVENT_SUBSCRIBER_BUS;
    }
    
    @Override protected Type getEventSubscriberType() {
        return EVENT_SUBSCRIBER;
    }
}