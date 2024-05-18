package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector3d;

import java.util.Collection;

public interface Hoverable {
    
    Collection<TextAPI<?>> getHoverLines(double x, double y);
    boolean isHovering(double x, double y);
    void drawHovered(RenderContext ctx, Vector3d center, double mouseX, double mouseY);
    boolean shouldDrawHovered();
}