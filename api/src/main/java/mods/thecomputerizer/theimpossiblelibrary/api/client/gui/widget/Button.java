package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper;
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

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.RED;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class Button extends WidgetGroup {
    
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
    
    public static Button colored(ColorCache color, TextAPI<?> text, TextAPI<?> ... hoverLines) {
        return colored(0d,0d,color,text,Arrays.asList(hoverLines));
    }
    
    public static Button colored(ColorCache color, TextAPI<?> text, Collection<TextAPI<?>> hoverLines) {
        return colored(0d,0d,color,text,hoverLines);
    }
    
    public static Button colored(Vector2d center, ColorCache color, TextAPI<?> text, TextAPI<?> ... hoverLines) {
        return colored(center.x,center.y,color,text,Arrays.asList(hoverLines));
    }
    
    public static Button colored(Vector2d center, ColorCache color, TextAPI<?> text, Collection<TextAPI<?>> hoverLines) {
        return colored(center.x,center.y,color,text,hoverLines);
    }
    
    public static Button colored(double centerX, double centerY, ColorCache color, TextAPI<?> text, TextAPI<?> ... hoverLines) {
        return colored(centerX,centerY,color,text,Arrays.asList(hoverLines));
    }
    
    public static Button colored(double centerX, double centerY, ColorCache color, TextAPI<?> text, Collection<TextAPI<?>> hoverLines) {
        double heightRatio = RenderHelper.getCurrentHeightRatio()*10d; //Vanilla buttons are 200x20
        Square shape = ShapeHelper.square(Y,0.25,heightRatio);
        ShapeWidget colorWidget = ShapeWidget.from(shape,color,centerX,centerY);
        TextWidget textWidget = TextWidget.from(text);
        ShapeWidget hoverColor = ShapeWidget.from(shape,ColorHelper.reverse(color,color.a()),centerX,centerY);
        Widget hover = BasicWidgetGroup.from(hoverColor,textWidget,ShapeWidget.outlineFrom(shape,RED,2f,centerX,centerY));
        Button button = new Button(colorWidget,TextWidget.from(text),hover);
        button.hoverLines.addAll(hoverLines);
        return button;
    }
    
    public static WidgetGroup raidalGroup(Circle circle, Vector2d center, int slices,
            BiConsumer<Integer,Button> settings) {
        return BasicWidgetGroup.from(raidal(circle,center,slices,0d,settings));
    }
    
    public static WidgetGroup raidalGroup(Circle circle, Vector2d center, int slices, double startAngle,
            BiConsumer<Integer,Button> settings) {
        return BasicWidgetGroup.from(raidal(circle,center,slices,startAngle,settings));
    }
    
    public static WidgetGroup raidalGroup(Circle circle, double centerX, double centerY, int slices, double startAngle,
            BiConsumer<Integer,Button> settings) {
        return BasicWidgetGroup.from(raidal(circle,centerX,centerY,slices,startAngle,settings));
    }
    
    public static Button[] raidal(Circle circle, Vector2d center, int slices, double startAngle,
            BiConsumer<Integer,Button> settings) {
        return raidal(circle,center.x,center.y,slices,startAngle,settings);
    }
    
    public static Button[] raidal(Circle circle, double centerX, double centerY, int slices, double startAngle,
            BiConsumer<Integer,Button> settings) {
        Button[] buttons = new Button[Math.max(slices,1)];
        CircleSlice[] sliceArray = circle.slice(Math.max(slices,1),startAngle);
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
        this.hoverLines = new ArrayList<>();
        if(Objects.nonNull(shape)) addWidget(shape);
        if(Objects.nonNull(text)) addWidget(text);
        if(Objects.nonNull(hover)) hover.setParent(this);
    }
    
    @Override public Button copy() {
        Button copy = new Button(Objects.nonNull(this.shape) ? this.shape.copy() : null,
                                 Objects.nonNull(this.text) ? this.text.copy() : null,
                                 Objects.nonNull(this.hover) ? this.hover.copy() : null);
        copy.addHoverLines(this.hoverLines);
        for(Widget widget : this.widgets)
            if(widget!=this.shape && widget!=this.text) copy.addWidget(widget.copy());
        copy.clickFunc = this.clickFunc;
        copy.height = this.height;
        copy.scaleX = this.scaleX;
        copy.scaleY = this.scaleY;
        copy.width = this.width;
        copy.x = this.x;
        copy.y = this.y;
        return copy;
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
    
    public void setHoverText(Consumer<TextWidget> text) {
        if(this.hover instanceof TextWidget) text.accept((TextWidget)this.hover);
        else if(this.hover instanceof WidgetGroup)
            for(Widget widget : ((WidgetGroup)this.hover).widgets)
                if(widget instanceof TextWidget) text.accept((TextWidget)widget);
    }
    
    @Override public boolean shouldDrawHovered() {
        return Objects.nonNull(this.hover) || !this.hoverLines.isEmpty();
    }
}
