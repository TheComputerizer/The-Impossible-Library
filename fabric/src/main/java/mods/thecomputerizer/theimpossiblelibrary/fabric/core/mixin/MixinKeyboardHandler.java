package mods.thecomputerizer.theimpossiblelibrary.fabric.core.mixin;

import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomFabricEvents.KEY_PRESSED;

@Mixin(KeyboardHandler.class)
public class MixinKeyboardHandler {
    
    @Inject(at = @At("TAIL"), method = "keyPress")
    private void theimpossiblelibrary$onKeyPress(long windowPointer, int key, int scanCode, int action,
            int modifiers, CallbackInfo ci) {
        KEY_PRESSED.invoker().onKeyPressed(key,scanCode,action,modifiers);
    }
}