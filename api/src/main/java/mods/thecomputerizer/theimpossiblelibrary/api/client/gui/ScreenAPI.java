package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.Widget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.WidgetGroup;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector3d;

import java.util.Collection;

@SuppressWarnings("unused") @Getter
public class ScreenAPI extends WidgetGroup {
    
    protected final ScreenAPI parentScreen;
    protected final TextAPI<?> title;
    protected final int guiScale;
    @Setter protected boolean focused;
    
    public ScreenAPI(TextAPI<?> title, MinecraftWindow window, int guiScale) {
        this(null,title,window,guiScale);
    }
    
    public ScreenAPI(ScreenAPI parentScreen, TextAPI<?> title, MinecraftWindow window, int guiScale) {
        super(parentScreen);
        this.parentScreen = parentScreen;
        this.title = title;
        this.guiScale = guiScale;
        onResolutionUpdated(window);
    }
    
    private ScreenAPI(ScreenAPI parentScreen, TextAPI<?> title, int guiScale, boolean focused) {
        super(parentScreen);
        this.parentScreen = parentScreen;
        this.title = title;
        this.guiScale = guiScale;
        this.focused = focused;
    }
    
    public void close() {
        if(onCloseRequested(false)) ScreenHelper.open(this.parentScreen);
    }
    
    public void closeAll() {
        ScreenHelper.open((ScreenAPI)null);
    }
    
    @Override public ScreenAPI copy() {
        ScreenAPI copy = new ScreenAPI(this.parentScreen,this.title,this.guiScale,this.focused);
        for(Widget widget : this.widgets) copy.addWidget(widget.copy());
        copy.height = this.height;
        copy.scaleX = this.scaleX;
        copy.scaleY = this.scaleY;
        copy.width = this.width;
        copy.x = this.x;
        copy.y = this.y;
        return copy;
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        super.draw(ctx,center,mouseX,mouseY);
        Collection<TextAPI<?>> text = getHoverLines(mouseX,mouseY);
        if(!text.isEmpty()) ctx.drawTooltip(text,mouseX,mouseY);
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
