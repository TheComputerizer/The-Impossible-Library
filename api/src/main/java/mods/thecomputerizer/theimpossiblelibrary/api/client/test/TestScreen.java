package mods.thecomputerizer.theimpossiblelibrary.api.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.ShapeWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderShape;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderShapeOutline;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

public class TestScreen extends ScreenAPI {
    
    @SuppressWarnings("DataFlowIssue")
    public TestScreen() {
        super(TextHelper.getLiteral("test"),TILRef.getClientSubAPI(ClientAPI::getMinecraft).getWindow());
        RenderContext ctx = RenderHelper.getContext();
        RenderShape circle = RenderShape.circle(ctx,0.8d,WHITE.withAlpha(0.5f));
        addWidget(new ShapeWidget(circle,0d,0d));
        double scale = 1.1d;
        for(float f=15f;f>0f;f-=1f) {
            addRing(circle,scale,f);
            scale+=0.1d;
        }
        RenderShape square = RenderShape.square(ctx,0.75d,new TextureWrapper()
                .setTexture(TILRef.res("test/logo.png")).setAlpha(0.4f));
        addWidget(new ShapeWidget(square,0d,0d));
    }
    
    void addRing(RenderShape reference, double scale, float lineWidth) {
        RenderShape copy = new RenderShape(reference.getWrapped().getScaled(scale),reference.getColor());
        addWidget(new ShapeWidget(RenderShapeOutline.of(copy,lineWidth),0d,0d));
    }
}