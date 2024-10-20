package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
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

public class TILCoreEntryPointFabric extends CoreEntryPoint {
    
    static final String ARRAYLIST = "java/util/ArrayList";
    static final String DEBUG_OVERLAY = mapClass("net.minecraft.client.gui.components.DebugScreenOverlay", "net.minecraft.class_340");
    static final String[] DEBUG_LIST_FIELDS = new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"};
    static final String CUSTOM_EVENTS = "mods/thecomputerizer/theimpossiblelibrary/fabric/common/event/CustomFabricEvents";
    static final String FABRIC_EVENT = "net/fabricmc/fabric/api/event/Event";
    static final String KEYBOARD_HANDLER = mapClass("net.minecraft.client.KeyboardHandler", "net.minecraft.class_309");
    static final String INVOKER_DESC = TypeHelper.methodDesc(OBJECT_TYPE);
    static final String LIST = "java/util/List";
    static final String POSESTACK = mapClass("com.mojang.blaze3d.vertex.PoseStack", "net.minecraft.class_4587");
    
    static String mapClass(String dev, String notDev) {
        return CoreAPI.getInstance().mapClassName(DEV ? dev : notDev,false);
    }
    
    final CoreAPI core;
   
    public TILCoreEntryPointFabric() {
        this.core = CoreAPI.getInstance();
        TILRef.logInfo("Initialized core version handler {}",getClass());
    }
    
    void addRenderFields(List<FieldNode> fields) {
        String signature = toSignature(LIST, String.class.getName());
        for(String name : new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"})
            fields.add(new FieldNode(PROTECTED_FINAL,name,toDesc(LIST),signature,null));
    }
    
    InsnList buildKeyPressInvoker() {
        String keyPressedOwner = customEventOwner("KeyPressed");
        String keyPressedDesc = TypeHelper.voidMethodDesc(INT_TYPE,INT_TYPE,INT_TYPE,INT_TYPE);
        // get KEY_PRESSED event field
        beginList(new InsnList()).insField(GETSTATIC,CUSTOM_EVENTS,"KEY_PRESSED",toDesc(FABRIC_EVENT))
                .insInvokeVirtual(FABRIC_EVENT,"invoker",INVOKER_DESC).insType(CHECKCAST,keyPressedOwner);
        // load parameters
        for(int i : new int[]{3,4,5,6}) insVar(ILOAD,i);
        // invoke event
        return insInvokeInterface(keyPressedOwner,"onKeyPressed",keyPressedDesc).endList();
    }
    
    InsnList buildRenderDebugInvoker(ClassNode node) {
        String renderDebugOwner = customEventOwner("RenderDebugInfo");
        Type listType = Type.getType(List.class);
        String renderDebugDesc = TypeHelper.voidMethodDesc(Type.getType(toDesc(POSESTACK)),listType,listType);
        String getInfoDesc = TypeHelper.methodDesc(List.class);
        String addAllDesc = TypeHelper.methodDesc(BOOLEAN_TYPE,Collection.class);
        beginList(new InsnList());
        // set up list fields
        for(String name : DEBUG_LIST_FIELDS) {
            boolean left = name.endsWith("left");
            String methodName = this.core.mapMethodName(node.name,left ? (DEV ? "getGameInformation" : "method_1835") :
                    (DEV ? "getSystemInformation" : "method_1839"),getInfoDesc);
            // clear lists
            insThis().insField(GETFIELD,DEBUG_OVERLAY,name,toDesc(LIST)).insInvokeInterface(LIST,"clear");
            // collect & add to lists
            insThis().insField(GETFIELD,DEBUG_OVERLAY,name,toDesc(LIST))
                    .insThis().insInvokeVirtual(DEBUG_OVERLAY,methodName,getInfoDesc)
                    .insInvokeInterface(LIST, "addAll",addAllDesc);
        }
        // get RENDER_DEBUG_INFO event field
        insField(GETSTATIC,CUSTOM_EVENTS,"RENDER_DEBUG_INFO",toDesc(FABRIC_EVENT))
                .insInvokeVirtual(FABRIC_EVENT,"invoker",INVOKER_DESC).insType(CHECKCAST,renderDebugOwner);
        // load PoseStack parameter
        insVar(ALOAD,1);
        // load lists
        for(String name : DEBUG_LIST_FIELDS) insThis().insField(GETFIELD,DEBUG_OVERLAY,name,toDesc(LIST));
        // invoke event
        return insInvokeInterface(renderDebugOwner,"onRenderDebug",renderDebugDesc).endList();
    }
    
    @Override public List<String> classTargets() {
        return Arrays.asList(KEYBOARD_HANDLER, DEBUG_OVERLAY);
    }
    
    String customEventOwner(String name) {
        return CUSTOM_EVENTS+"$"+name;
    }
    
    @Override public ClassNode editClass(ClassNode classNode) {
        TILRef.logInfo("Editing class node for {}",classNode.name);
        if(isTarget(classNode)) {
            String name = getClassName(classNode);
            TILRef.logInfo("Editing mapped class node {}",name);
            boolean screenOverlay = name.endsWith("class_340") || name.endsWith("DebugScreenOverlay");
            boolean keyboard = name.endsWith("class_309") || name.endsWith("KeyboardHandler");
            if(screenOverlay) addRenderFields(classNode.fields);
            for(MethodNode method : classNode.methods) {
                InsnList code = method.instructions;
                String methodName = getMethodName(classNode,method);
                TILRef.logInfo("Editing method node {}({})",method.name,methodName);
                if(keyboard && Misc.equalsAny(methodName,"keyPress","method_1466")) {
                    TILRef.logInfo("Building KEY_PRESSED invoker");
                    code.insert(ASMHelper.findLabel(code,38),buildKeyPressInvoker());
                }
                else if(screenOverlay) {
                    if(methodName.equals("<init>"))
                        code.insert(ASMHelper.findNode(code,node -> node.getOpcode()==INVOKESPECIAL,0),
                                    initRenderFields());
                    else if(Misc.equalsAny(methodName,"drawGameInformation","method_1847")) {
                        replace(code,"theimpossiblelibrary$left");
                        TILRef.logInfo("Building RENDER_DEBUG_INFO invoker");
                        code.insertBefore(code.getFirst(),buildRenderDebugInvoker(classNode));
                    } else if(Misc.equalsAny(methodName,"drawSystemInformation","method_1848"))
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
        beginList(new InsnList());
        // create list fields & initialize them with new ArrayList instances
        for(String name : new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"})
            insThis().insType(NEW,ARRAYLIST).insBasic(DUP).insInvokeSpecial(ARRAYLIST,"<init>")
                    .insField(PUTFIELD,DEBUG_OVERLAY,name,toDesc(LIST));
        return endList();
    }
    
    void replace(InsnList code, String name) {
        Supplier<FieldInsnNode> fieldNode = () -> new FieldInsnNode(GETFIELD,DEBUG_OVERLAY,name,toDesc(LIST));
        ASMHelper.replaceNode(code,node -> node.getOpcode()==INVOKEVIRTUAL ? fieldNode.get() : node,0,0);
    }
}