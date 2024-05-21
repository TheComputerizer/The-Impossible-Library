package mods.thecomputerizer.theimpossiblelibrary.api.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.BasicWidgetGroup;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.Button;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.ShapeWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.Widget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.WidgetGroup;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.FuzzBall;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderShape;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector3d;

import java.util.Collection;
import java.util.Collections;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.BLACK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

public class TestScreen extends ScreenAPI {
    
    private final FuzzBall fuzz;
    private boolean coloredFuzz;
    
    @SuppressWarnings("DataFlowIssue")
    public TestScreen() {
        super(TextHelper.getLiteral("test"),TILRef.getClientSubAPI(ClientAPI::getMinecraft).getWindow());
        double heightRatio = RenderHelper.getCurrentHeightRatio();
        Circle circle = ShapeHelper.circle(Y,0.65d,0.35d, heightRatio);
        WidgetGroup radialMenu = Button.raidalGroup(circle,0d,0d,7,(i,button) -> {
            button.getShape().setColor(BLACK);
            Widget texture = ShapeWidget.from(ShapeHelper.square(Y,0.25d,heightRatio),TILRef.res("test/logo.png"));
            Vector3d pos = button.getShape().getCenterForGroup(VectorHelper.zero3D());
            texture.setX(pos.x);
            texture.setY(pos.y);
            button.addWidget(texture);
            button.addHoverLine(TextHelper.getLiteral("test hover "+i));
            button.setClickFunc(b -> this.coloredFuzz = !this.coloredFuzz);
            button.setHover(BasicWidgetGroup.from(ShapeWidget.of(RenderShape.from(
                    button.getShape().getShape().getWrapped()),0d,0d),texture));
        });
        addWidget(radialMenu);
        addWidget(ShapeWidget.outlineFrom(circle.getScaled(6d/13d),10f));
        addWidget(ShapeWidget.outlineFrom(circle.getScaled(1.1d),10f));
        this.fuzz = ShapeHelper.square(Y,2d,1d).makeFuzzBall(
                500,1000,5f,5f,() -> new ColorCache(
                        this.coloredFuzz ? RandomHelper.randomFloat(0.6f,1f) : 0f,
                        this.coloredFuzz ? 0.5f : 0f,
                        this.coloredFuzz ? 0.5f : 0f,
                        this.coloredFuzz ? 1f : RandomHelper.randomFloat(0.75f,1f)
                ));
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        this.fuzz.draw2D(ctx);
        super.draw(ctx,center,mouseX,mouseY);
    }
    
    @Override public Collection<TextAPI<?>> getHoverLines(double x, double y) {
        Collection<TextAPI<?>> lines = super.getHoverLines(x,y);
        if(lines.isEmpty()) return Collections.singleton(TextHelper.getLiteral(String.format("(%1$2f, %2$2f)",x,y)));
        return lines;
    }
}