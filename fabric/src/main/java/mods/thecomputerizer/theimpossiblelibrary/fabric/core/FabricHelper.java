package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR;
import static net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.CLIENT_STARTED;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STARTED;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STARTING;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STOPPED;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STOPPING;
import static net.minecraft.network.chat.Style.EMPTY;
import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;

public class FabricHelper {
    
    static final int DEFAULT_BACKGROUND_COLOR = 0xF0100010;
    static final int DEFAULT_BORDER_COLOR_START = 0x505000FF;
    static final int DEFAULT_BORDER_COLOR_END = (DEFAULT_BORDER_COLOR_START&0xFEFEFE)>>1|DEFAULT_BORDER_COLOR_START&0xFF000000;
    
    private static volatile Object CURRENT_SERVER;
    
    public static void finalizeEntrypoints(CommonEntryPoint entryPoint) {
        if(CoreAPI.getInstance().getSide().isClient()) {
            CLIENT_STARTED.register(client -> {
                entryPoint.onInterModProcess();
                entryPoint.onLoadComplete();
            });
        } else {
            SERVER_STARTING.register(server -> { //Is this the right event??
                entryPoint.onInterModProcess();
                entryPoint.onLoadComplete();
            });
        }
        SERVER_STARTING.register(server -> {
            entryPoint.onServerAboutToStart();
            entryPoint.onServerStarting();
        });
        SERVER_STARTED.register(server -> entryPoint.onServerStarted());
        SERVER_STOPPING.register(server -> entryPoint.onServerStopping());
        SERVER_STOPPED.register(server -> entryPoint.onServerStopped());
    }
    
    public static Object getCurrentServer() {
        return CURRENT_SERVER;
    }
    
    public static String getMappingNamespace() {
        return FabricLauncherBase.getLauncher().getTargetNamespace();
    }
    
    @IndirectCallers
    public static Field getObfField(String srgName, Class<?> owner, Object instance) {
        return getObfField(srgName,owner,instance.getClass());
    }
    
    public static Field getObfField(String srgName, Class<?> owner, Class<?> instanceClass) {
        return ReflectionHelper.getField(owner,getObfFieldName(srgName,owner,instanceClass));
    }
    
    @IndirectCallers
    public static String getObfFieldName(String srgName, Class<?> owner, Object instance) {
        return getObfFieldName(srgName,owner,instance.getClass());
    }
    
    public static String getObfFieldName(String srgName, Class<?> owner, Class<?> instanceClass) {
        return getObfFieldName(srgName,owner.getName(),Type.getDescriptor(instanceClass));
    }
    
    public static String getObfFieldName(String srgName, String owner, String desc) {
        return getResolver().mapFieldName(getMappingNamespace(),owner,srgName,desc);
    }
    
    public static MappingResolver getResolver() {
        return FabricLoader.getInstance().getMappingResolver();
    }
    
    public static void registerServerHooks() {
        SERVER_STARTING.register(server -> CURRENT_SERVER = server);
        SERVER_STOPPED.register(server -> CURRENT_SERVER = null);
    }
    
    public static void renderTooltip(PoseStack matrix, List<? extends FormattedText> textLines, int mouseX, int mouseY,
            int screenWidth, int screenHeight, int maxTextWidth, Font font) {
        renderTooltip(matrix,textLines,mouseX,mouseY,screenWidth,screenHeight,maxTextWidth,DEFAULT_BACKGROUND_COLOR,
                      DEFAULT_BORDER_COLOR_START,DEFAULT_BORDER_COLOR_END,font);
    }
    
