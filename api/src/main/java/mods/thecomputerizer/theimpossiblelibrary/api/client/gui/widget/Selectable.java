package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import org.joml.Vector3d;

public interface Selectable {
    
    boolean isSelected();
    void drawSelected(RenderContext ctx, Vector3d center, double mouseX, double mouseY);
}