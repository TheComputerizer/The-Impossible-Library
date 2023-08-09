package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class RadialButton extends AbstractRadialButton {
    private final List<Component> tooltipLines;
    private final ResourceLocation centerIcon;
    private final ResourceLocation altCenterIcon;
    private final float iconHoverSizeIncrease;
    private final String centerText;
    private final BiConsumer<Screen, RadialButton> handlerFunction;

    public RadialButton(List<String> tooltipLines, @Nullable ResourceLocation centerIcon,
                        @Nullable ResourceLocation altCenterIcon, float hoverIncrease, @Nullable String centerText,
                        BiConsumer<Screen, RadialButton> onClick) {
        super(new Vector4f(0,0,0,255));
        this.handlerFunction = onClick;
        this.tooltipLines = tooltipLines.stream().map(Component::literal).collect(Collectors.toList());
        this.centerIcon = centerIcon;
        this.altCenterIcon = Objects.isNull(altCenterIcon) ? centerIcon : altCenterIcon;
        this.iconHoverSizeIncrease = hoverIncrease;
        this.centerText = centerText;
    }

    public void setHover(boolean superHover, double mouseDeg, Vector3f angles) {
        this.hover = superHover && mouseDeg>=angles.x() && mouseDeg<angles.y();
    }

    public void draw(Vector3f center, float zLevel, Vector3f radius, Vector3f angles,
                     Vector3f mouse, Vector3f relativeCenter, int resolution) {
        setCenterPos(relativeCenter);
        for (int i = 0; i < resolution; i++)
            drawRadialSection(center,zLevel,radius,angles.x(),angles.y()-angles.x(),i,resolution);
    }

    public void drawCenterIcon(GuiGraphics graphics, float centerRadius) {
        if(Objects.nonNull(this.centerIcon)) {
            ResourceLocation actualIcon = this.centerIcon;
            float hoverIncrease = 0f;
            if(this.hover) {
                actualIcon = this.altCenterIcon;
                hoverIncrease = centerRadius*this.iconHoverSizeIncrease;
            }
            GuiUtil.bufferSquareTexture(graphics,getCenterPos(),centerRadius+hoverIncrease, actualIcon);
        }
    }

    public void drawText(Screen parent, GuiGraphics graphics, Vector3f mouse, boolean isCurrent) {
        Font font = Minecraft.getInstance().font;
        if(Objects.nonNull(this.centerText)) {
            int color = this.hover ? 16777120 : 14737632;
            graphics.drawCenteredString(font, this.centerText, (int) getCenterPos().x(), (int) getCenterPos().y(), color);
        }
        if(this.hover && isCurrent) graphics.renderComponentTooltip(font, this.tooltipLines, (int) mouse.x(), (int) mouse.y());
    }

    public void handleClick(Screen screen) {
        if(this.hover) {
            playPressSound(Minecraft.getInstance().getSoundManager());
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
