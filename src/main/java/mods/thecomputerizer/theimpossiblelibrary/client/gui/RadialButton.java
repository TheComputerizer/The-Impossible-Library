package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.GuiUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import javax.vecmath.Vector2f;
import java.util.List;
import java.util.function.Function;

public class RadialButton extends Gui {
    private final List<String> tooltipLines;
    private final ResourceLocation centerIcon;
    private final String centerText;
    private final int r;
    private final int b;
    private final int g;
    private final int a;
    private final Function<GuiScreen, Object> handlerFunction;
    private boolean hover;
    private final Vector2f centerPos;

    public RadialButton(List<String> tooltipLines, @Nullable ResourceLocation centerIcon, @Nullable String centerText,
                        Function<GuiScreen, Object> applyClick) {
        this.tooltipLines = tooltipLines;
        this.centerIcon = centerIcon;
        this.centerText = centerText;
        this.r = 0;
        this.b = 0;
        this.g = 0;
        this.a = 64;
        this.hover = false;
        this.centerPos = new Vector2f(0,0);
        this.handlerFunction = applyClick;
    }

    public void draw(BufferBuilder builder, Vector2f center, float zLevel, Vector2f radius, Vector2f angles,
                        Vector2f mouse, Vector2f relativeCenter, int resolution) {
        this.centerPos.set(relativeCenter);
        for (int i = 0; i < resolution; i++)
            drawRadialSection(builder,center,zLevel,radius,angles.x,angles.y-angles.x,i,resolution);
    }

    private void drawRadialSection(BufferBuilder buffer, Vector2f center, float zLevel, Vector2f radius, float startAngle,
                                   float angleDif, int index, int resolution) {
        float angle1 = startAngle+(index/(float)resolution)*angleDif;
        float angle2 = startAngle+((index+1)/(float)resolution)*angleDif;
        Vector2f pos1In = MathUtil.getVertex(center,radius.x,angle1);
        Vector2f pos2In = MathUtil.getVertex(center,radius.x,angle2);
        Vector2f pos1Out = MathUtil.getVertex(center,radius.y,angle1);
        Vector2f pos2Out = MathUtil.getVertex(center,radius.y,angle2);
        if(this.hover) GuiUtil.setBuffer(buffer,pos1In,pos2In,pos1Out,pos2Out,zLevel,255,255,255,this.a);
        else GuiUtil.setBuffer(buffer,pos1In,pos2In,pos1Out,pos2Out,zLevel,this.r,this.g,this.b,this.a);
    }

    public void drawCenterIcon(float centerRadius) {
        if(this.centerIcon!=null)
            GuiUtil.bufferSquareTexture(this.centerPos,centerRadius,this.centerIcon);
    }

    /*
        The angles are input in degrees here
     */
    public void setHover(boolean superHover, double mouseDeg, Vector2f angles) {
        this.hover = superHover && mouseDeg>=angles.x && mouseDeg<angles.y;
    }

    public void drawText(GuiScreen parent, Vector2f mouse) {
        if(this.centerText!=null) {
            int color = this.hover ? 16777120 : 14737632;
            drawCenteredString(parent.mc.fontRenderer, this.centerText, (int) this.centerPos.x, (int) this.centerPos.y, color);
        }
        if(this.hover) parent.drawHoveringText(this.tooltipLines, (int) mouse.x, (int) mouse.y);
    }

    @Nullable
    public Object handleClick(GuiScreen screen) {
        if(this.hover) return this.handlerFunction.apply(screen);
        return null;
    }

    /*
        This is included if you wanted to be able to assign a button to a static object and create it later
     */
    @FunctionalInterface
    public interface CreatorFunction<L, R, S, F, B> {
        B apply(L list, @Nullable R icon, @Nullable S text, F handler);
    }
}
