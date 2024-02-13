package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.apache.logging.log4j.util.TriConsumer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class RadialProgressBar extends AbstractRadialButton {

    private final Vector2f radii;
    private final int resolution;
    private final TriConsumer<ScreenAPI<?>,RadialProgressBar,Vector2f> handlerFunction;
    @Setter
    private float progress;

    /**
     For radii: x=inner y=outer
     For colors: x=r y=g z=b w=a
     Resolution determines how round the circular progress bar will be. Higher = more round
     The initial progress should be a percentage
     */
    public RadialProgressBar(int innerRadius, int outerRadius, float initialProgress, int resolution,
                             TriConsumer<ScreenAPI<?>,RadialProgressBar,Vector2f> onClick) {
        super(new Vector4f(0,0,0,128));
        this.handlerFunction = onClick;
        this.radii = new Vector2f(innerRadius, outerRadius);
        this.resolution = resolution;
        this.progress = initialProgress;
    }

    /**
     Determines the percentage progress the mouse is pointing to
     This can be used within the handler function
     */
    public float mousePosToProgress(Vector2f mousePos) {
        float mouseAngleDeg = (float)Math.toDegrees(Math.atan2(mousePos.y-getCenterPos().y,mousePos.x-getCenterPos().x));
        if(mouseAngleDeg<0) mouseAngleDeg = 360f+mouseAngleDeg;
        return mouseAngleDeg/360f;
    }

    public boolean getHover() {
        return this.hover;
    }

    /**
     * Use this if the radial progress bar is in the center of a radial element
     */
    public void setHover(boolean superHover) {
        this.hover = superHover;
    }

    /**
     * Use this if the radial progress bar is standalone
     */
    public void setHover(Vector2f relativeCenter, double mouseDistance) {
        this.hover = MathHelper.isInCircle(relativeCenter,mouseDistance,this.radii);
    }

    /**
     * The relative center is what determines where on the screen the center of the progress bar is
     */
    public void draw(MinecraftAPI mc, Vector2f relativeCenter, float offset) {
        if(this.progress>0) {
            this.setCenterPos(relativeCenter);
            Vector2f angles = MathHelper.toRadians(MathHelper.progressAngles(this.progress));
            int adjustedRes = (int)(((float)this.resolution)*this.progress);
            for(int i=0; i<this.resolution; i++)
                drawRadialSection(mc,relativeCenter,offset,this.radii,angles.x,angles.y-angles.x,i,this.resolution);
        }
    }

    public void handleClick(ScreenAPI<?> screen, Vector2f mousePos) {
        if(this.hover) {
            playPressSound(screen.getMinecraft());
            this.handlerFunction.accept(screen,this, mousePos);
        }
    }
}