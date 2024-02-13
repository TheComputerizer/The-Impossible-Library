package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector2f;
import org.joml.Vector4f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class RadialButton extends AbstractRadialButton {

    private final List<TextAPI<?>> tooltipLines;
    private final ResourceLocationAPI centerIcon;
    private final ResourceLocationAPI altCenterIcon;
    private final float iconHoverSizeIncrease;
    private final String centerText;
    private final BiConsumer<ScreenAPI<?>,RadialButton> handlerFunction;

    public RadialButton(MinecraftAPI mc, List<String> tooltipLines, @Nullable ResourceLocationAPI centerIcon,
                        @Nullable ResourceLocationAPI altCenterIcon, float hoverIncrease, @Nullable String centerText,
                        BiConsumer<ScreenAPI<?>,RadialButton> onClick) {
        super(new Vector4f(0,0,0,255));
        this.handlerFunction = onClick;
        this.tooltipLines = tooltipLines.stream().map(mc::getLiteralText).collect(Collectors.toList());
        this.centerIcon = centerIcon;
        this.altCenterIcon = Objects.isNull(altCenterIcon) ? centerIcon : altCenterIcon;
        this.iconHoverSizeIncrease = hoverIncrease;
        this.centerText = centerText;
    }

    public void setHover(boolean superHover, double mouseDeg, Vector2f angles) {
        this.hover = superHover && mouseDeg>=angles.x() && mouseDeg<angles.y();
    }

    public void draw(MinecraftAPI mc, Vector2f center, float zLevel, Vector2f radius, Vector2f angles,
                     Vector2f mouse, Vector2f relativeCenter, int resolution) {
        setCenterPos(relativeCenter);
        for (int i = 0; i < resolution; i++)
            drawRadialSection(mc,center,zLevel,radius,angles.x(),angles.y()-angles.x(),i,resolution);
    }

    public void drawCenterIcon(MinecraftAPI mc, float centerRadius) {
        if(Objects.nonNull(this.centerIcon)) {
            ResourceLocationAPI actualIcon = this.centerIcon;
            float hoverIncrease = 0f;
            if(this.hover) {
                actualIcon = this.altCenterIcon;
                hoverIncrease = centerRadius*this.iconHoverSizeIncrease;
            }
            Vector2f center = getCenterPos();
            float radius = centerRadius+hoverIncrease;
            MinecraftWindow window = mc.getWindow();
            RenderHelper.bufferSquareTex(mc,center,centerRadius+hoverIncrease,this.colors.w,actualIcon);
        }
    }

    public void drawText(MinecraftAPI mc, Vector2f mouse, boolean isCurrent) {
        FontAPI font = mc.getFont();
        RenderAPI renderer = mc.getRenderer();
        if(Objects.nonNull(this.centerText)) {
            int color = this.hover ? 16777120 : 14737632;
            renderer.drawCenteredString(font,this.centerText,(int)getCenterPos().x(),(int)getCenterPos().y(),color);
        }
        if(this.hover && isCurrent) renderer.renderTooltip(font,this.tooltipLines,(int) mouse.x(),(int) mouse.y());
    }

    public void handleClick(ScreenAPI<?> screen) {
        if(this.hover) {
            playPressSound(screen.getMinecraft());
            this.handlerFunction.accept(screen,this);
        }
    }
}