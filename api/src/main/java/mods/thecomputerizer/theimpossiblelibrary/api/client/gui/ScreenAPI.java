package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.WidgetGroup;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector3d;

@SuppressWarnings("unused") @Getter
public class ScreenAPI extends WidgetGroup {
    
    protected final ScreenAPI parent;
    protected final TextAPI<?> title;
    
    public ScreenAPI(TextAPI<?> title, MinecraftWindow window) {
        this(null,title,window);
    }
    
    public ScreenAPI(ScreenAPI parent, TextAPI<?> title, MinecraftWindow window) {
        super(true);
        this.parent = parent;
        this.title = title;
        onResolutionUpdated(window);
    }
    
    public void close() {
        if(onCloseRequested(false)) onScreenClosed();
    }
    
    @Override public void drawHovered(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {}
    
    @Override public double getHeight() {
        return 2d;
    }
    
    @Override public double getWidth() {
        return 2d;
    }
    
    public void onScreenClosed() {}
    
    public boolean onCloseRequested(boolean pressedEsc) {
        return true;
    }
    
    public float defaultBackgroundDarkness() {
        return 1f;
    }
    
    public boolean shouldPauseGame() {
        return false;
    }
}
