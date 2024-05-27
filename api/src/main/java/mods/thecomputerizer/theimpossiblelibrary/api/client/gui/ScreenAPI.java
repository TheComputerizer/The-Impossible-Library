package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.ShapeWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.Widget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.WidgetGroup;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.Collection;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.BLACK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

@SuppressWarnings("unused") @Getter
public class ScreenAPI extends WidgetGroup {
    
    protected final ScreenAPI parentScreen;
    protected final TextAPI<?> title;
    protected final int guiScale;
    @Setter protected ShapeWidget background;
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
        this.background = asBackground(BLACK);
    }
    
    private ScreenAPI(ScreenAPI parentScreen, TextAPI<?> title, int guiScale, boolean focused) {
        super(parentScreen);
        this.parentScreen = parentScreen;
        this.title = title;
        this.guiScale = guiScale;
        this.focused = focused;
        this.background = asBackground(BLACK);
    }
    
    @SuppressWarnings("SameParameterValue")
    protected ShapeWidget asBackground(ColorCache color) {
        return ShapeWidget.from(fullScreenPlane(),color.withAlpha(defaultBackgroundDarkness()));
    }
    
    protected ShapeWidget asBackground(ResourceLocationAPI<?> texture) {
        return ShapeWidget.from(fullScreenPlane(),texture,defaultBackgroundDarkness());
    }
    
    protected ShapeWidget asBackground(TextureWrapper texture) {
        return ShapeWidget.from(fullScreenPlane(),texture.setAlpha(defaultBackgroundDarkness()));
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
    
    public float defaultBackgroundDarkness() {
        return 2f/3f;
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        if(Objects.nonNull(this.background)) this.background.draw(ctx,center,mouseX,mouseY);
        super.draw(ctx,center,mouseX,mouseY);
        Collection<TextAPI<?>> text = getHoverLines(mouseX,mouseY);
        if(!text.isEmpty()) ctx.drawTooltip(text,mouseX,mouseY);
    }
    
    @Override public void drawHovered(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {}
    
    protected Plane fullScreenPlane() {
        return ShapeHelper.plane(Y,new Vector2d(-1d,-1d),new Vector2d(1d,1d));
    }
    
    @Override public double getHeight() {
        return 2d;
    }
    
    @Override public double getWidth() {
        return 2d;
    }
    
    public boolean onCloseRequested(boolean pressedEsc) {
        return true;
    }
    
    @Override public void onScreenClosed() {
        eachWidget(Widget::onScreenClosed);
    }
    
    @Override public void onScreenOpened() {
        eachWidget(Widget::onScreenOpened);
    }
    
    public boolean shouldPauseGame() {
        return false;
    }
}
