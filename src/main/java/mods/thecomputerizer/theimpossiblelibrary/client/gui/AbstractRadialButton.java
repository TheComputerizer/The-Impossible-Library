package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.init.SoundEvents;

import javax.vecmath.Point2i;
import javax.vecmath.Point4i;

public abstract class AbstractRadialButton extends Gui {

    protected final Point4i colors;
    protected final Point2i centerPos;
    protected boolean hover;

    protected AbstractRadialButton(Point4i colors) {
        this.colors = colors;
        this.hover = false;
        this.centerPos = new Point2i(0,0);
    }

    public void setColor(int r, int b, int g, int a) {
        this.colors.set(r, b, g, a);
    }

    public Point2i getCenterPos() {
        return this.centerPos;
    }

    protected void drawRadialSection(BufferBuilder buffer, Point2i center, float zLevel, Point2i radius, float startAngle,
                                   float angleDif, int index, int resolution) {
        float angle1 = startAngle+(index/(float)resolution)*angleDif;
        float angle2 = startAngle+((index+1)/(float)resolution)*angleDif;
        Point2i pos1In = MathUtil.getVertex(center,radius.x,angle1);
        Point2i pos2In = MathUtil.getVertex(center,radius.x,angle2);
        Point2i pos1Out = MathUtil.getVertex(center,radius.y,angle1);
        Point2i pos2Out = MathUtil.getVertex(center,radius.y,angle2);
        if(this.hover)
            GuiUtil.setBuffer(buffer,pos1In,pos2In,pos1Out,pos2Out,zLevel,GuiUtil.reverseColors(this.colors));
        else
            GuiUtil.setBuffer(buffer,pos1In,pos2In,pos1Out,pos2Out,zLevel,this.colors);
    }

    protected void playPressSound(SoundHandler handler) {
        handler.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
