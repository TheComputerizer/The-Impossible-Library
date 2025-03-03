package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.font.Font1_19;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;

import static net.minecraft.client.gui.Font.DisplayMode.NORMAL;

public class Font1_19_4 extends Font1_19 {
    
    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        getWrapped().draw(getMatrix(renderer),text,x,y,14737632);
    }
    
    @Override public void drawInBatch(Object text, float x, float y, int color, boolean shadow, Object matrix,
            Object source, boolean transparent, int bgColor, int light) {
        getWrapped().drawInBatch((FormattedCharSequence)text,x,y,color,shadow,(Matrix4f)matrix,
                                 (MultiBufferSource)source,NORMAL,bgColor,light);
    }
    
    @Override public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        getWrapped().drawShadow(getMatrix(renderer),text,x,y,14737632);
    }
}
