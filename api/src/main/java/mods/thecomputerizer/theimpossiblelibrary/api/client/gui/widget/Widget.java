package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import org.joml.Vector3d;

@Getter @Setter
public abstract class Widget {
    
    protected double x;
    protected double y;
    
    public abstract void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY);
    
    public Vector3d getCenter(Vector3d center) {
        return center;
    }
    
    public abstract double getHeight();
    public abstract double getWidth();
    public abstract void onResolutionUpdated(MinecraftWindow window);
}