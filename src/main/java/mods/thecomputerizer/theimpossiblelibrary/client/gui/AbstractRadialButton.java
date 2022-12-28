package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;

public abstract class AbstractRadialButton extends GuiComponent {

    protected final Vector4f colors;
    protected Vector3f centerPos;
    protected boolean hover;

    protected AbstractRadialButton(Vector4f colors) {
        this.colors = colors;
        this.hover = false;
        this.centerPos = new Vector3f(0,0,0);
    }

    public void setColor(int r, int b, int g, int a) {
        this.colors.set(r, b, g, a);
    }

    public Vector3f getCenterPos() {
        return this.centerPos;
    }

    protected void drawRadialSection(Vector3f center, float zLevel, Vector3f radius, float startAngle,
                                   float angleDif, int index, int resolution) {
        float angle1 = startAngle+(index/(float)resolution)*angleDif;
        float angle2 = startAngle+((index+1)/(float)resolution)*angleDif;
        Vector3f pos1In = MathUtil.getVertex(center,radius.x(),angle1);
        Vector3f pos2In = MathUtil.getVertex(center,radius.x(),angle2);
        Vector3f pos1Out = MathUtil.getVertex(center,radius.y(),angle1);
        Vector3f pos2Out = MathUtil.getVertex(center,radius.y(),angle2);
        if(this.hover)
            GuiUtil.setBuffer(pos1In,pos2In,pos1Out,pos2Out,zLevel,GuiUtil.reverseColors(this.colors));
        else
            GuiUtil.setBuffer(pos1In,pos2In,pos1Out,pos2Out,zLevel,this.colors);
    }

    protected void playPressSound(SoundManager handler) {
        handler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
