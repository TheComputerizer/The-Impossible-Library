package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class RadialButton extends AbstractRadialButton {
    private final List<ITextComponent> tooltipLines;
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
        this.tooltipLines = tooltipLines.stream().map(StringTextComponent::new).collect(Collectors.toList());
        this.centerIcon = centerIcon;
        this.altCenterIcon = Objects.isNull(altCenterIcon) ? centerIcon : altCenterIcon;
        this.iconHoverSizeIncrease = hoverIncrease;
        this.centerText = centerText;
    }

    public void setHover(boolean superHover, double mouseDeg, Vector2f angles) {
        this.hover = superHover && mouseDeg>=angles.x && mouseDeg<angles.y;
    }

    public void draw(Vector2f center, float zLevel, Vector2f radius, Vector2f angles,
                     Vector2f mouse, Vector2f relativeCenter, int resolution) {
        setCenterPos(relativeCenter);
        for (int i = 0; i < resolution; i++)
            drawRadialSection(center,zLevel,radius,angles.x,angles.y-angles.x,i,resolution);
    }

    public void drawCenterIcon(MatrixStack matrix, float centerRadius) {
        if(this.centerIcon!=null) {
            ResourceLocation actualIcon = this.centerIcon;
            float hoverIncrease = 0f;
            if(this.hover) {
                actualIcon = this.altCenterIcon;
                hoverIncrease = centerRadius*this.iconHoverSizeIncrease;
            }
            GuiUtil.bufferSquareTexture(matrix,getCenterPos(),centerRadius+hoverIncrease, actualIcon);
        }
    }

    public void drawText(Screen parent, MatrixStack matrix, Vector2f mouse, boolean isCurrent) {
        if(Objects.nonNull(this.centerText)) {
            int color = this.hover ? 16777120 : 14737632;
            drawCenteredString(matrix,parent.getMinecraft().font,this.centerText,(int)getCenterPos().x,(int)getCenterPos().y, color);
        }
        if(this.hover && isCurrent) parent.renderComponentTooltip(matrix,this.tooltipLines,(int)mouse.x,(int)mouse.y);
    }

    public void handleClick(Screen screen) {
        if(this.hover) {
            playPressSound(screen.getMinecraft().getSoundManager());
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
