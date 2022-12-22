package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector4f;
import org.apache.logging.log4j.util.TriConsumer;

public class RadialProgressBar extends AbstractRadialButton {

    private final Vector2f radii;
    private final int resolution;
    private final TriConsumer<Screen, RadialProgressBar, Vector2f> handlerFunction;
    private float progress;

    /*
        For radii: x=inner y=outer
        For colors: x=r y=g z=b w=a
        Resolution determines how round the circular progress bar will be. Higher = more round
        The initial progress should be a percentage
     */
    public RadialProgressBar(int innerRadius, int outerRadius, float initialProgress, int resolution,
                             TriConsumer<Screen, RadialProgressBar, Vector2f> onClick) {
        super(new Vector4f(0,0,0,128));
        this.handlerFunction = onClick;
        this.radii = new Vector2f(innerRadius, outerRadius);
        this.resolution = resolution;
        this.progress = initialProgress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    /*
        Determines the percentage progress the mouse is pointing to
        This can be used within the handler function
     */
    public float mousePosToProgress(Vector2f mousePos) {
        float mouseAngleDeg = (float) Math.toDegrees(Math.atan2(mousePos.y - this.centerPos.y, mousePos.x - this.centerPos.x));
        if(mouseAngleDeg<0) mouseAngleDeg = 360f+mouseAngleDeg;
        return mouseAngleDeg/360f;
    }

    public boolean getHover() {
        return this.hover;
    }

    /*
        Use this if the radial progress bar is in the center of a radial element
     */
    public void setHover(boolean superHover) {
        this.hover = superHover;
    }

    /*
        Use this if the radial progress bar is standalone
     */
    public void setHover(Vector2f relativeCenter, double mouseDistance) {
        this.hover = MathUtil.isInCircle(relativeCenter, mouseDistance, this.radii);
    }

    /*
        The relative center is what determines where on the screen the center of the progress bar is
     */
    public void draw(Vector2f relativeCenter, float zLevel) {
        if(this.progress>0) {
            this.centerPos = relativeCenter;
            Vector2f angles = MathUtil.toRadians(MathUtil.progressAngles(this.progress));
            int adjustedRes = (int) (((float)this.resolution)*this.progress);
            for (int i = 0; i < this.resolution; i++)
                drawRadialSection(relativeCenter, zLevel, this.radii, angles.x, angles.y - angles.x, i, this.resolution);
        }
    }

    public void handleClick(Screen screen, Vector2f mousePos) {
        if(this.hover) {
            playPressSound(screen.getMinecraft().getSoundManager());
            this.handlerFunction.accept(screen, this, mousePos);
        }
    }
}
