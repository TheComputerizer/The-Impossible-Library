package mods.thecomputerizer.theimpossiblelibrary.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import javax.vecmath.*;

public class FontUtil {
    @SuppressWarnings("UnnecessaryUnicodeEscape")
    public static final String ASCII_CHARS = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df" +
            "\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000" +
            "\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstu" +
            "vwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4" +
            "\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1" +
            "\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593" +
            "\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500" +
            "\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553" +
            "\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6" +
            "\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7" +
            "\u221a\u207f\u00b2\u25a0\u0000";


    /**
     * Render a single character, basically the same as FontRenderer does it
     */
    public static void renderChar(char c, Tuple2f pos, FontRenderer font, Tuple4i colors) {
        if(c==160 || c==' ') return;
        int i = ASCII_CHARS.indexOf(c);
        if(i!=1 && !font.getUnicodeFlag())
            renderAsciiChar(i,pos,font.charWidth[i],font,colors);
        else renderUnicodeChar(c,pos,font,colors);
    }

    /**
     * Buffers the texture used by the character input and returns its UV values stored as UMin, Umax, VMin, and Vmax
     * respectively
     */
    public static Point4f bufferAndGetCharUV(char c, FontRenderer font) {
        bufferCharTex(c, font);
        return getCharUV(c, font);
    }

    /**
     * Returns the UV values of a single character stored as UMin, Umax, VMin, and Vmax respectively
     */
    public static void bufferCharTex(char c, FontRenderer font) {
        if(c==160 || c==' ') return;
        int i = ASCII_CHARS.indexOf(c);
        if(i!=1 && !font.getUnicodeFlag()) bufferAsciiTex(font);
        else bufferUnicodeTex(c, font);
    }

    /**
     * Buffers the texture used by the character input
     */
    public static Point4f getCharUV(char c, FontRenderer font) {
        if(c==160 || c==' ') return new Point4f(0f,1f,0f,1f);
        int i = ASCII_CHARS.indexOf(c);
        return i!=1 && !font.getUnicodeFlag() ? getAsciiUV(i,(float)font.charWidth[i]-0.01f) :
                getUnicodeUV(c,font.glyphWidth[c]&255);
    }

