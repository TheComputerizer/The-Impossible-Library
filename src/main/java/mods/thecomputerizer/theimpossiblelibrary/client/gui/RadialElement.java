package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
    Creates and renders a generic radial gui element
    Set the inner radius to 0 if you want a full circle to be rendered
 */
public class RadialElement {
    private final Screen parentScreen;
    private final List<RadialButton> buttons;
    private final ResourceLocation centerIcon;
    private final ResourceLocation altCenterIcon;
    private final int iconRadius;
    private final RadialProgressBar centerProgress;
    private final List<Component> centerTooltips;
    private final String centerText;
    private final float resolution;
    private final float iconHoverSizeIncrease;
    private final Vector3f center;
    private final Vector3f radius;
    private boolean hover;
    private boolean centerHover;

    public RadialElement(Screen parent, @Nullable ResourceLocation center, @Nullable ResourceLocation altCenter,
                         @Nullable RadialProgressBar centerProgress, int centerX, int centerY, int radiusIn,
                         int radiusOut, int iconRadius, @Nullable String centerText, List<String> centerTooltips,
                         float resolution, float hoverIncrease, RadialButton ... buttons) {
        this(parent,center,altCenter,centerProgress,centerX,centerY,radiusIn,radiusOut,iconRadius,centerText,
                centerTooltips,resolution,hoverIncrease, Arrays.stream(buttons).collect(Collectors.toList()));
    }

    public RadialElement(Screen parent, @Nullable ResourceLocation center, @Nullable ResourceLocation altCenter,
                         @Nullable RadialProgressBar centerProgress, int centerX, int centerY, int radiusIn,
                         int radiusOut, int iconRadius, @Nullable String centerText, List<String> centerTooltips,
                         float resolution, float hoverIncrease, List<RadialButton> buttons) {
        this.parentScreen = parent;
        this.buttons = buttons;
        this.centerTooltips = centerTooltips.stream().map(line -> (Component)MutableComponent.create(new LiteralContents(line))).toList();
        this.centerIcon = center;
        this.altCenterIcon = Objects.isNull(altCenter) ? center : altCenter;
        this.iconRadius = iconRadius;
        this.centerProgress = centerProgress;
        this.centerText = centerText;
        this.resolution = resolution;
        this.iconHoverSizeIncrease = hoverIncrease;
        this.center = new Vector3f(centerX,centerY,0);
        this.radius = new Vector3f(radiusIn,radiusOut,0);
        this.hover = false;
        this.centerHover = false;
    }

    protected boolean calculateCenterHover(double mouseRelativeRadius) {
        if(Objects.nonNull(this.centerIcon)) return mouseRelativeRadius<=this.iconRadius;
        else if(Objects.nonNull(this.centerProgress)) return this.centerProgress.getHover();
        return MathUtil.isInCircle(this.center, mouseRelativeRadius, this.radius.x());
    }

