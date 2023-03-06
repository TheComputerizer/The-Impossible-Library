package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

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
        this.tooltipLines = tooltipLines.stream().map(line -> (Component)MutableComponent.create(new LiteralContents(line))).toList();
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
        this.centerPos = relativeCenter;
        for (int i = 0; i < resolution; i++)
            drawRadialSection(center,zLevel,radius,angles.x(),angles.y()-angles.x(),i,resolution);
    }

    public void drawCenterIcon(PoseStack matrix, float centerRadius) {
        if(this.centerIcon!=null) {
            ResourceLocation actualIcon = this.centerIcon;
            float hoverIncrease = 0f;
            if(this.hover) {
                actualIcon = this.altCenterIcon;
                hoverIncrease = centerRadius*this.iconHoverSizeIncrease;
            }
            GuiUtil.bufferSquareTexture(matrix, this.centerPos, centerRadius+hoverIncrease, actualIcon);
        }
    }

    public void drawText(Screen parent, PoseStack matrix, Vector3f mouse, boolean isCurrent) {
        if(this.centerText!=null) {
            int color = this.hover ? 16777120 : 14737632;
            drawCenteredString(matrix, Minecraft.getInstance().font, this.centerText, (int) this.centerPos.x(), (int) this.centerPos.y(), color);
        }
        if(this.hover && isCurrent) parent.renderComponentTooltip(matrix, this.tooltipLines, (int) mouse.x(), (int) mouse.y());
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
