package mods.thecomputerizer.theimpossiblelibrary.api.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.Button;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.ShapeWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.TextWidget;

public class TestButton extends Button {
    
    public TestButton(TextWidget text) {
        super(ShapeWidget.texturedPlane(0.2d,0.1d,1d,null), text);
    }
}