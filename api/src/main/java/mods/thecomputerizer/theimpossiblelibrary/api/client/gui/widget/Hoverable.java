package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import java.util.Collection;

public interface Hoverable {
    
    Collection<TextAPI<?>> getHoverLines(double mouseX, double mouseY);
    boolean isHovering(double mouseX, double mouseY);
    void drawHovered(RenderContext ctx, Vector3 center, double mouseX, double mouseY);
    boolean shouldDrawHovered();
}