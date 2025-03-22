package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.TILCoreEntryPointFabric;
import org.objectweb.asm.Type;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.ALOAD;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.GETFIELD;
import static org.objectweb.asm.Type.BOOLEAN_TYPE;

/**
 * Accounts for the switch from PoseStack to GuiGraphics
 */
public class TILCoreEntryPointFabric1_21 extends TILCoreEntryPointFabric {
    
    static final String DEBUG_OVERLAY = mapClass("net/minecraft/client/gui/components/DebugScreenOverlay","net/minecraft/class_340");
    static final String GRAPHICS = mapClass("net/minecraft/client/gui/GuiGraphics", "net/minecraft/class_332");
    static final String GRAPHICS_POSE = mapDev("pose","method_51448");
    
    /**
     * Load local GuiGraphics & call GuiGraphics#pose to load its PoseStack
     */
    @Override protected void loadLocalPoseStack(int index) {
        insVar(ALOAD,index);
        insInvokeVirtual(GRAPHICS,GRAPHICS_POSE,TypeHelper.methodDesc(Type.getType(toDesc(POSESTACK))));
    }
    
    @Override protected CoreEntryPoint renderDebugQuery(String owner) {
        insVar(ALOAD,0);
        String mcFieldDesc = toDesc(MINECRAFT);
        String mcFieldName = mapDev("minecraft","field_2035");
        String guiFieldDesc = toDesc(GUI.replace('.','/'));
        String guiFieldName = mapDev("gui","field_1705");
        String overlayMethodDesc = TypeHelper.methodDesc(Type.getType(toDesc(DEBUG_OVERLAY)));
        String overlayMethodName = mapDev("getDebugOverlay","method_53531");
        String showDebugMethodDesc = TypeHelper.methodDesc(BOOLEAN_TYPE);
        String showDebugMethodName = mapDev("showDebugScreen","method_53536");
        return insField(GETFIELD,owner,mcFieldName,mcFieldDesc)
                .insField(GETFIELD,MINECRAFT,guiFieldName,guiFieldDesc)
                .insInvokeVirtual(GUI.replace('.','/'),overlayMethodName,overlayMethodDesc)
                .insInvokeVirtual(DEBUG_OVERLAY,showDebugMethodName,showDebugMethodDesc);
    }
}