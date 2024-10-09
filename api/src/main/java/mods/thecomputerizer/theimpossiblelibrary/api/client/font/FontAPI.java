package mods.thecomputerizer.theimpossiblelibrary.api.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.MutableWrapped;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class FontAPI<F> extends MutableWrapped<F> {
    
    private final Function<MinecraftAPI<?>,F> fontGetter;
    
    protected FontAPI(Function<MinecraftAPI<?>,F> fontGetter) {
        this.fontGetter = fontGetter;
    }

    public abstract void draw(RenderAPI renderer, String text, float x, float y, int color);
    public abstract void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color);
    @IndirectCallers public abstract int getCharWidth(char c);
    public abstract int getFontHeight();
    public abstract int getStringWidth(String str);
    
    @Override public F getWrapped() {
        if(Objects.isNull(this.wrapped)) this.wrapped = this.fontGetter.apply(ClientHelper.getMinecraft());
        return this.wrapped;
    }
    
    public void renderToolTip(RenderAPI renderer, Collection<TextAPI<?>> lines, int x, int y, int width,
            int height, int maxWidth) {
        renderer.setFont(getWrapped());
        List<?> components = lines.stream().map(text -> text.getAsComponent()).collect(Collectors.toList());
        TILRef.getClientHandles().renderToolTip(renderer,components,x,y,width,height,maxWidth);
    }
    
    public String trimStringTo(String str, Number width) {
        return trimStringTo(str,width.intValue(),false);
    }
    
    public abstract String trimStringTo(String str, int width, boolean withReset);
}