package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;

public interface Selectable {
    
    boolean isSelected();
    void drawSelected(RenderContext ctx, Vector3 center, double mouseX, double mouseY);
}