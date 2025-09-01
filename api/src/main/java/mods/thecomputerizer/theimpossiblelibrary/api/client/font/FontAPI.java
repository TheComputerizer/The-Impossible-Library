package mods.thecomputerizer.theimpossiblelibrary.api.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.MutableWrapped;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class FontAPI<F> extends MutableWrapped<F> {
    
    private final Function<MinecraftAPI<?>,F> fontGetter;
    
    protected FontAPI(Function<MinecraftAPI<?>,F> fontGetter) {
        this.fontGetter = fontGetter;
    }

    public abstract void draw(RenderAPI renderer, String text, float x, float y, int color);
    public abstract void drawInBatch(Object text, float x, float y, int color, boolean shadow, Object matrix,
            Object source, boolean transparent, int bgColor, int light);
    public abstract void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color);
    @IndirectCallers public abstract int getCharWidth(char c);
    public abstract int getFontHeight();
    public abstract int getStringWidth(@Nullable String str);
    protected final int getStringWidth(@Nullable String str, BiFunction<F,String,Integer> widthGetter) {
        return Objects.nonNull(str) ? widthGetter.apply(getWrapped(),str) : 0;
    }
    
    @Override public F getWrapped() {
        if(Objects.isNull(this.wrapped)) {
            MinecraftAPI<?> mc = ClientHelper.getMinecraft();
            if(Objects.nonNull(mc)) this.wrapped = this.fontGetter.apply(mc);
        }
        return this.wrapped;
    }
    
    public void renderToolTip(RenderAPI renderer, Collection<TextAPI<?>> lines, int x, int y, int width,
            int height, int maxWidth) {
        if(Objects.isNull(renderer)) return;
        renderer.setFont(getWrapped());
        TILRef.getClientHandles().renderToolTip(renderer,unwrapTooltipComponents(lines),x,y,width,height,maxWidth);
    }
    
    public String trimStringTo(String str, Number width) {
        return trimStringTo(str,width.intValue(),false);
    }
    
    public abstract String trimStringTo(String str, int width, boolean withReset);
    
    public <T> List<T> unwrapTooltipComponents(Collection<TextAPI<?>> lines) {
        return GenericUtils.cast(lines.stream().map(text -> text.getAsComponent())
                                         .collect(Collectors.toList()));
    }
}