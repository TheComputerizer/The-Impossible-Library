package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.GuiUtil;
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
import javax.vecmath.Vector2f;
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
    private final List<String> centerTooltips;
    private final String centerText;
    private final float resolution;
    private final Vector2f center;
    private final Vector2f radius;
    private boolean hover;

    public RadialElement(GuiScreen parent, @Nullable ResourceLocation center, int centerX, int centerY, int radiusIn,
                  int radiusOut, @Nullable String centerText, List<String> centerTooltips, float resolution,
                  RadialButton ... buttons) {
        this.parentScreen = parent;
        this.buttons = Arrays.stream(buttons).collect(Collectors.toList());
        this.centerTooltips = centerTooltips;
        this.centerIcon = center;
        this.centerText = centerText;
        this.resolution = resolution;
        this.center = new Vector2f(centerX,centerY);
        this.radius = new Vector2f(radiusIn,radiusOut);
        this.hover = false;
    }

    /*
        This will return null if nothing was successfully clicked on
     */
    @Nullable
    public Object mousePressed(int mouseX, int mouseY, int mouseButton) {
        Object ret = null;
        if(mouseButton==0 && this.parentScreen!=null) {
            for (RadialButton button : this.buttons) {
                ret = button.handleClick(this.parentScreen);
                if(ret!=null) break;
            }
        }
        return ret;
    }

    public void render(float zLevel, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        Vector2f mouse = new Vector2f(mouseX,mouseY);
        double mouseAngleDeg = Math.toDegrees(Math.atan2(mouseY - this.center.y, mouseX - this.center.x));
        double mouseRelativeRadius = Math.sqrt(Math.pow(mouseX - this.center.x, 2) + Math.pow(mouseY - this.center.y, 2));
        float numButtons = this.buttons.size();
        if(mouseAngleDeg < ((-0.5f/numButtons)+0.25f)*360) mouseAngleDeg += 360;
        this.hover = MathUtil.isInCircle(this.center,mouseRelativeRadius,this.radius);
        if(!this.buttons.isEmpty()) {
            int buttonRes = (int)(this.resolution/numButtons);
            int index = 0;
            boolean checkHover = false;
            for (RadialButton button : this.buttons) {
                Vector2f angles = MathUtil.makeAngleVector(index,(int)numButtons);
                button.setHover(this.hover,mouseAngleDeg,angles);
                button.draw(buffer, this.center, zLevel, this.radius, MathUtil.toRadians(angles), mouse,
                        MathUtil.getCenterPosOfSlice(angles,this.radius,this.center,(int)numButtons),buttonRes);
                index++;
            }
            this.hover = checkHover;
        } else drawEmpty(buffer, zLevel, mouse);
        tessellator.draw();
        drawIcons(tessellator, this.center, this.radius, numButtons==1);
        drawText(mouse,mouseRelativeRadius);
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    private void drawEmpty(BufferBuilder buffer, float zLevel, Vector2f mouse) {
        float startAngle = (float)Math.toRadians(-0.25f * 360);
        for (int i = 0; i < resolution; i++) {
            float angle1 = (float) Math.toRadians(startAngle + (i/resolution) * MathUtil.CIRCLE_RADIANS);
            float angle2 = (float) Math.toRadians(startAngle + ((i + 1)/resolution) * MathUtil.CIRCLE_RADIANS);
            Vector2f pos1In = MathUtil.getVertex(center, radius.x, angle1);
            Vector2f pos2In = MathUtil.getVertex(center, radius.x, angle2);
            Vector2f pos1Out = MathUtil.getVertex(center, radius.y, angle1);
            Vector2f pos2Out = MathUtil.getVertex(center, radius.y, angle2);
            if(this.hover) GuiUtil.setBuffer(buffer,pos1In,pos2In,pos1Out,pos2Out,zLevel,255,255,255,64);
            else GuiUtil.setBuffer(buffer,pos1In,pos2In,pos1Out,pos2Out,zLevel,0,0,0,64);
        }
    }

    private void drawIcons(Tessellator tessellator, Vector2f center, Vector2f radius, boolean hasOneButton) {
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        if(this.centerIcon!=null)
            GuiUtil.bufferSquareTexture(center,radius.x*2,this.centerIcon);
        for(RadialButton button : this.buttons) button.drawCenterIcon((radius.y-radius.x)/2f);
        GlStateManager.disableAlpha();
        GlStateManager.disableTexture2D();
    }

    private void drawText(Vector2f mouse, double mouseRelativeRadius) {
        if(this.parentScreen!=null) {
            if (this.centerText != null) {
                int color = mouseRelativeRadius < this.radius.x ? 16777120 : 14737632;
                this.parentScreen.drawCenteredString(Minecraft.getMinecraft().fontRenderer, this.centerText,
                        (int) this.center.x, (int) this.center.y, color);
            }
            for (RadialButton button : this.buttons) button.drawText(this.parentScreen, mouse);
            if (mouseRelativeRadius < this.radius.x) parentScreen.drawHoveringText(this.centerTooltips, (int) mouse.x, (int) mouse.y);
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
