package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.BOOLEAN_TYPE;
import static org.objectweb.asm.Type.INT_TYPE;
import static org.objectweb.asm.Type.VOID_TYPE;

public class TILCoreEntryPointFabric extends CoreEntryPoint { //TODO Clean this up once it works as intended
    
    static final String ARRAYLIST_OWNER = "java/util/ArrayList";
    static final String COLLECTION_OWNER = "java/util/Collection";
    static final String DEBUG_OWNER = DEV ? "net/minecraft/client/gui/components/DebugScreenOverlay" : "net/minecraft/class_340";
    static final String EVENTS_OWNER = "mods/thecomputerizer/theimpossiblelibrary/fabric/common/event/CustomFabricEvents";
    static final String EVENT_OWNER  = "net/fabricmc/fabric/api/event/Event";
    static final String EVENT_DESC = "Lnet/fabricmc/fabric/api/event/Event;";
    static final String KEYBOARD_OWNER = DEV ? "net/minecraft/client/KeyboardHandler" : "net/minecraft/class_309";
    static final String INVOKER_DESC = TypeHelper.method(OBJECT_TYPE,new Type[]{}).getDescriptor();
    static final String LIST_DESC = "Ljava/util/List;";
    static final String LIST_OWNER = "java/util/List";
    static final String POSESTACK_OWNER = DEV ? "com/mojang/blaze3d/vertex/PoseStack" : "net/minecraft/class_4587";
    static final String STRING_LIST_SIGNATURE = "Ljava/util/List<Ljava/lang/String;>;";
   
    public TILCoreEntryPointFabric() {
        TILRef.logInfo("Initialized core version handler {}",getClass());
    }
    
    void addRenderFields(List<FieldNode> fields) {
        for(String name : new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"})
            fields.add(new FieldNode(PROTECTED_FINAL,name,LIST_DESC,STRING_LIST_SIGNATURE,null));
    }
    
    InsnList buildKeyPressInvoker() {
        String keyPressedOwner = customEventOwner("KeyPressed");
        String keyPressedDesc = TypeHelper.method(VOID_TYPE,INT_TYPE,INT_TYPE,INT_TYPE,INT_TYPE).getDescriptor();
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(GETSTATIC,EVENTS_OWNER,"KEY_PRESSED",EVENT_DESC));
        list.add(new MethodInsnNode(INVOKEVIRTUAL,EVENT_OWNER,"invoker",INVOKER_DESC));
        list.add(new TypeInsnNode(CHECKCAST,keyPressedOwner));
        list.add(new VarInsnNode(ILOAD,3));
        list.add(new VarInsnNode(ILOAD,4));
        list.add(new VarInsnNode(ILOAD,5));
        list.add(new VarInsnNode(ILOAD,6));
        list.add(new MethodInsnNode(INVOKEINTERFACE,keyPressedOwner,"onKeyPressed",keyPressedDesc,true));
        return list;
    }
    
