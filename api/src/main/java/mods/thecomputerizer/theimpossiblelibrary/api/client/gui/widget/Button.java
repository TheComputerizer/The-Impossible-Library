package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle.CircleSlice;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Square;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector2d;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class Button extends BoundedWidgetGroup {
    
    public static Button basic(TextAPI<?> text, TextAPI<?> ... hoverLines) {
        return basic(0d, 0d, text,Arrays.asList(hoverLines));
    }
    
    public static Button basic(TextAPI<?> text, Collection<TextAPI<?>> hoverLines) {
        return basic(0d,0d,text,hoverLines);
    }
    
    public static Button basic(Vector2d center, TextAPI<?> text, TextAPI<?> ... hoverLines) {
        return basic(center.x,center.y,text,Arrays.asList(hoverLines));
    }
    
    public static Button basic(double centerX, double centerY, TextAPI<?> text, TextAPI<?> ... hoverLines) {
        return basic(centerX,centerY,text,Arrays.asList(hoverLines));
    }
    
    public static Button basic(Vector2d center, TextAPI<?> text, Collection<TextAPI<?>> hoverLines) {
        return basic(center.x,center.y,text,hoverLines);
    }
    
    public static Button basic(double centerX, double centerY, TextAPI<?> text, Collection<TextAPI<?>> hoverLines) {
        double heightRatio = RenderHelper.getCurrentHeightRatio()*10d; //Vanilla buttons are 200x20
        Square shape = ShapeHelper.square(Y,0.25,heightRatio);
        ShapeWidget texture = ShapeWidget.from(shape,ScreenHelper.getVanillaButtonTexture(false,false),centerX,centerY);
        TextWidget textWidget = TextWidget.from(text);
        ShapeWidget hoverTex = ShapeWidget.from(shape,ScreenHelper.getVanillaButtonTexture(true,false),centerX,centerY);
        Widget hover = BasicWidgetGroup.from(hoverTex,textWidget,ShapeWidget.outlineFrom(shape,2f,centerX,centerY));
        Button button = new Button(texture,TextWidget.from(text),hover);
        button.hoverLines.addAll(hoverLines);
        return button;
    }
    
    public static WidgetGroup raidalGroup(Circle circle, Vector2d center, int slices,
            BiConsumer<Integer,Button> settings) {
        return BasicWidgetGroup.from(raidal(circle,center,slices,settings));
    }
    
    public static WidgetGroup raidalGroup(Circle circle, double centerX, double centerY, int slices,
            BiConsumer<Integer,Button> settings) {
        return BasicWidgetGroup.from(raidal(circle,centerX,centerY,slices,settings));
    }
    
    public static Button[] raidal(Circle circle, Vector2d center, int slices,
            BiConsumer<Integer,Button> settings) {
        return raidal(circle,center.x,center.y,slices,settings);
    }
    
    public static Button[] raidal(Circle circle, double centerX, double centerY, int slices, BiConsumer<Integer,Button> settings) {
        Button[] buttons = new Button[Math.max(slices,1)];
        CircleSlice[] sliceArray = circle.slice(Math.max(slices,1));
        for(int i=0;i<sliceArray.length;i++) {
            final int index = i;
            buttons[i] = raidal(sliceArray[i],centerX,centerY,button -> settings.accept(index,button));
        }
        return buttons;
    }
    
    public static Button raidal(Circle circle, Vector2d center, Consumer<Button> settings) {
        return raidal(circle,center.x,center.y,settings);
    }
    
    public static Button raidal(Circle circle, double centerX, double centerY, Consumer<Button> settings) {
        Button button = new Button(ShapeWidget.from(circle,centerX,centerY),null,null);
        settings.accept(button);
        return button;
    }
    
    @Getter protected final ShapeWidget shape;
    @Getter protected final TextWidget text;
    @Getter protected Collection<TextAPI<?>> hoverLines;
    @Setter protected Widget hover;
    @Getter protected Consumer<Button> clickFunc;
    
    public Button(@Nullable ShapeWidget shape, @Nullable TextWidget text, @Nullable Widget hover) {
        this.shape = shape;
        this.text = text;
        this.hover = hover;
        this.x = Objects.nonNull(shape) ? shape.x : 0d;
        this.y = Objects.nonNull(shape) ? shape.y : 0d;
        this.hoverLines = new ArrayList<>();
        if(Objects.nonNull(shape)) this.widgets.add(shape);
        if(Objects.nonNull(text)) this.widgets.add(text);
    }
    
    @Override public void drawHovered(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        if(Objects.nonNull(this.hover)) this.hover.draw(ctx,center,mouseX,mouseY);
        else draw(ctx,center,mouseX,mouseY);
    }
    
    @Override public double getHeight() {
        return this.shape.getHeight();
    }
    
    public void addHoverLine(TextAPI<?> text) {
        this.hoverLines.add(text);
    }
    
    public void addHoverLines(TextAPI<?> ... text) {
        this.hoverLines.addAll(Arrays.asList(text));
    }
    
    public void addHoverLines(Collection<TextAPI<?>> text) {
        this.hoverLines.addAll(text);
    }
    
    @Override public Collection<TextAPI<?>> getHoverLines(double x, double y) {
        return Objects.nonNull(this.hoverLines) && isHovering(x,y) ? this.hoverLines : Collections.emptyList();
    }
    
    @Override public double getWidth() {
        return this.shape.getWidth();
    }
    
    @Override public boolean isHovering(double x, double y) {
        return this.shape.isInside(x,y,0d);
    }
    
    /**
     No click actions by default
     */
    @Override public boolean onClicked(double x, double y, boolean leftClick) {
        if(isHovering(x,y)) {
            playClickSound();
            if(Objects.nonNull(this.clickFunc)) this.clickFunc.accept(this);
            return true;
        }
        return false;
    }
    
    @Override public void playClickSound() {
        ScreenHelper.playVanillaClickSound();
    }
    
    public Button setClickFunc(Consumer<Button> onClick) {
        this.clickFunc = onClick;
        return this;
    }
    
    public Button setHoverLines(Collection<TextAPI<?>> text) {
        this.hoverLines = text;
        return this;
    }
    
    @Override public boolean shouldDrawHovered() {
        return Objects.nonNull(this.hover) || !this.hoverLines.isEmpty();
    }
}