    private static void bufferAsciiTex(FontRenderer font) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(font.locationFontTexture);
    }

    private static Point4f getAsciiUV(int c, float width) {
        float u = (float)(c%16*8);
        float v = (float)(c/16*8);
        return new Point4f(u/128f,(u+width-1f)/128f,v/128f,(v+7.99f)/128f);
    }
    private static void renderAsciiChar(int c, Tuple2f pos, float width, FontRenderer font, Tuple4i colors) {
        bufferAsciiTex(font);
        drawAsciiChar(false,pos,getAsciiUV(c,width),width - 0.01F,colors);
    }

    private static void drawAsciiChar(boolean triangles, Tuple2f pos, Tuple4f uv, float width, Tuple4i colors) {
        if(triangles) {
            GlStateManager.glBegin(GL11.GL_TRIANGLE_STRIP);
            GuiUtil.tupleColor(colors);
            GlStateManager.glTexCoord2f(uv.x, uv.z);
            GlStateManager.glVertex3f(pos.x, pos.y, 0.0F);
            GlStateManager.glTexCoord2f(uv.x, uv.w);
            GlStateManager.glVertex3f(pos.x, pos.y + 7.99F, 0.0F);
            GlStateManager.glTexCoord2f(uv.y, uv.z);
            GlStateManager.glVertex3f(pos.x + width - 1.0F, pos.y, 0.0F);
            GlStateManager.glTexCoord2f(uv.y, uv.w);
            GlStateManager.glVertex3f(pos.x + width - 1.0F, pos.y + 7.99F, 0.0F);
            GlStateManager.glEnd();
        } else {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(pos.x, pos.y + 8d, 0.0D).tex(uv.x, uv.w).endVertex();
            bufferbuilder.pos(pos.x + 8d, pos.y + 8d, 0.0D).tex(uv.y, uv.w).endVertex();
            bufferbuilder.pos(pos.x + 8d, pos.y, 0.0D).tex(uv.y, uv.z).endVertex();
            bufferbuilder.pos(pos.x, pos.y, 0.0D).tex(uv.x, uv.z).endVertex();
            tessellator.draw();
        }
    }

    private static void bufferUnicodeTex(char c, FontRenderer font) {
        int i = font.glyphWidth[c] & 255;
        if (i == 0) return;
        font.loadGlyphTexture(c / 256);
    }

    private static void bufferUnicodeTexInner(char c, FontRenderer font) {
        font.loadGlyphTexture(c / 256);
    }

    private static Point4f getUnicodeUV(char c, int glyphWidth) {
        if(glyphWidth==0) return new Point4f(0f,1f,0f,1f);
        float u = (float)(c%16*16)+(float)(glyphWidth>>>4);
        float v = (float)((c&255)/16*16);
        float width = (float)((glyphWidth&15)+1)-(float)(glyphWidth>>>4)-0.02f;
        return new Point4f(u/256f,(u+width)/256f,v/256f,(v+15.98f)/256f);
    }

    private static void renderUnicodeChar(char c, Tuple2f pos, FontRenderer font, Tuple4i colors) {
        int i = font.glyphWidth[c] & 255;
        if (i == 0) return;
        bufferUnicodeTexInner(c, font);
        float width = (float)((i&15)+1)-(float)(i>>>4)-0.02f;
        drawUnicodeChar(true,pos,getUnicodeUV(c,i),width,colors);
    }

    private static void drawUnicodeChar(boolean triangles, Tuple2f pos, Tuple4f uv, float width, Tuple4i colors) {
        if(triangles) {
            GlStateManager.glBegin(GL11.GL_TRIANGLE_STRIP);
            GuiUtil.tupleColor(colors);
            GlStateManager.glTexCoord2f(uv.x, uv.z);
            GlStateManager.glVertex3f(pos.x, pos.y, 0.0F);
            GlStateManager.glTexCoord2f(uv.x, uv.w);
            GlStateManager.glVertex3f(pos.x, pos.y + 7.99F, 0.0F);
            GlStateManager.glTexCoord2f(uv.y, uv.z);
            GlStateManager.glVertex3f(pos.x + width / 2.0F, pos.y, 0.0F);
            GlStateManager.glTexCoord2f(uv.y, uv.w);
            GlStateManager.glVertex3f(pos.x + width / 2.0F, pos.y + 7.99F, 0.0F);
            GlStateManager.glEnd();
        } else {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(pos.x, pos.y + 8d, 0.0D).tex(uv.x, uv.w).endVertex();
            bufferbuilder.pos(pos.x + 8d, pos.y + 8d, 0.0D).tex(uv.y, uv.w).endVertex();
            bufferbuilder.pos(pos.x + 8d, pos.y, 0.0D).tex(uv.y, uv.z).endVertex();
            bufferbuilder.pos(pos.x, pos.y, 0.0D).tex(uv.x, uv.z).endVertex();
            tessellator.draw();
        }
    }
    

    /**
         Converts a TextFormatting object into a single color integer with an optional alpha value
     */
    public static int convertTextFormatting(TextFormatting format, int a) {
        int r,b,g;
        switch (format) {
            case DARK_RED: return GuiUtil.makeRGBAInt(170,0,0,a);
            case RED: return GuiUtil.makeRGBAInt(255,85,85,a);
            case GOLD: return GuiUtil.makeRGBAInt(255,170,0,a);
            case YELLOW: return GuiUtil.makeRGBAInt(255,255,85,a);
            case DARK_GREEN: return GuiUtil.makeRGBAInt(0,170,0,a);
            case GREEN: return GuiUtil.makeRGBAInt(85,255,85,a);
            case AQUA: return GuiUtil.makeRGBAInt(85,255,255,a);
            case DARK_AQUA: return GuiUtil.makeRGBAInt(0,170,170,a);
            case DARK_BLUE: return GuiUtil.makeRGBAInt(0,0,170,a);
            case BLUE: return GuiUtil.makeRGBAInt(85,85,255,a);
            case LIGHT_PURPLE: return GuiUtil.makeRGBAInt(255,85,255,a);
            case DARK_PURPLE: return GuiUtil.makeRGBAInt(170,0,170,a);
            case GRAY: return GuiUtil.makeRGBAInt(170,170,170,a);
            case DARK_GRAY: return GuiUtil.makeRGBAInt(85,85,85,a);
            case BLACK: return GuiUtil.makeRGBAInt(0,0,0,a);
            default: return GuiUtil.makeRGBAInt(255,255,255,a);
        }
    }
}
