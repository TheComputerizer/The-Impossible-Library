package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.platform.PngInfo;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.apache.commons.compress.utils.IOUtils;

import java.io.IOException;
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
        this.millisPerFrame = (long)(1000f/((float)fps));
        this.milliCounter = 0;
        Resource resource = getResource();
        PngInfo size = new PngInfo(resource.toString(),resource.getInputStream());
        this.frames = size.height/size.width;
        this.curFrame = 0;
        IOUtils.closeQuietly(resource);
        this.startedRendering = false;
        Constants.testLog("INITIALIZED SPRITE WITH {} FRAMES AND FPS MILLIS OF {}",this.frames,this.millisPerFrame);
    }

    private Resource getResource() throws IOException {
        return Minecraft.getInstance().getResourceManager().getResource(this.source);
    }

    private void renderTick() {
        long curMillis = Util.getMillis();
        long elapsedMillis = curMillis-this.prevMillis;
        this.prevMillis = curMillis;
        Constants.testLog("IT HAS BEEN {} MILLISECONDS SINCE THE LAST RENDER TICK",elapsedMillis);
        this.milliCounter+=elapsedMillis;
        while(this.milliCounter>=this.millisPerFrame) {
            this.curFrame++;
            if(this.curFrame>=this.frames) this.curFrame = 0;
            this.milliCounter-=this.millisPerFrame;
        }
    }

    @Override
    public void render(PoseStack matrix, Window res) {
        if(this.startedRendering) renderTick();
        if(canRender()) {
            if(!this.startedRendering) {
                this.prevMillis = Util.getMillis();
                this.startedRendering = true;
            }
            preRender(matrix);
            int resX = res.getGuiScaledWidth();
            int resY = res.getGuiScaledHeight();
            matrix.scale(scaleX(resX,resY),scaleY(), 1f);
            drawSprite(matrix.last().pose(),posX(resX,resY),posY(resY),resX,resY);
            postRender(matrix);
        }
    }

    private void drawSprite(Matrix4f matrix, int x, int y, int width, int height) {
        float framePercent = 1f/((float)this.frames);
        float vMin = (float)Math.max(0d,this.curFrame*framePercent);
        float vMax = (float)Math.min(1d,(this.curFrame+1)*framePercent);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(matrix,x, y + height, 0).uv(0,vMax).endVertex();
        buffer.vertex(matrix,x + width, y + height, 0).uv(1f, vMax).endVertex();
        buffer.vertex(matrix,x + width, y, 0).uv(1f, vMin).endVertex();
        buffer.vertex(matrix,x, y, 0).uv(0, vMin).endVertex();
        Tesselator.getInstance().end();
    }

    @Override
    public void stop() {
        super.stop();
        this.startedRendering = false;
    }
}
