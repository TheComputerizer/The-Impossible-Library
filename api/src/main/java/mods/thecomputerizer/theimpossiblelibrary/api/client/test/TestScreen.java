package mods.thecomputerizer.theimpossiblelibrary.api.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.BasicTypeableWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.BasicWidgetGroup;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.Button;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.ShapeWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.TextWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.Widget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.WidgetGroup;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.WidgetList;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderShape;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

@SuppressWarnings({"SameParameterValue","unused"})
public class TestScreen extends ScreenAPI {
    
    private final TextWidget clicked;
    private boolean coloredFuzz;
    
    public TestScreen(int guiScale) {
        super(TextHelper.getLiteral("test"),ClientHelper.getWindow(),guiScale);
        addFuzz(5,10,1f,1f);
        //addRadialMenu(RenderHelper.getCurrentHeightRatio(),5);
        this.clicked = TextWidget.literal("0",-0.75d,0.75d).setColor(BLUE);
        addScrollableMenu(100,1.8d,1.8d);
        addWidget(this.clicked);
        addWidget(BasicTypeableWidget.literal("Text Box!",RED,0d,0.95d));
    }
    
    private void addFuzz(int minCount, int maxCount, float minWidth, float maxWidth) {
        addWidget(ShapeWidget.fuzz(ShapeHelper.square(Y,2d,1d),minCount,maxCount,minWidth,maxWidth,
                                   () -> new ColorCache(
                                           this.coloredFuzz ? RandomHelper.randomFloat(0.6f,1f) : 0f,
                                           this.coloredFuzz ? 0.5f : 0f,
                                           this.coloredFuzz ? 0.5f : 0f,
                                           this.coloredFuzz ? 1f : RandomHelper.randomFloat(0.75f,1f)
                                   )));
    }
    
    
    private void addRadialMenu(double heightRatio, int slices) {
        Circle circle = ShapeHelper.circle(Y,0.65d,0.35d,heightRatio);
        WidgetGroup radialMenu = Button.radialGroup(circle, 0d, 0d, slices, 0d, (i,button) -> {
            button.getShape().setColor(BLACK);
            Widget texture = ShapeWidget.from(ShapeHelper.square(Y,0.25d,heightRatio),TILRef.res("test/logo.png"));
            Vector3d pos = button.getShape().getCenterForGroup(VectorHelper.zero3D());
            texture.setX(pos.x);
            texture.setY(pos.y);
            button.addWidget(texture);
            button.addHoverLine(TextHelper.getLiteral("test hover "+i));
            button.setClickFunc(b -> this.coloredFuzz = !this.coloredFuzz);
            button.setHover(BasicWidgetGroup.from(ShapeWidget.of(RenderShape.from(
                    button.getShape().getWrapped().getWrapped()),0d,0d),texture));
        });
        addWidget(radialMenu);
        addWidget(ShapeWidget.outlineFrom(circle.getScaled(6d/13d),10f));
        addWidget(ShapeWidget.outlineFrom(circle.getScaled(1.1d),10f));
    }
    
    private void addScrollableMenu(int size, double width, double height) {
        WidgetList list = WidgetList.from(Button.colored(BLACK,TextHelper.getLiteral("template")),0d,0d,width,height,0.05d);
        Plane shape = (Plane)((Button)list.getElementTemplate()).getShape().getWrapped().getWrapped();
        double scaleRatio = (((double)this.guiScale)+1d)/2d;
        shape.setScales(scaleRatio,scaleRatio);
        for(int i=0;i<size;i++) {
            list.addWidgetFromTemplate((template,index) -> {
                Button button = (Button)template;
                String indexStr = index==3 ? "33333333333333333333333333333333333333" : String.valueOf(index);
                String literally = "element "+indexStr;
                button.setText(literally);
                button.setHoverText(text -> text.setText(literally));
                button.addHoverLine(TextHelper.getLiteral("hover "+indexStr));
                button.setClickFunc(b -> this.clicked.setText(indexStr));
            });
        }
        addWidget(ShapeWidget.from(ShapeHelper.square(Y,1.8d,1d),LIGHT_PURPLE.withAlpha(0.5f)));
        addWidget(list);
        addWidget(ShapeWidget.outlineFrom(ShapeHelper.square(Y,1.8d,1d),WHITE,4f));
    }
}