    InsnList buildRenderDebugInvoker() {
        String renderDebugOwner = customEventOwner("RenderDebugInfo");
        Type listType = Type.getType(List.class);
        Type poseStackType = Type.getType("L"+POSESTACK_OWNER+";");
        String renderDebugDesc = TypeHelper.method(VOID_TYPE,poseStackType,listType,listType).getDescriptor();
        InsnList list = new InsnList();
        boolean game = true;
        String getInfoDesc = TypeHelper.method(List.class,new Type[]{}).getDescriptor();
        String addAllDesc = TypeHelper.method(BOOLEAN_TYPE,Collection.class).getDescriptor();
        for(String name : new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"}) {
            list.add(new VarInsnNode(ALOAD,0));
            list.add(new FieldInsnNode(GETFIELD,DEBUG_OWNER,name,LIST_DESC));
            list.add(new MethodInsnNode(INVOKEINTERFACE,LIST_OWNER,"clear",EMPTY_METHOD.getDescriptor(),true));
            list.add(new VarInsnNode(ALOAD,0));
            list.add(new FieldInsnNode(GETFIELD,DEBUG_OWNER,name,LIST_DESC));
            list.add(new VarInsnNode(ALOAD,0));
            String methodName = game ? (DEV ? "getGameInformation" : "method_1835") : (DEV ? "getSystemInformation" : "method_1839");
            list.add(new MethodInsnNode(INVOKEVIRTUAL,DEBUG_OWNER,methodName,getInfoDesc));
            list.add(new MethodInsnNode(INVOKEINTERFACE,LIST_OWNER,"addAll",addAllDesc,true));
            game = false;
        }
        list.add(new FieldInsnNode(GETSTATIC,EVENTS_OWNER,"RENDER_DEBUG_INFO",EVENT_DESC));
        list.add(new MethodInsnNode(INVOKEVIRTUAL,EVENT_OWNER,"invoker",INVOKER_DESC));
        list.add(new TypeInsnNode(CHECKCAST,renderDebugOwner));
        list.add(new VarInsnNode(ALOAD,1));
        for(String name : new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"}) {
            list.add(new VarInsnNode(ALOAD,0));
            list.add(new FieldInsnNode(GETFIELD,DEBUG_OWNER,name,LIST_DESC));
        }
        list.add(new MethodInsnNode(INVOKEINTERFACE,renderDebugOwner,"onRenderDebug",renderDebugDesc,true));
        return list;
    }
    
    @Override public List<String> classTargets() {
        return Arrays.asList(KEYBOARD_OWNER,DEBUG_OWNER);
    }
    
    String customEventOwner(String name) {
        return EVENTS_OWNER+"$"+name;
    }
    
    @Override public ClassNode editClass(ClassNode classNode) {
        if(isTarget(classNode)) {
            String name = classNode.name;
            boolean screenOverlay = name.endsWith("class_340") || name.endsWith("DebugScreenOverlay");
            boolean keyboard = name.endsWith("class_309") || name.endsWith("KeyboardHandler");
            if(screenOverlay) addRenderFields(classNode.fields);
            for(MethodNode method : classNode.methods) {
                InsnList code = method.instructions;
                if(keyboard && Misc.equalsAny(method.name,"keyPress","method_25404")) {
                    TILRef.logInfo("Building KEY_PRESSED invoker");
                    code.insert(ASMHelper.findLabel(code,38),buildKeyPressInvoker());
                }
                else if(screenOverlay) {
                    if(method.name.equals("<init>")) code.insert(ASMHelper.findNode(code,node ->
                                    node.getOpcode()==INVOKESPECIAL,0),initRenderFields());
                    else if(Misc.equalsAny(method.name,"drawGameInformation","method_1847")) {
                        replace(code,"theimpossiblelibrary$left");
                        TILRef.logInfo("Building RENDER_DEBUG_INFO invoker");
                        code.insertBefore(code.getFirst(),buildRenderDebugInvoker());
                    } else if(Misc.equalsAny(method.name,"drawSystemInformation","method_1848"))
                        replace(code,"theimpossiblelibrary$right");
                }
            }
            
        }
        return classNode;
    }
    
    @Override public String getCoreID() {
        return MODID+"_core";
    }
    
    @Override public String getCoreName() {
        return NAME+" Core";
    }
    
    InsnList initRenderFields() {
        InsnList fields = new InsnList();
        for(String name : new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"}) {
            fields.add(new VarInsnNode(ALOAD,0));
            fields.add(new TypeInsnNode(NEW,ARRAYLIST_OWNER));
            fields.add(new InsnNode(DUP));
            fields.add(new MethodInsnNode(INVOKESPECIAL,ARRAYLIST_OWNER,"<init>",EMPTY_METHOD.getDescriptor()));
            fields.add(new FieldInsnNode(PUTFIELD,DEBUG_OWNER,name,LIST_DESC));
        }
        return fields;
    }
    
    void replace(InsnList code, String name) {
        Supplier<FieldInsnNode> fieldNode = () -> new FieldInsnNode(GETFIELD,DEBUG_OWNER,name,LIST_DESC);
        ASMHelper.replaceNode(code,node -> node.getOpcode()==INVOKEVIRTUAL ? fieldNode.get() : node,0,0);
    }
}