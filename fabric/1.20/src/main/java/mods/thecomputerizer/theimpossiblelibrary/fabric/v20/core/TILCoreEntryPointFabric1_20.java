package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.TILCoreEntryPointFabric;
import org.objectweb.asm.Type;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.ALOAD;

/**
 * Accounts for the switch from PoseStack to GuiGraphics
 */
public class TILCoreEntryPointFabric1_20 extends TILCoreEntryPointFabric {
    
    static final String GRAPHICS = mapClass("net/minecraft/client/gui/GuiGraphics", "net/minecraft/class_332");
    static final String GRAPHICS_POSE = mapDev("pose","method_51448");
    
    /**
     * Load local GuiGraphics & call GuiGraphics#pose to load its PoseStack
     */
    @Override protected void loadLocalPoseStack(int index) {
        insVar(ALOAD,index);
        insInvokeVirtual(GRAPHICS,GRAPHICS_POSE,TypeHelper.methodDesc(Type.getType(toDesc(POSESTACK))));
    }
}
