package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class WidgetScrollableAPI<W extends WidgetAPI<?>> extends WidgetCollectionAPI<W> {

    @Setter @Getter private WidgetBox scrollBar;
    private int index;

    protected WidgetScrollableAPI(WidgetBox scrollBar) {
        this.scrollBar = scrollBar;
    }

    protected void drawScrollBar(RenderAPI renderer, float offset) {
        this.scrollBar.draw(renderer,offset);
    }

    @Override
    public @Nullable WidgetAPI<?> getParent() {
        return null;
    }

    @Override
    public @Nullable ScreenAPI<?> getScreen() {
        return null;
    }

    @Override
    public void removeWidget(int x, int y) {
        super.removeWidget(x,y);
        int size = this.widgets.size();
        if(this.index>=size) this.index = size>0 ? size-1 : 0;
    }

    @Override
    public void removeWidgets(int x, int y) {
        WidgetAPI<?> widget = getWidget(x,y);
        while(Objects.nonNull(widget)) {
            this.widgets.remove(widget);
            widget = getWidget(x,y);
        }
        int size = this.widgets.size();
        if(this.index>=size) this.index = size>0 ? size-1 : 0;
    }

    @Override
    public W set(W widget) {
        return null;
    }

    @Override
    public boolean scrollable() {
        return true;
    }

    @Override
    public void scrollUp(int mouseX, int mouseY) {
        if(this.index<this.widgets.size()-1) this.index++;
    }

    @Override
    public void scrollDown(int mouseX, int mouseY) {
        if(this.index>0) this.index--;
    }
}