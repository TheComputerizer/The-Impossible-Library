package mods.thecomputerizer.theimpossiblelibrary.forge.v21.core;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.TILCoreEntryPointForge;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.BOOLEAN_TYPE;

public class TILCoreEntryPointForge1_21 extends TILCoreEntryPointForge {
    
    static final String ARRAYLIST = "java/util/ArrayList";
    static final String[] DEBUG_LIST_FIELDS = new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"};
    static final String DEBUG_OVERLAY = "net/minecraft/client/gui/components/DebugScreenOverlay";
    protected static final String GUI = "net/minecraft/client/gui/Gui";
    static final String LIST = "java/util/List";
    protected static final String MINECRAFT = "net/minecraft/client/Minecraft";
    static final String REF = Type.getInternalName(TILRef.class);
    static final String SHARED_HANDLES_CLIENT = Type.getInternalName(SharedHandlesClient.class);
    
    protected final CoreAPI core;
    
    public TILCoreEntryPointForge1_21(CoreAPI core) {
        this.core = core;
        TILRef.logInfo("Initialized core version handler {}",getClass());
    }
    
    void addRenderFields(List<FieldNode> fields) {
        String signature = toSignature(LIST,String.class.getName());
        for(String name : new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"})
            fields.add(new FieldNode(PROTECTED_FINAL,name,toDesc(LIST),signature,null));
    }
    
    InsnList buildRenderDebugInvoker() {
        Type listType = Type.getType(List.class);
        String listDesc = toDesc(LIST);
        beginList(new InsnList());
        renderDebugQuery().insIf(IF_NOT_EQUAL,new Label());
        for(String name : DEBUG_LIST_FIELDS)
            insThis().insField(GETFIELD,guiClass(),name,listDesc).insInvokeInterface(LIST,"clear");
        String renderDesc = TypeHelper.voidMethodDesc(OBJECT_TYPE,listType,listType);
        insInvokeStatic(REF,"getClientHandles",TypeHelper.methodDesc(SharedHandlesClient.class)); // get client handles
        insVar(ALOAD,1); // load PoseStack or GuiGraphics parameter depending on the version
        for(String name : DEBUG_LIST_FIELDS) insThis().insField(GETFIELD,guiClass(),name,listDesc); // load lists
        insInvokeVirtual(SHARED_HANDLES_CLIENT,"renderDebugText",renderDesc); // call renderDebugText
        return insLabel().endList();
    }
    
    @Override public List<String> classTargets() {
        return List.of(guiClass());
    }
    
    @Override public ClassNode editClass(ClassNode classNode) {
        TILRef.logInfo("Editing class node for {}",classNode.name);
        if(isTarget(classNode)) {
            addRenderFields(classNode.fields);
            for(MethodNode method : classNode.methods) {
                InsnList code = method.instructions;
                String methodName = getMethodName(classNode,method);
                if("<init>".equals(methodName)) {
                    Function<AbstractInsnNode,Boolean> compare = node -> node.getOpcode()==INVOKESPECIAL;
                    AbstractInsnNode node = ASMHelper.findNode(code,compare,0);
                    code.insert(node,initRenderFields());
                } else if("render".equals(methodName)) {
                    TILRef.logInfo("Building RENDER_DEBUG_INFO invoker for gui with ordinal {}",3);
                    AbstractInsnNode label = ASMHelper.findLabel(code,3);
                    code.insertBefore(label,buildRenderDebugInvoker());
                }
            }
        }
        return classNode;
    }
    
    String guiClass() {
        return GUI;
    }
    
    InsnList initRenderFields() {
        beginList(new InsnList());
        // create list fields & initialize them with new ArrayList instances
        for(String name : new String[]{"theimpossiblelibrary$left","theimpossiblelibrary$right"})
            insThis().insType(NEW,ARRAYLIST).insBasic(DUP).insInvokeSpecial(ARRAYLIST,"<init>")
                    .insField(PUTFIELD,guiClass(),name,toDesc(LIST));
        return endList();
    }
    
    protected CoreEntryPoint renderDebugQuery() {
        insVar(ALOAD,0);
        String overlayMethodDesc = TypeHelper.methodDesc(Type.getType(toDesc(DEBUG_OVERLAY)));
        return insField(GETFIELD,guiClass(),"minecraft",toDesc(MINECRAFT))
                .insField(GETFIELD,MINECRAFT,"gui",toDesc(GUI.replace('.', '/')))
                .insInvokeVirtual(GUI.replace('.','/'),"getDebugOverlay",overlayMethodDesc)
                .insInvokeVirtual(DEBUG_OVERLAY,"showDebugScreen",TypeHelper.methodDesc(BOOLEAN_TYPE));
    }
}