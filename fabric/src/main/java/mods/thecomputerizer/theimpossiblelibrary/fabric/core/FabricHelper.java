package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.FormattedText;
import org.lwjgl.opengl.GL11;
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
import static org.lwjgl.opengl.GL12.GL_RESCALE_NORMAL;

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
    
    @IndirectCallers
    public static String getCurrentNamespace() {
        return getResolver().getCurrentRuntimeNamespace();
    }
    
    public static Field getObfField(String namespace, String name, Class<?> owner, Object instance) {
        return getObfField(namespace,name,owner,instance.getClass());
    }
    
    public static Field getObfField(String namespace, String srgName, Class<?> owner, Class<?> instanceClass) {
        return ReflectionHelper.getField(owner,getObfFieldName(namespace,srgName,owner,instanceClass));
    }
    
    @IndirectCallers
    public static String getObfFieldName(String namespace, String srgName, Class<?> owner, Object instance) {
        return getObfFieldName(namespace,srgName,owner,instance.getClass());
    }
    
    public static String getObfFieldName(String namespace, String name, Class<?> owner, Class<?> instanceClass) {
        return getObfFieldName(namespace,name,owner.getName(),Type.getDescriptor(instanceClass));
    }
    
    public static String getObfFieldName(String namespace, String name, String owner, String desc) {
        return getResolver().mapFieldName(namespace,owner,name,desc);
    }
    
    public static MappingResolver getResolver() {
        return FabricLoader.getInstance().getMappingResolver();
    }
    
    @IndirectCallers
    public static String getTargetNamespace() {
        return FabricLauncherBase.getLauncher().getTargetNamespace();
    }
    
    public static boolean isJava8() {
        return System.getProperty("java.version").startsWith("1.");
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
    public static void renderTooltip(PoseStack matrix, List<? extends FormattedText> textLines, int mouseX, int mouseY,
            int screenWidth, int screenHeight, int maxTextWidth, int backgroundColor, int borderColorStart,
            int borderColorEnd, Font font) {
        boolean java8 = isJava8();
        RenderAPI renderer = ClientHelper.getRenderer();
        if(!textLines.isEmpty() && Objects.nonNull(renderer)) {
            if(java8) GL11.glDisable(GL_RESCALE_NORMAL); //Only used to compile
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
            if(needsWrap) {
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
            Object mat = matrix.last().pose();
            Object buffer = renderer.getBufferBuilder();
            if(!java8) buffer =renderer.beginBuffer(buffer,GL_QUADS,POSITION_COLOR);
            drawGradientRect(java8,renderer,buffer,mat,zLevel,tooltipX-3,tooltipY-4,tooltipX+tooltipTextWidth+3,
                             tooltipY-3,backgroundColor,backgroundColor);
            drawGradientRect(java8,renderer,buffer,mat,zLevel,tooltipX-3,tooltipY+tooltipHeight+3,
                             tooltipX+tooltipTextWidth+3,tooltipY+tooltipHeight+4,backgroundColor,
                             backgroundColor);
            drawGradientRect(java8,renderer,buffer,mat,zLevel,tooltipX-3,tooltipY-3,tooltipX+tooltipTextWidth+3,
                             tooltipY+tooltipHeight+3,backgroundColor,backgroundColor);
            drawGradientRect(java8,renderer,buffer,mat,zLevel,tooltipX-4,tooltipY-3,tooltipX-3,
                             tooltipY+tooltipHeight+3,backgroundColor,backgroundColor);
            drawGradientRect(java8,renderer,buffer,mat,zLevel,tooltipX+tooltipTextWidth+3,tooltipY-3,
                             tooltipX+tooltipTextWidth+4,tooltipY+tooltipHeight+3,backgroundColor,
                             backgroundColor);
            drawGradientRect(java8,renderer,buffer,mat,zLevel,tooltipX-3,tooltipY-3+1,tooltipX-3+1,
                             tooltipY+tooltipHeight+3-1,borderColorStart,borderColorEnd);
            drawGradientRect(java8,renderer,buffer,mat,zLevel,tooltipX+tooltipTextWidth+2,tooltipY-3+1,
                             tooltipX+tooltipTextWidth+3,tooltipY+tooltipHeight+3-1,borderColorStart,
                             borderColorEnd);
            drawGradientRect(java8,renderer,buffer,mat,zLevel,tooltipX-3,tooltipY-3,tooltipX+tooltipTextWidth+3,
                             tooltipY-3+1,borderColorStart,borderColorStart);
            drawGradientRect(java8,renderer,buffer,mat,zLevel,tooltipX-3,tooltipY+tooltipHeight+2,
                             tooltipX+tooltipTextWidth+3,tooltipY+tooltipHeight+3,borderColorEnd,
                             borderColorEnd);
            if(!java8) {
                RenderSystem.enableDepthTest();
                renderer.disableTexture();
                renderer.enableBlend();
                renderer.defaultBlendFunc();
                renderer.endBuffer();
                renderer.disableBlend();
                renderer.enableTexture();
            }
            Object renderType = renderer.renderSourceImmediate();
            matrix.translate(0d,0d,zLevel);
            for(int lineNumber=0; lineNumber<textLines.size(); lineNumber++) {
                FormattedText line = textLines.get(lineNumber);
                FontAPI<?> api = ClientHelper.getFont();
                if(Objects.nonNull(line) && Objects.nonNull(api))
                    api.drawInBatch(line,tooltipX,tooltipY,-1,true,
                                    mat,renderType,false,0,15728880);
                if(lineNumber+1==titleLinesCount) tooltipY+=2;
                tooltipY += 10;
            }
            renderer.endBatch(renderType);
            matrix.popPose();
            if(java8) GL11.glEnable(GL_RESCALE_NORMAL); //Only used to compile
        }
    }
    
    /**
     * The equivalent of GuiUtils#drawGradientRect from forge
     */
    public static void drawGradientRect(boolean java8, RenderAPI renderer, Object buffer, Object mat, int zLevel,
            int left, int top, int right, int bottom, int startColor, int endColor) {
        if(Objects.isNull(renderer)) return;
        float startAlpha = (float)(startColor>>24&255)/255f;
        float startRed = (float)(startColor>>16&255)/255f;
        float startGreen = (float)(startColor>>8&255)/255f;
        float startBlue = (float)(startColor&255)/255f;
        float endAlpha = (float)(endColor>>24&255)/255f;
        float endRed = (float)(endColor>>16&255)/255f;
        float endGreen = (float)(endColor>>8&255)/255f;
        float endBlue = (float)(endColor&255)/255f;
        if(java8) {
            RenderSystem.enableDepthTest();
            renderer.disableTexture();
            renderer.enableBlend();
            renderer.defaultBlendFunc();
            GL11.glShadeModel(GL_SMOOTH); //Only used to compile
            buffer =renderer.beginBuffer(buffer,GL_QUADS,POSITION_COLOR);
        }
        drawVertex(renderer,buffer,mat,right,top,zLevel,startRed,startGreen,startBlue,startAlpha);
        drawVertex(renderer,buffer,mat,left,top,zLevel,startRed,startGreen,startBlue,startAlpha);
        drawVertex(renderer,buffer,mat,right,bottom,zLevel,endRed,endGreen,endBlue,endAlpha);
        drawVertex(renderer,buffer,mat,left,bottom,zLevel,endRed,endGreen,endBlue,endAlpha);
        if(java8) {
            renderer.endBuffer();
            GL11.glShadeModel(GL_FLAT);
            renderer.disableBlend();
            renderer.enableTexture();
        }
    }
    
    private static void drawVertex(RenderAPI renderer, Object buffer, Object mat, float x, float y, float z,
            float r, float g,float b, float a) {
        renderer.endVertex(renderer.vertexColor(renderer.vertexWithMatrix(buffer,mat,x,y,z),r,g,b,a));
    }
}