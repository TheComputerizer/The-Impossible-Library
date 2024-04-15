package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.SimpleWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetRadial;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;

import javax.annotation.Nullable;
import java.util.Objects;

public class ScreenHelper {

    public static @Nullable ScreenHelperAPI getAPI() {
        return TILRef.getClientSubAPI(ClientAPI::getScreenHelper);
    }

    public static @Nullable ButtonAPI<?> getButton(WidgetShape shape) {
        return getButton(null,shape);
    }
    
    public static <B> @Nullable ButtonAPI<?> getButton(@Nullable B button, WidgetShape shape) {
        ScreenHelperAPI api = getAPI();
        return Objects.nonNull(api) ? api.getButton(button,shape) : null;
    }
    
    public static @Nullable RadialButtonAPI<?> getRadialButton(WidgetRadial shape) {
        ScreenHelperAPI api = getAPI();
        return Objects.nonNull(api) ? api.getRadialButton(shape) : null;
    }
    
    public static @Nullable WidgetCollectionAPI<WidgetAPI<?>> getRadialCollection(WidgetRadial reference, int slices) {
        WidgetCollectionAPI<WidgetAPI<?>> collection = getWidgetCollection();
        if(Objects.isNull(collection)) return null;
        double angleDif = 360d/(double)slices;
        for(int i=0;i<slices;i++) {
            WidgetRadial shape = new WidgetRadial(reference);
            shape.setAnglesDegrees(angleDif*i,angleDif*(i+1));
            shape.setResolution(shape.getResolution()/slices);
            collection.addWidget(getRadialButton(shape));
        }
        return collection;
    }
    
    public static <S> @Nullable ScreenAPI<?> getScreen(S screen) {
        ScreenHelperAPI api = getAPI();
        return Objects.nonNull(api) ? api.getScreen(screen) : null;
    }

    public static @Nullable WidgetScrollableAPI<WidgetAPI<?>> getScrollable(WidgetBox scrollBar) {
        ScreenHelperAPI api = getAPI();
        return Objects.nonNull(api) ? api.getScrollable(scrollBar) : null;
    }

    public static @Nullable WidgetScrollableAPI<WidgetAPI<?>> getScrollableBoxes(
            WidgetBox reference, WidgetBox scrollBar, int rows, int cols) {
        return null; //TODO implement this
    }

    public static SimpleWidget getSimple(WidgetShape shape) {
        return new SimpleWidget(shape);
    }
    
    public static <T> @Nullable TextWidgetAPI<?> getTextWidget(T text, WidgetShape shape) {
        ScreenHelperAPI api = getAPI();
        return Objects.nonNull(api) ? api.getTextWidget(text,shape) : null;
    }

    public static @Nullable WidgetCollectionAPI<WidgetAPI<?>> getWidgetCollection() {
        ScreenHelperAPI api = getAPI();
        return Objects.nonNull(api) ? api.getWidgetCollection() : null;
    }
}
