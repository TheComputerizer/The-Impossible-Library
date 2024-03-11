package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.joml.Vector2f;
import org.joml.Vector4f;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 Creates and renders a generic radial gui element
 Set the inner radius to 0 if you want a full circle to be rendered
 */
public class RadialElement {
    private final ScreenAPI<?> parentScreen;
    private final List<RadialButton> buttons;
    private final ResourceLocationAPI<?> centerIcon;
    private final ResourceLocationAPI<?> altCenterIcon;
    private final int iconRadius;
    private final RadialProgressBar centerProgress;
    private final List<TextAPI<?>> centerTooltips;
    private final String centerText;
    private final float resolution;
    private final float iconHoverSizeIncrease;
    private final Vector2f center;
    private final Vector2f radius;
    private boolean hover;
    private boolean centerHover;

    public RadialElement(ScreenAPI<?> parent, @Nullable ResourceLocationAPI<?> center, @Nullable ResourceLocationAPI<?> altCenter,
                         @Nullable RadialProgressBar centerProgress, int centerX, int centerY, int radiusIn,
                         int radiusOut, int iconRadius, @Nullable String centerText, List<String> centerTooltips,
                         float resolution, float hoverIncrease, RadialButton ... buttons) {
        this(parent,center,altCenter,centerProgress,centerX,centerY,radiusIn,radiusOut,iconRadius,centerText,
                centerTooltips,resolution,hoverIncrease,Arrays.stream(buttons).collect(Collectors.toList()));
    }

    public RadialElement(ScreenAPI<?> parent, @Nullable ResourceLocationAPI<?> center, @Nullable ResourceLocationAPI<?> altCenter,
                         @Nullable RadialProgressBar centerProgress, int centerX, int centerY, int radiusIn,
                         int radiusOut, int iconRadius, @Nullable String centerText, List<String> centerTooltips,
                         float resolution, float hoverIncrease, List<RadialButton> buttons) {
        this.parentScreen = parent;
        this.buttons = buttons;
        this.centerTooltips = centerTooltips.stream().map(text -> parent.getMinecraft().getLiteralText(text))
                .collect(Collectors.toList());
        this.centerIcon = center;
        this.altCenterIcon = Objects.isNull(altCenter) ? center : altCenter;
        this.iconRadius = iconRadius;
        this.centerProgress = centerProgress;
        this.centerText = centerText;
        this.resolution = resolution;
        this.iconHoverSizeIncrease = hoverIncrease;
        this.center = new Vector2f(centerX,centerY);
        this.radius = new Vector2f(radiusIn,radiusOut);
        this.hover = false;
        this.centerHover = false;
    }

    protected boolean calculateCenterHover(double mouseRelativeRadius) {
        if(Objects.nonNull(this.centerIcon)) return mouseRelativeRadius<=this.iconRadius;
        else if(Objects.nonNull(this.centerProgress)) return this.centerProgress.getHover();
        return MathHelper.isInCircle(this.center, mouseRelativeRadius, this.radius.x);
    }

