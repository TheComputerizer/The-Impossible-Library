package mods.thecomputerizer.theimpossiblelibrary.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.*;

public class GuiUtil {

    /*
        Pushes a colored section to a BufferBuilder for rendering given 4 corner positions stored in vectors
        zLevel can be accessed from any class that extends GuiScreen
     */
    public static void setBuffer(BufferBuilder builder, Point2i pos1In, Point2i pos2In, Point2i pos1Out, Point2i pos2Out,
                                 float zLevel, Point4i colors) {
        tupleColor(builder.pos(pos1Out.x, pos1Out.y, zLevel),colors).endVertex();
        tupleColor(builder.pos(pos1In.x, pos1In.y, zLevel),colors).endVertex();
        tupleColor(builder.pos(pos2In.x, pos2In.y, zLevel),colors).endVertex();
        tupleColor(builder.pos(pos2Out.x, pos2Out.y, zLevel),colors).endVertex();
    }

    /*
        Pushes a generic texture for rendering via a ResourceLocation
        Make sure to pass in a valid ResourceLocation since that does not get checked here
     */
    public static void bufferSquareTexture(Tuple2i center, float radius, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GlStateManager.color(1f, 1f, 1f, 1f);
        GuiScreen.drawModalRectWithCustomSizedTexture((int) (center.x - radius / 2f), (int) (center.y - radius / 2f),
                radius, radius, (int) radius, (int) radius, radius, radius);
        GlStateManager.color(1f, 1f, 1f, 1f);
    }

    /*
        Utilizes a tuple to set the color for a BufferBuilder
     */
    public static BufferBuilder tupleColor(BufferBuilder builder, Tuple4i colors) {
        return builder.color(colors.x, colors.y, colors.z, colors.w);
    }

    /*
        Reverses a color tuple which is generally used to set the hover color
     */
    public static Point4i reverseColors(Tuple4i colors) {
        return new Point4i(Math.abs(colors.x-255), Math.abs(colors.y-255), Math.abs(colors.z-255), colors.w);
    }

    /*
        Converts a color tuple into a single integer
     */
    public static int makeRGBAInt(Tuple4i colors) {
        return (colors.x<<24)+(colors.y<<16)+(colors.z<<8)+colors.w;
    }
}
