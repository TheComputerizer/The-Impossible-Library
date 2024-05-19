package mods.thecomputerizer.theimpossiblelibrary.api.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.ShapeWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderShape;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

public class TestScreen extends ScreenAPI {
    
    @SuppressWarnings("DataFlowIssue")
    public TestScreen() {
        super(TextHelper.getLiteral("test"),TILRef.getClientSubAPI(ClientAPI::getMinecraft).getWindow());
        addWidget(new ShapeWidget(new RenderShape(new Circle(Y.getDirection(),0.8d,
                RenderHelper.getContext().getHeightRatio()),WHITE.withAlpha(0.5f)),0d,0d));
    }
}