package mods.thecomputerizer.theimpossiblelibrary.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
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
        Pushes a generic texture for rendering via a ResourceLocation
        Make sure to pass in a valid ResourceLocation since that does not get checked here
     */
    public static void bufferSquareTexture(Vector2f center, float radius, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GlStateManager.color(1f, 1f, 1f, 1f);
        GuiScreen.drawModalRectWithCustomSizedTexture((int) (center.x - radius / 2f), (int) (center.y - radius / 2f),
                radius, radius, (int) radius, (int) radius, radius, radius);
        GlStateManager.color(1f, 1f, 1f, 1f);
    }
}
