package mods.thecomputerizer.theimpossiblelibrary.mixin;


import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FontRenderer.class)
public class MixinFont {

    /**
     * @author The_Computerizer
     * @reason Lower alpha cutoff for text rendering for smoother fade in
     */
    @Overwrite
    private static int adjustColor(int pColor) {
        return (pColor & -33554432) == 0 ? pColor | -16777216 : pColor;
    }
}