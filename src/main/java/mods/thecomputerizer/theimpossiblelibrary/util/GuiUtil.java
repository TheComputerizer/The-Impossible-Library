package mods.thecomputerizer.theimpossiblelibrary.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector2f;

public class GuiUtil {

    /*
        Pushes a colored section to a BufferBuilder for rendering given 4 corner positions stored in vectors
        zLevel can be accessed from any class that extends GuiScreen
     */
    public static void setBuffer(BufferBuilder builder, Vector2f pos1In, Vector2f pos2In, Vector2f pos1Out, Vector2f pos2Out,
                                 float zLevel, int r, int g, int b, int a) {
        builder.pos(pos1Out.x, pos1Out.y, zLevel).color(r, g, b, a).endVertex();
        builder.pos(pos1In.x, pos1In.y, zLevel).color(r, g, b, a).endVertex();
        builder.pos(pos2In.x, pos2In.y, zLevel).color(r, g, b, a).endVertex();
        builder.pos(pos2Out.x, pos2Out.y, zLevel).color(r, g, b, a).endVertex();
    }

    /*
        Pushes a generic texture to a BufferBuilder for rendering via a ResourceLocation
        Make sure to pass in a valid ResourceLocation since that does not get checked here
        zLevel can be accessed from any class that extends GuiScreen
     */
    public static void bufferSquareTexture(BufferBuilder builder, Vector2f center, float radius, float zLevel, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        Vector2f ver1 = new Vector2f(center.x - (radius / 2f), center.y + (radius / 2f));
        Vector2f ver2 = new Vector2f(center.x + (radius / 2f), center.y + (radius / 2f));
        Vector2f ver3 = new Vector2f(center.x + (radius / 2f), center.y - (radius / 2f));
        Vector2f ver4 = new Vector2f(center.x - (radius / 2f), center.y - (radius / 2f));
        builder.pos(ver1.x, ver1.y, zLevel).tex(1d, 2d).endVertex();
        builder.pos(ver2.x, ver2.y, zLevel).tex(2d, 2d).endVertex();
        builder.pos(ver3.x, ver3.y, zLevel).tex(2d, 1d).endVertex();
        builder.pos(ver4.x, ver4.y, zLevel).tex(1d, 1d).endVertex();
    }
}