    /**
     Remember to call this method from your GUI class if you want to be able to click on the buttons here
     */
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton==0 && Objects.nonNull(this.parentScreen)) {
            if(Objects.nonNull(this.centerProgress)) 
                this.centerProgress.handleClick(this.parentScreen,new Vector2f(mouseX,mouseY));
            for(RadialButton button : this.buttons)
                button.handleClick(this.parentScreen);
        }
    }

    public void render(RenderAPI renderer, float offset, int mouseX, int mouseY) {
        renderer.pushMatrix();
        renderer.disableAlpha();
        renderer.enableBlend();
        renderer.disableTexture();
        renderer.setColor(1f,1f,1f,1f);
        Vector2f mouse = new Vector2f(mouseX,mouseY);
        double mouseAngleDeg = MathHelper.getAngle(mouse,this.center);
        double mouseRelativeRadius = MathHelper.distance(mouse,this.center);
        float numButtons = this.buttons.size();
        if(mouseAngleDeg<((-0.5f/numButtons)+0.25f)*360d) mouseAngleDeg+=360d;
        boolean currentScreen = renderer.getMinecraft().isCurrentScreen(this.parentScreen);
        if(currentScreen) {
            this.centerHover = calculateCenterHover(mouseRelativeRadius);
            if(!this.centerHover) this.hover = MathHelper.isInCircle(this.center,mouseRelativeRadius,this.radius);
            else this.hover = false;
        } else this.centerHover = false;
        if(!this.buttons.isEmpty()) {
            int buttonRes = (int)(this.resolution/numButtons);
            int index = 0;
            for (RadialButton button : this.buttons) {
                Vector2f angles = MathHelper.makeAngleVector(index,(int)numButtons);
                if(currentScreen) button.setHover(this.hover,mouseAngleDeg,angles);
                button.draw(renderer,this.center,offset,this.radius,MathHelper.toRadians(angles),mouse,
                        MathHelper.getCenterPosOfSlice(angles,this.radius,this.center,(int)numButtons),buttonRes);
                index++;
            }
        } else drawEmpty(renderer,offset,mouse);
        drawCenterProgress(renderer,this.center,currentScreen,offset);
        drawIcons(renderer,this.center,this.radius,numButtons==1);
        drawText(renderer,mouse,mouseRelativeRadius,currentScreen);
        renderer.enableTexture();
        renderer.popMatrix();
    }

    private void drawEmpty(RenderAPI renderer, float offset, Vector2f mouse) {
        float startAngle = (float)Math.toRadians(-0.25f*360);
        for(int i=0; i<this.resolution; i++) {
            float angle1 = (float)Math.toRadians(startAngle+(i/this.resolution)*MathHelper.CIRCLE_RADIANS);
            float angle2 = (float)Math.toRadians(startAngle+((i+1)/this.resolution)*MathHelper.CIRCLE_RADIANS);
            Vector2f pos1In = MathHelper.getVertex(this.center,this.radius.x,angle1);
            Vector2f pos2In = MathHelper.getVertex(this.center,this.radius.x,angle2);
            Vector2f pos1Out = MathHelper.getVertex(this.center,this.radius.y,angle1);
            Vector2f pos2Out = MathHelper.getVertex(this.center,this.radius.y,angle2);
            Vector4f color = this.hover ? new Vector4f(255,255,255,64) : new Vector4f(0,0,0,64);
            RenderHelper.setBuffer(renderer,pos1In,pos2In,pos1Out,pos2Out,offset,color);
        }
    }

    private void drawIcons(RenderAPI renderer, Vector2f center, Vector2f radius, boolean hasOneButton) {
        renderer.enableTexture();
        renderer.enableAlpha();
        if(Objects.nonNull(this.centerIcon)) {
            ResourceLocationAPI<?> actualIcon = this.centerIcon;
            int hoverIncrease = 0;
            if(this.centerHover) {
                actualIcon = this.altCenterIcon;
                hoverIncrease = (int)(((float)this.iconRadius)*this.iconHoverSizeIncrease*2f);
            }
            RenderHelper.bufferSquareTex(renderer,center,(this.iconRadius*2)+hoverIncrease,1f,actualIcon);
        }
        for(RadialButton button : this.buttons) button.drawCenterIcon(renderer,(radius.y-radius.x)/2f);
        renderer.disableAlpha();
        renderer.disableTexture();
    }

    private void drawCenterProgress(RenderAPI renderer, Vector2f center, boolean currentScreen, float offset) {
        if(Objects.nonNull(this.centerProgress)) {
            if(currentScreen) this.centerProgress.setHover(this.centerHover);
            else this.centerProgress.setHover(false);
            this.centerProgress.draw(renderer,this.center,offset);
        }
    }

    private void drawText(RenderAPI renderer, Vector2f mouse, double mouseRelativeRadius, boolean isCurrent) {
        if(Objects.nonNull(this.parentScreen)) {
            FontAPI font = renderer.getFont();
            if(Objects.nonNull(this.centerText)) {
                int color = this.centerHover ? 16777120 : 14737632;
                renderer.drawCenteredString(font,this.centerText,(int)this.center.x,(int)this.center.y,color);
            }
            for(RadialButton button : this.buttons) button.drawText(renderer,mouse,isCurrent);
            MinecraftWindow window = renderer.getWindow();
            renderer.renderTooltip(font,this.centerTooltips,(int)mouse.x,(int)mouse.y,(int)window.getWidthF(),
                    (int)window.getHeightF(),-1);
        }
    }

    /**
     This is included if you wanted to be able to assign a radial element to a static object and create it later
     */
    @FunctionalInterface
    public interface CreatorFunction<P,C,AC,B,X,Y,I,O,IR,T,CT,R,HI,RB,E> {
        E apply(P parent, @Nullable C center, @Nullable AC altCenter, @Nullable B bar, X x, Y y, I in, O out, IR iconRad,
                @Nullable T text, CT tooltips, R res, HI hovInc, RB buttons);
    }
}