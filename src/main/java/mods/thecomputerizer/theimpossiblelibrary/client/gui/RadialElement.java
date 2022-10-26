package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.GuiUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.LogUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;
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

    @Nullable
    public Object mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton==0 && this.hover)
            for(RadialButton button : this.buttons)
                if(button.getHover()) return button.handleClick(this.parentScreen);
        return null;
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
        float numButtons = this.buttons.size();
        if(!this.buttons.isEmpty()) {
            int buttonRes = (int)(this.resolution/numButtons);
            int index = 0;
            boolean checkHover = false;
            for (RadialButton button : this.buttons) {
                float startAngle = (((index - 0.5f) / numButtons) + 0.25f) * 360;
                float endAngle = (((index + 0.5f) / numButtons) + 0.25f) * 360;
                if(button.draw(buffer, this.center, zLevel, this.radius, (float)Math.toRadians(startAngle),
                        (float)Math.toRadians(endAngle), mouse, buttonRes))
                    checkHover = true;
                index++;
            }
            this.hover = checkHover;
        } else drawEmpty(buffer, zLevel, mouse);
        tessellator.draw();
        //buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        //drawIcons(buffer, this.center, this.radius, zLevel, numButtons==1);
        //tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
        drawText(mouse);
    }

    private void drawEmpty(BufferBuilder buffer, float zLevel, Vector2f mouse) {
        this.hover = MathUtil.isInCircle(mouse,this.center,this.radius);
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

    private void drawIcons(BufferBuilder buffer, Vector2f center, Vector2f radius, float zLevel, boolean hasOneButton) {
        if(this.centerIcon!=null) GuiUtil.bufferSquareTexture(buffer,center,radius.x,zLevel,this.centerIcon);
        //for(RadialButton button : this.buttons) button.drawCenterIcon(buffer,center,radius,zLevel,hasOneButton);
    }

    private boolean isCenterHovering(Vector2f mouse) {
        return !this.hover && MathUtil.isInCircle(mouse,this.center,new Vector2f(0,this.radius.y));
    }

    private void drawText(Vector2f mouse) {
        int color;
        if(this.centerText!=null) {
            color = this.hover ? 16777120 : 14737632;
            this.parentScreen.drawCenteredString(Minecraft.getMinecraft().fontRenderer, this.centerText,
                    (int)this.center.x, (int)this.center.y, color);
        }
        for(RadialButton button : this.buttons) {
            String text = button.getCenterText();
            if(text!=null) {
                Vector2f centerPos = button.getCenterPos();
                color = button.getHover() ? 16777120 : 14737632;
                this.parentScreen.drawCenteredString(Minecraft.getMinecraft().fontRenderer, text, (int)centerPos.x,
                        (int)centerPos.y,color);
            }
        }
        if(isCenterHovering(mouse) && !this.centerTooltips.isEmpty())
            parentScreen.drawHoveringText(this.centerTooltips,(int)mouse.x,(int)mouse.y);
        else
            for(RadialButton button : this.buttons)
                if(button.getHover())
                    parentScreen.drawHoveringText(button.getTooltipLines(),(int)mouse.x,(int)mouse.y);

    }

    /*
        This is included if you wanted to be able to assign a radial element to a static object and create it later
     */
    @FunctionalInterface
    public interface CreatorFunction<G, R, I, S, T, K, B, C> {
        C apply(@Nullable G parent, @Nullable R icon, I location, @Nullable S text, T tooltips, K res, B buttons);
    }
}
