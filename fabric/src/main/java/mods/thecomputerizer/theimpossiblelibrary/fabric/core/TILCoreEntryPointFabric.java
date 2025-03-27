package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
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
    static final String CUSTOM_EVENTS = "mods/thecomputerizer/theimpossiblelibrary/fabric/client/event/CustomClientFabricEvents";
    static final String FABRIC_EVENT = "net/fabricmc/fabric/api/event/Event";
    protected static final String GUI = mapClass("net.minecraft.client.gui.Gui", "net.minecraft.class_329");
    static final String KEYBOARD_HANDLER = mapClass("net.minecraft.client.KeyboardHandler", "net.minecraft.class_309");
    static final String INVOKER_DESC = TypeHelper.methodDesc(OBJECT_TYPE);
    static final String LIST = "java/util/List";
    protected static final String MINECRAFT = mapClass("net/minecraft/client/Minecraft", "net/minecraft/class_310");
    static final String OPTIONS = mapClass("net/minecraft/client/Options", "net/minecraft/class_315");
    protected static final String POSESTACK = mapClass("com.mojang.blaze3d.vertex.PoseStack", "net.minecraft.class_4587");
    static final String REF = Type.getInternalName(TILRef.class);
    static final String SHARED_HANDLES_CLIENT = Type.getInternalName(SharedHandlesClient.class);
    
    protected static String mapClass(String dev, String notDev) {
        return CoreAPI.getInstance().mapClassName(mapDev(dev,notDev),false);
    }
    
    protected static String mapDev(String dev, String notDev) {
        return DEV ? dev : notDev;
    }
    
    protected final CoreAPI core;
    
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
    
    InsnList buildRenderDebugInvoker(ClassNode node, String owner) {
        boolean actualDebug = DEBUG_OVERLAY.equals(owner);
        String renderDebugOwner = customEventOwner("RenderDebugInfo");
        Type  listType = Type.getType(List.class);
        String renderDebugDesc = TypeHelper.voidMethodDesc(Type.getType(toDesc(POSESTACK)),listType,listType);
        String getInfoDesc = TypeHelper.methodDesc(List.class);
        String addAllDesc = TypeHelper.methodDesc(BOOLEAN_TYPE,Collection.class);
        String listDesc = toDesc(LIST);
        beginList(new InsnList());
        if(actualDebug) {
            for(String name : DEBUG_LIST_FIELDS) { // set up list fields
                // clear lists
                insThis().insField(GETFIELD,owner,name,listDesc).insInvokeInterface(LIST,"clear");
                boolean left = name.endsWith("left");
                String methodName = this.core.mapMethodName(node.name,left ?
                        (DEV ? "getGameInformation" : "method_1835") :
                        (DEV ? "getSystemInformation" : "method_1839"),getInfoDesc);
                // collect & add to lists
                insThis().insField(GETFIELD,owner,name,listDesc)
                        .insThis().insInvokeVirtual(owner,methodName,getInfoDesc)
                        .insInvokeInterface(LIST,"addAll",addAllDesc);
            }
        } else  {
            renderDebugQuery(owner).insIf(IF_NOT_EQUAL,new Label());
            for(String name : DEBUG_LIST_FIELDS)
                insThis().insField(GETFIELD,owner,name,listDesc).insInvokeInterface(LIST,"clear");
        }
        // get RENDER_DEBUG_INFO event field
        insField(GETSTATIC,CUSTOM_EVENTS,"RENDER_DEBUG_INFO",toDesc(FABRIC_EVENT))
                .insInvokeVirtual(FABRIC_EVENT,"invoker",INVOKER_DESC).insType(CHECKCAST,renderDebugOwner);
        loadLocalPoseStack(1); // load PoseStack parameter
        for(String name : DEBUG_LIST_FIELDS) insThis().insField(GETFIELD,owner,name,listDesc); // load lists
        insInvokeInterface(renderDebugOwner,"onRenderDebug",renderDebugDesc); // invoke event
        if(!actualDebug) {
            String renderDesc = TypeHelper.voidMethodDesc(OBJECT_TYPE,listType,listType);
            insInvokeStatic(REF,"getClientHandles",TypeHelper.methodDesc(SharedHandlesClient.class)); // get client handles
            insVar(ALOAD,1); // load PoseStack or GuiGraphics parameter depending on the version
            for(String name : DEBUG_LIST_FIELDS) insThis().insField(GETFIELD,owner,name,listDesc); // load lists
            insInvokeVirtual(SHARED_HANDLES_CLIENT,"renderDebugText",renderDesc); // call renderDebugText
        }
        return actualDebug ? endList() : insLabel().endList();
    }
    
    @Override public List<String> classTargets() {
        return this.core.isClientSide() ? Arrays.asList(KEYBOARD_HANDLER,DEBUG_OVERLAY,GUI) : Collections.emptyList();
    }
    
    String customEventOwner(String name) {
        return CUSTOM_EVENTS+"$"+name;
    }
    
    @Override public ClassNode editClass(ClassNode classNode) {
        TILRef.logInfo("Editing class node for {}",classNode.name);
        if(isTarget(classNode)) {
            String name = getClassName(classNode);
            boolean gui = name.endsWith("class_329") || name.endsWith("Gui");
            boolean screenOverlay = name.endsWith("class_340") || name.endsWith("DebugScreenOverlay");
            boolean keyboard = name.endsWith("class_309") || name.endsWith("KeyboardHandler");
            if(gui || screenOverlay) addRenderFields(classNode.fields);
            for(MethodNode method : classNode.methods) {
                InsnList code = method.instructions;
                String methodName = getMethodName(classNode,method);
                if(keyboard && Misc.equalsAny(methodName,"keyPress","method_1466")) {
                    int ordinal = keyPressOrdinal(this.core.getVersion());
                    TILRef.logInfo("Building KEY_PRESSED invoker with ordinal {}",ordinal);
                    code.insert(ASMHelper.findLabel(code,ordinal),buildKeyPressInvoker());
                } else if(screenOverlay) {
                    if(methodName.equals("<init>")) {
                        Function<AbstractInsnNode,Boolean> compare = node -> node.getOpcode()==INVOKESPECIAL;
                        AbstractInsnNode node = ASMHelper.findNode(code,compare,0);
                        code.insert(node,initRenderFields(DEBUG_OVERLAY));
                    } else if(Misc.equalsAny(methodName,"drawGameInformation","method_1847")) {
                        replace(code,"theimpossiblelibrary$left");
                        TILRef.logInfo("Building RENDER_DEBUG_INFO invoker for debug screen");
                        code.insertBefore(code.getFirst(),buildRenderDebugInvoker(classNode,DEBUG_OVERLAY));
                    } else if(Misc.equalsAny(methodName,"drawSystemInformation","method_1848"))
                        replace(code,"theimpossiblelibrary$right");
                } else if(gui) {
                    if(methodName.equals("<init>")) {
                        Function<AbstractInsnNode,Boolean> compare = node -> node.getOpcode()==INVOKESPECIAL;
                        AbstractInsnNode node = ASMHelper.findNode(code,compare,0);
                        code.insert(node,initRenderFields(GUI));
                    } else if(Misc.equalsAny(methodName,"render","method_1753")) {
                        int ordinal = guiRenderOrdinal(this.core.getVersion());
                        TILRef.logInfo("Building RENDER_DEBUG_INFO invoker for gui with ordinal {}",ordinal);
                        AbstractInsnNode label = ASMHelper.findLabel(code,ordinal);
                        code.insertBefore(label,buildRenderDebugInvoker(classNode,GUI));
                    }
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
    
    int guiRenderOrdinal(GameVersion version) {
        switch(version) {
            case V16_5: return 60;
            case V18_2:
            case V19_2: return 68;
            case V19_4: return 62;
            case V20_1:
            case V20_4: return 58;
            default: return 3;
        }
    }
    
    int keyPressOrdinal(GameVersion version) {
        switch(version) {
            case V16_5: return 38;
            case V18_2:
            case V19_2: return 45;
            case V19_4:
            case V20_1: return 48;
            case V20_4:
            case V20_6: return 50;
            default: return 52;
        }
    }
    
    InsnList initRenderFields(String owner) {
        beginList(new InsnList());
        // create list fields & initialize them with new ArrayList instances
        for(String name : new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"})
            insThis().insType(NEW,ARRAYLIST).insBasic(DUP).insInvokeSpecial(ARRAYLIST,"<init>")
                    .insField(PUTFIELD,owner,name,toDesc(LIST));
        return endList();
    }
    
    @SuppressWarnings("SameParameterValue")
    protected void loadLocalPoseStack(int index) {
        insVar(ALOAD,index);
    }
    
    protected CoreEntryPoint renderDebugQuery(String owner) {
        insVar(ALOAD,0);
        String mcFieldDesc = toDesc(MINECRAFT);
        String mcFieldName = mapDev("minecraft","field_2035");
        String optionsFieldDesc = toDesc(OPTIONS);
        String optionsFieldName = mapDev("options","field_1690");
        String renderFieldName = mapDev("renderDebug","field_1866");
        return insField(GETFIELD,owner,mcFieldName,mcFieldDesc)
                .insField(GETFIELD,MINECRAFT,optionsFieldName,optionsFieldDesc)
                .insField(GETFIELD,OPTIONS,renderFieldName,"Z");
    }
    
    void replace(InsnList code, String name) {
        Supplier<FieldInsnNode> fieldNode = () -> new FieldInsnNode(GETFIELD,DEBUG_OVERLAY,name,toDesc(LIST));
        ASMHelper.replaceNode(code,node -> node.getOpcode()==INVOKEVIRTUAL ? fieldNode.get() : node,0,0);
    }
}