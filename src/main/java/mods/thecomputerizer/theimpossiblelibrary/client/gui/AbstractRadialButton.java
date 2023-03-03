package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector4f;

public abstract class AbstractRadialButton extends AbstractGui {

    protected final Vector4f colors;
    protected Vector2f centerPos;
    protected boolean hover;

    protected AbstractRadialButton(Vector4f colors) {
        this.colors = colors;
        this.hover = false;
        this.centerPos = new Vector2f(0,0);
    }

    public void setColor(int r, int b, int g, int a) {
        this.colors.set(r, b, g, a);
    }

    public Vector2f getCenterPos() {
        return this.centerPos;
    }

    protected void drawRadialSection(Vector2f center, float zLevel, Vector2f radius, float startAngle,
                                   float angleDif, int index, int resolution) {
        float angle1 = startAngle+(index/(float)resolution)*angleDif;
        float angle2 = startAngle+((index+1)/(float)resolution)*angleDif;
        Vector2f pos1In = MathUtil.getVertex(center,radius.x,angle1);
        Vector2f pos2In = MathUtil.getVertex(center,radius.x,angle2);
        Vector2f pos1Out = MathUtil.getVertex(center,radius.y,angle1);
        Vector2f pos2Out = MathUtil.getVertex(center,radius.y,angle2);
        if(this.hover) GuiUtil.setBuffer(pos1In,pos2In,pos1Out,pos2Out,zLevel,GuiUtil.reverseColors(this.colors));
        else GuiUtil.setBuffer(pos1In,pos2In,pos1Out,pos2Out,zLevel,this.colors);
    }

    protected void playPressSound(SoundHandler handler) {
        handler.play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