    /**
     * The equivalent of GuiUtils#drawHoveringText from forge
     */
    @SuppressWarnings("deprecation")
    public static void renderTooltip(PoseStack matrix, List<? extends FormattedText> textLines, int mouseX, int mouseY,
            int screenWidth, int screenHeight, int maxTextWidth, int backgroundColor, int borderColorStart,
            int borderColorEnd, Font font) {
        if(!textLines.isEmpty()) {
            RenderSystem.disableRescaleNormal();
            RenderSystem.disableDepthTest();
            int tooltipTextWidth = 0;
            for(FormattedText textLine : textLines) {
                int textLineWidth = font.width(textLine);
                if(textLineWidth>tooltipTextWidth) tooltipTextWidth = textLineWidth;
            }
            boolean needsWrap = false;
            int titleLinesCount = 1;
            int tooltipX = mouseX + 12;
            if(tooltipX+tooltipTextWidth+4>screenWidth) {
                tooltipX = mouseX-16-tooltipTextWidth;
                if(tooltipX<4) { // if the tooltip doesn't fit on the screen
                    if(mouseX>screenWidth/2) tooltipTextWidth = mouseX-12-8;
                    else tooltipTextWidth = screenWidth-16-mouseX;
                    needsWrap = true;
                }
            }
            if(maxTextWidth>0 && tooltipTextWidth>maxTextWidth) {
                tooltipTextWidth = maxTextWidth;
                needsWrap = true;
            }
            if (needsWrap) {
                int wrappedTooltipWidth = 0;
                List<FormattedText> wrappedTextLines = new ArrayList<>();
                for(int i=0; i<textLines.size(); i++) {
                    FormattedText textLine = textLines.get(i);
                    List<FormattedText> wrappedLine = font.getSplitter().splitLines(textLine,tooltipTextWidth,EMPTY);
                    if(i==0) titleLinesCount = wrappedLine.size();
                    for(FormattedText line : wrappedLine) {
                        int lineWidth = font.width(line);
                        if(lineWidth>wrappedTooltipWidth) wrappedTooltipWidth = lineWidth;
                        wrappedTextLines.add(line);
                    }
                }
                tooltipTextWidth = wrappedTooltipWidth;
                textLines = wrappedTextLines;
                if(mouseX>screenWidth/2) tooltipX = mouseX-16-tooltipTextWidth;
                else tooltipX = mouseX+12;
            }
            int tooltipY = mouseY - 12;
            int tooltipHeight = 8;
            if(textLines.size()>1) {
                tooltipHeight+=(textLines.size()-1)*10;
                if(textLines.size()>titleLinesCount) tooltipHeight+=2; // gap between title lines and next lines
            }
            if(tooltipY<4) tooltipY = 4;
            else if (tooltipY+tooltipHeight+4>screenHeight) tooltipY = screenHeight-tooltipHeight-4;
            final int zLevel = 400;
            matrix.pushPose();
            Matrix4f mat = matrix.last().pose();
            drawGradientRect(mat,zLevel,tooltipX-3,tooltipY-4,tooltipX+tooltipTextWidth+3,
                             tooltipY-3,backgroundColor,backgroundColor);
            drawGradientRect(mat,zLevel,tooltipX-3,tooltipY+tooltipHeight+3,tooltipX+tooltipTextWidth+3,
                             tooltipY+tooltipHeight+4,backgroundColor,backgroundColor);
            drawGradientRect(mat,zLevel,tooltipX-3,tooltipY-3,tooltipX+tooltipTextWidth+3,
                             tooltipY+tooltipHeight+3,backgroundColor,backgroundColor);
            drawGradientRect(mat,zLevel,tooltipX-4,tooltipY-3,tooltipX-3,tooltipY+tooltipHeight+3,
                             backgroundColor,backgroundColor);
            drawGradientRect(mat,zLevel,tooltipX+tooltipTextWidth+3,tooltipY-3,
                             tooltipX+tooltipTextWidth+4,tooltipY+tooltipHeight+3,backgroundColor,
                             backgroundColor);
            drawGradientRect(mat,zLevel,tooltipX-3,tooltipY-3+1,tooltipX-3+1,
                             tooltipY+tooltipHeight+3-1,borderColorStart,borderColorEnd);
            drawGradientRect(mat,zLevel,tooltipX+tooltipTextWidth+2,tooltipY-3+1,
                             tooltipX+tooltipTextWidth+3,tooltipY+tooltipHeight+3-1,borderColorStart,
                             borderColorEnd);
            drawGradientRect(mat,zLevel,tooltipX-3,tooltipY-3,tooltipX+tooltipTextWidth+3,
                             tooltipY-3+1,borderColorStart,borderColorStart);
            drawGradientRect(mat,zLevel,tooltipX-3,tooltipY+tooltipHeight+2,tooltipX+tooltipTextWidth+3,
                             tooltipY+tooltipHeight+3,borderColorEnd,borderColorEnd);
            BufferSource renderType = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            matrix.translate(0d,0d,zLevel);
            for(int lineNumber=0; lineNumber<textLines.size(); lineNumber++) {
                FormattedText line = textLines.get(lineNumber);
                if(Objects.nonNull(line))
                    font.drawInBatch(Language.getInstance().getVisualOrder(line),(float)tooltipX,(float)tooltipY,-1,true,mat,renderType,false,0,15728880);
                if(lineNumber+1==titleLinesCount) tooltipY+=2;
                tooltipY += 10;
            }
            renderType.endBatch();
            matrix.popPose();
            RenderSystem.enableDepthTest();
            RenderSystem.enableRescaleNormal();
        }
    }
    
    /**
     * The equivalent of GuiUtils#drawGradientRect from forge
     */
    @SuppressWarnings("deprecation")
    public static void drawGradientRect(Matrix4f mat, int zLevel, int left, int top, int right, int bottom,
            int startColor, int endColor) {
        float startAlpha = (float)(startColor>>24&255)/255f;
        float startRed = (float)(startColor>>16&255)/255f;
        float startGreen = (float)(startColor>>8&255)/255f;
        float startBlue = (float)(startColor&255)/255f;
        float endAlpha = (float)(endColor>>24&255)/255f;
        float endRed = (float)(endColor>>16&255)/255f;
        float endGreen = (float)(endColor>>8&255)/255f;
        float endBlue = (float)(endColor&255)/255f;
        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL_SMOOTH);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(GL_QUADS,POSITION_COLOR);
        buffer.vertex(mat,right,top,zLevel).color(startRed,startGreen,startBlue,startAlpha).endVertex();
        buffer.vertex(mat,left,top,zLevel).color(startRed,startGreen,startBlue,startAlpha).endVertex();
        buffer.vertex(mat,left,bottom,zLevel).color(endRed,endGreen,endBlue,endAlpha).endVertex();
        buffer.vertex(mat,right,bottom,zLevel).color(endRed,endGreen,endBlue,endAlpha).endVertex();
        tesselator.end();
        RenderSystem.shadeModel(GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }
}