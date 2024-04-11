package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.font;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.render.Render1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.Text1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Font1_16_5 implements FontAPI {

    private FontRenderer font;

    @Override
    public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        getFont().draw(getMatrix(renderer),text,x,y,color);
    }

    @Override
    public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        getFont().drawShadow(getMatrix(renderer),text,x,y,color);
    }

    private FontRenderer getFont() {
        if(Objects.isNull(this.font)) this.font = Minecraft.getInstance().font;
        return this.font;
    }

    private MatrixStack getMatrix(RenderAPI renderer) {
        return ((Render1_16_5)renderer).getMatrix();
    }

    @Override
    public int getCharWidth(char c) {
        return getStringWidth(""+c);
    }

    @Override
    public int getFontHeight() {
        return getFont().lineHeight;
    }

    @Override
    public int getStringWidth(String str) {
        return getFont().width(str);
    }

    @Override
    public void renderToolTip(RenderAPI renderer, List<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth) {
        List<ITextComponent> components = new ArrayList<>();
        for(TextAPI<?> text : lines) components.add(((Text1_16_5)text).getComponent());
        GuiUtils.drawHoveringText(getMatrix(renderer),components,x,y,width,height,maxWidth,getFont());
    }
}
