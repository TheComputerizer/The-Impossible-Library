package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;

import javax.annotation.Nullable;
import javax.vecmath.Point4f;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class RadialButton extends AbstractRadialButton {
    private final List<String> tooltipLines;
    private final ResourceLocation centerIcon;
    private final ResourceLocation altCenterIcon;
    private final float iconHoverSizeIncrease;
    private final String centerText;
    private final BiConsumer<GuiScreen, RadialButton> handlerFunction;

    public RadialButton(List<String> tooltipLines, @Nullable ResourceLocation centerIcon,
                        @Nullable ResourceLocation altCenterIcon, float hoverIncrease, @Nullable String centerText,
                        BiConsumer<GuiScreen, RadialButton> onClick) {
        super(new Point4f(0,0,0,255));
        this.handlerFunction = onClick;
        this.tooltipLines = tooltipLines;
        this.centerIcon = centerIcon;
        this.altCenterIcon = Objects.isNull(altCenterIcon) ? centerIcon : altCenterIcon;
        this.iconHoverSizeIncrease = hoverIncrease;
        this.centerText = centerText;
    }

    public void setHover(boolean superHover, double mouseDeg, Vec2f angles) {
        this.hover = superHover && mouseDeg>=angles.x && mouseDeg<angles.y;
    }

    public void draw(Vec2f center, float zLevel, Vec2f radius, Vec2f angles,
                     Vec2f mouse, Vec2f relativeCenter, int resolution) {
        setCenterPos(relativeCenter);
        for (int i = 0; i < resolution; i++)
            drawRadialSection(center,zLevel,radius,angles.x,angles.y-angles.x,i,resolution);
    }

    public void drawCenterIcon(float centerRadius) {
        if(this.centerIcon!=null) {
            ResourceLocation actualIcon = this.centerIcon;
            float hoverIncrease = 0f;
            if(this.hover) {
                actualIcon = this.altCenterIcon;
                hoverIncrease = centerRadius*this.iconHoverSizeIncrease;
            }
            GuiUtil.bufferSquareTexture(getCenterPos(), centerRadius+hoverIncrease, actualIcon);
        }
    }

    public void drawText(GuiScreen parent, Vec2f mouse, boolean isCurrent) {
        if(this.centerText!=null) {
            int color = this.hover ? 16777120 : 14737632;
            drawCenteredString(parent.mc.fontRenderer, this.centerText, (int)getCenterPos().x, (int)getCenterPos().y, color);
        }
        if(this.hover && isCurrent) parent.drawHoveringText(this.tooltipLines, (int)mouse.x, (int)mouse.y);
    }

    public void handleClick(GuiScreen screen) {
        if(this.hover) {
            playPressSound(screen.mc.getSoundHandler());
            this.handlerFunction.accept(screen, this);
        }
    }

    /**
        This is included if you wanted to be able to assign a button to a static object and create it later
     */
    @FunctionalInterface
    public interface CreatorFunction<L, R, AR, HI, S, F, B> {
        B apply(L list, @Nullable R icon, @Nullable AR altIcon, HI hovInc, @Nullable S text, F handler);
    }
}
