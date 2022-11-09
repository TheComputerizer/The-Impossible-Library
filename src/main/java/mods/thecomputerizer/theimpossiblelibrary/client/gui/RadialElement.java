package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import javax.vecmath.Point2f;
import javax.vecmath.Point2i;
import javax.vecmath.Point4i;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
    Creates and renders a generic radial gui element
    Set the inner radius to 0 if you want a full circle to be rendered
 */
public class RadialElement extends Gui {
    private final GuiScreen parentScreen;
    private final List<RadialButton> buttons;
    private final ResourceLocation centerIcon;
    private final RadialProgressBar centerProgress;
    private final List<String> centerTooltips;
    private final String centerText;
    private final float resolution;
    private final Point2i center;
    private final Point2i radius;
    private boolean hover;
    private boolean centerHover;

    public RadialElement(GuiScreen parent, @Nullable ResourceLocation center, @Nullable RadialProgressBar centerProgress,
                         int centerX, int centerY, int radiusIn, int radiusOut, @Nullable String centerText, List<String>
                                 centerTooltips, float resolution, RadialButton ... buttons) {
        this(parent,center,centerProgress,centerX,centerY,radiusIn,radiusOut,centerText,centerTooltips,resolution,
                Arrays.stream(buttons).collect(Collectors.toList()));
    }

    public RadialElement(GuiScreen parent, @Nullable ResourceLocation center, @Nullable RadialProgressBar centerProgress,
                         int centerX, int centerY, int radiusIn, int radiusOut, @Nullable String centerText, List<String>
                                 centerTooltips, float resolution, List<RadialButton> buttons) {
        this.parentScreen = parent;
        this.buttons = buttons;
        this.centerTooltips = centerTooltips;
        this.centerIcon = center;
        this.centerProgress = centerProgress;
        this.centerText = centerText;
        this.resolution = resolution;
        this.center = new Point2i(centerX,centerY);
        this.radius = new Point2i(radiusIn,radiusOut);
        this.hover = false;
        this.centerHover = false;
    }

    /*
        Remember to call this method from your GUI class if you want to be able to click on the buttons here
     */
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton==0 && this.parentScreen!=null) {
            if(this.centerProgress!=null) this.centerProgress.handleClick(this.parentScreen,new Point2i(mouseX,mouseY));
            for (RadialButton button : this.buttons)
                button.handleClick(this.parentScreen);
        }
    }

    public void render(float zLevel, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        Point2i mouse = new Point2i(mouseX,mouseY);
        double mouseAngleDeg = Math.toDegrees(Math.atan2(mouseY - this.center.y, mouseX - this.center.x));
        double mouseRelativeRadius = Math.sqrt(Math.pow(mouseX - this.center.x, 2) + Math.pow(mouseY - this.center.y, 2));
        float numButtons = this.buttons.size();
        if(mouseAngleDeg < ((-0.5f/numButtons)+0.25f)*360) mouseAngleDeg += 360;
        this.centerHover = MathUtil.isInCircle(this.center,mouseRelativeRadius,this.radius.x);
        if(!this.centerHover) this.hover = MathUtil.isInCircle(this.center,mouseRelativeRadius,this.radius);
        if(!this.buttons.isEmpty()) {
            int buttonRes = (int)(this.resolution/numButtons);
            int index = 0;
            boolean checkHover = false;
            for (RadialButton button : this.buttons) {
                Point2f angles = MathUtil.makeAngleTuple(index,(int)numButtons);
                button.setHover(this.hover,mouseAngleDeg,angles);
                button.draw(builder, this.center, zLevel, this.radius, MathUtil.toRadians(angles), mouse,
                        MathUtil.getCenterPosOfSlice(angles,this.radius,this.center,(int)numButtons),buttonRes);
                index++;
            }
            this.hover = checkHover;
        } else drawEmpty(builder, zLevel, mouse);
        tessellator.draw();
        drawCenterProgress(tessellator,this.center);
        drawIcons(this.center, this.radius, numButtons==1);
        drawText(mouse,mouseRelativeRadius);
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    private void drawEmpty(BufferBuilder buffer, float zLevel, Point2i mouse) {
        float startAngle = (float)Math.toRadians(-0.25f * 360);
        for (int i = 0; i < resolution; i++) {
            float angle1 = (float) Math.toRadians(startAngle + (i/resolution) * MathUtil.CIRCLE_RADIANS);
            float angle2 = (float) Math.toRadians(startAngle + ((i + 1)/resolution) * MathUtil.CIRCLE_RADIANS);
            Point2i pos1In = MathUtil.getVertex(center, radius.x, angle1);
            Point2i pos2In = MathUtil.getVertex(center, radius.x, angle2);
            Point2i pos1Out = MathUtil.getVertex(center, radius.y, angle1);
            Point2i pos2Out = MathUtil.getVertex(center, radius.y, angle2);
            if(this.hover) GuiUtil.setBuffer(buffer,pos1In,pos2In,pos1Out,pos2Out,zLevel,new Point4i(255,255,255,64));
            else GuiUtil.setBuffer(buffer,pos1In,pos2In,pos1Out,pos2Out,zLevel,new Point4i(0,0,0,64));
        }
    }

    private void drawIcons(Point2i center, Point2i radius, boolean hasOneButton) {
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        if(this.centerIcon!=null)
            GuiUtil.bufferSquareTexture(center,radius.x*2,this.centerIcon);
        for(RadialButton button : this.buttons) button.drawCenterIcon((radius.y-radius.x)/2f);
        GlStateManager.disableAlpha();
        GlStateManager.disableTexture2D();
    }

    private void drawCenterProgress(Tessellator tessellator, Point2i center) {
        if(this.centerProgress!=null) {
            tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
            this.centerProgress.setHover(this.centerHover);
            this.centerProgress.draw(tessellator.getBuffer(),this.center,this.zLevel);
            tessellator.draw();
        }
    }

    private void drawText(Point2i mouse, double mouseRelativeRadius) {
        if(this.parentScreen!=null) {
            if (this.centerText != null) {
                int color = this.centerHover ? 16777120 : 14737632;
                this.parentScreen.drawCenteredString(Minecraft.getMinecraft().fontRenderer, this.centerText,
                        this.center.x, this.center.y, color);
            }
            for (RadialButton button : this.buttons) button.drawText(this.parentScreen, mouse);
            if (this.centerHover) parentScreen.drawHoveringText(this.centerTooltips, mouse.x, mouse.y);
        }
    }

    /*
        This is included if you wanted to be able to assign a radial element to a static object and create it later
     */
    @FunctionalInterface
    public interface CreatorFunction<G, R, I, S, T, K, B, C> {
        C apply(@Nullable G parent, @Nullable R icon, I location, @Nullable S text, T tooltips, K res, B buttons);
    }
}
