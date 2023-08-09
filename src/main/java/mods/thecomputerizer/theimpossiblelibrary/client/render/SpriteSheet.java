package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class SpriteSheet extends PNG {

    private final int frames;
    private final long millisPerFrame;
    private int curFrame;
    private boolean startedRendering;
    private long prevMillis;
    private long milliCounter;

    /**
     * Preload a PNG animated sprite sheet with parameters for use in the Renderer class.
     */
    protected SpriteSheet(ResourceLocation location, Map<String, Object> parameters) throws IOException {
        super(location, parameters);
        int fps = getParameterAs("fps",20, Integer.class);
        this.millisPerFrame = (int)(1000f/((float)fps));
        this.milliCounter = 0;
        IResource resource = getResource();
        try(InputStream resourceStream = resource.getInputStream()) {
            PngSizeInfo size = new PngSizeInfo(resource.toString(),resourceStream);
            this.frames = size.height / size.width;
        }
        this.curFrame = 0;
        Constants.testLog("INITIALIZED SPRITE WITH {} FRAMES AND FPS MILLIS OF {}",this.frames,this.millisPerFrame);
    }

    private IResource getResource() throws IOException {
        return Minecraft.getInstance().getResourceManager().getResource(this.source);
    }

    private void renderTick() {
        long curMillis = Util.getMillis();
        long elapsedMillis = curMillis-this.prevMillis;
        this.prevMillis = curMillis;
        this.milliCounter+=elapsedMillis;
        while(this.milliCounter>=this.millisPerFrame) {
            this.curFrame++;
            if(this.curFrame>=this.frames) this.curFrame = 0;
            this.milliCounter-=this.millisPerFrame;
        }
    }

    @Override
    public void render(MatrixStack matrix, MainWindow res) {
        if(this.startedRendering) renderTick();
        if(canRender()) {
            if(!this.startedRendering) {
                this.prevMillis = Util.getMillis();
                this.startedRendering = true;
            }
            preRender(matrix);
            int resX = res.getGuiScaledWidth();
            int resY = res.getGuiScaledHeight();
            Minecraft.getInstance().getTextureManager().bind(this.source);
            matrix.scale(scaleX(resX,resY),scaleY(), 1f);
            drawSprite(matrix.last().pose(),posX(resX,resY),posY(resY),resX,resY);
            postRender(matrix);
        }
    }

    private void drawSprite(Matrix4f matrix, int x, int y, int width, int height) {
        float framePercent = 1f/((float)this.frames);
        float vMin = (float)Math.max(0d,this.curFrame*framePercent);
        float vMax = (float)Math.min(1d,(this.curFrame+1)*framePercent);
        BufferBuilder buffer = Tessellator.getInstance().getBuilder();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.vertex(matrix,x, y + height, 0).uv(0,vMax).endVertex();
        buffer.vertex(matrix,x + width, y + height, 0).uv(1f, vMax).endVertex();
        buffer.vertex(matrix,x + width, y, 0).uv(1f, vMin).endVertex();
        buffer.vertex(matrix,x, y, 0).uv(0, vMin).endVertex();
        Tessellator.getInstance().end();
    }

    @Override
    public void stop() {
        super.stop();
        this.startedRendering = false;
    }
}