    /**
        Remember to call this method from your GUI class if you want to be able to click on the buttons here
     */
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton==0 && this.parentScreen!=null) {
            if(Objects.nonNull(this.centerProgress)) this.centerProgress.handleClick(this.parentScreen,new Vector3f(mouseX,mouseY,0));
            for (RadialButton button : this.buttons)
                button.handleClick(this.parentScreen);
        }
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        Vector3f mouse = new Vector3f(mouseX,mouseY,0);
        double mouseAngleDeg = MathUtil.getAngle(mouse, this.center);
        double mouseRelativeRadius = MathUtil.distance(mouse, this.center);
        float numButtons = this.buttons.size();
        if(mouseAngleDeg < ((-0.5f/numButtons)+0.25f)*360) mouseAngleDeg += 360;
        boolean currentScreen = Minecraft.getInstance().screen == this.parentScreen;
        if(currentScreen) {
            this.centerHover = calculateCenterHover(mouseRelativeRadius);
            if (!this.centerHover) this.hover = MathUtil.isInCircle(this.center, mouseRelativeRadius, this.radius);
            else this.hover = false;
        } else this.centerHover = false;
        if(!this.buttons.isEmpty()) {
            int buttonRes = (int)(this.resolution/numButtons);
            int index = 0;
            for (RadialButton button : this.buttons) {
                Vector3f angles = MathUtil.makeAngleVector(index,(int)numButtons);
                if(currentScreen) button.setHover(this.hover,mouseAngleDeg,angles);
                button.draw(this.center, this.radius, MathUtil.toRadians(angles), mouse,
                        MathUtil.getCenterPosOfSlice(angles,this.radius,this.center,(int)numButtons),buttonRes);
                index++;
            }
        } else drawEmpty(mouse);
        drawCenterProgress(this.center,currentScreen);
        drawIcons(graphics, this.center, this.radius, numButtons==1);
        drawText(graphics, mouse,mouseRelativeRadius, currentScreen);
        graphics.pose().popPose();
    }

    private void drawEmpty(Vector3f mouse) {
        float startAngle = (float)Math.toRadians(-0.25f * 360);
        for (int i = 0; i < resolution; i++) {
            float angle1 = (float) Math.toRadians(startAngle + (i/resolution) * MathUtil.CIRCLE_RADIANS);
            float angle2 = (float) Math.toRadians(startAngle + ((i + 1)/resolution) * MathUtil.CIRCLE_RADIANS);
            Vector3f pos1In = MathUtil.getVertex(center, radius.x(), angle1);
            Vector3f pos2In = MathUtil.getVertex(center, radius.x(), angle2);
            Vector3f pos1Out = MathUtil.getVertex(center, radius.y(), angle1);
            Vector3f pos2Out = MathUtil.getVertex(center, radius.y(), angle2);
            if(this.hover) GuiUtil.setBuffer(pos1In,pos2In,pos1Out,pos2Out,0f,new Vector4f(255,255,255,64));
            else GuiUtil.setBuffer(pos1In,pos2In,pos1Out,pos2Out,0f,new Vector4f(0,0,0,64));
        }
    }

    private void drawIcons(GuiGraphics graphics, Vector3f center, Vector3f radius, boolean hasOneButton) {
        RenderSystem.defaultBlendFunc();
        if(Objects.nonNull(this.centerIcon)) {
            ResourceLocation actualIcon = this.centerIcon;
            int hoverIncrease = 0;
            if(this.centerHover) {
                actualIcon = this.altCenterIcon;
                hoverIncrease = (int)(((float)this.iconRadius)*this.iconHoverSizeIncrease*2f);
            }
            GuiUtil.bufferSquareTexture(graphics, center, (this.iconRadius*2)+hoverIncrease, actualIcon);
        }
        for(RadialButton button : this.buttons) button.drawCenterIcon(graphics,(radius.y()-radius.x())/2f);
    }

    private void drawCenterProgress(Vector3f center, boolean currentScreen) {
        if(Objects.nonNull(this.centerProgress)) {
            if(currentScreen) this.centerProgress.setHover(this.centerHover);
            else this.centerProgress.setHover(false);
            this.centerProgress.draw(this.center);
        }
    }

    private void drawText(GuiGraphics graphics, Vector3f mouse, double mouseRelativeRadius, boolean isCurrent) {
        if(Objects.nonNull(this.parentScreen)) {
            Font font = Minecraft.getInstance().font;
            if (Objects.nonNull(this.centerText)) {
                int color = this.centerHover ? 16777120 : 14737632;
                graphics.drawCenteredString(font, this.centerText,(int) this.center.x(), (int) this.center.y(), color);
            }
            for (RadialButton button : this.buttons) button.drawText(this.parentScreen, graphics, mouse, isCurrent);
            if (this.centerHover && isCurrent)
                graphics.renderComponentTooltip(font, this.centerTooltips, (int) mouse.x(), (int) mouse.y());
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
