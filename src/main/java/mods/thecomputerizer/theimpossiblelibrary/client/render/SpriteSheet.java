package mods.thecomputerizer.theimpossiblelibrary.client.render;

import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SpriteSheet extends PNG {

    private TextureAtlasSprite texture;
    private boolean failedInit = false;

    /**
     * Preload a PNG animated sprite sheet with parameters for use in the Renderer class.
     */
    protected SpriteSheet(ResourceLocation location, Map<String, Object> parameters) throws IOException {
        super(location, parameters);
    }

    private void initSprite() {
        IResource resource = null;
        boolean closed = false;
        try {
            TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
            this.texture = map.registerSprite(this.source);
            resource = Minecraft.getMinecraft().getResourceManager().getResource(this.source);
            PngSizeInfo pngsizeinfo = PngSizeInfo.makeFromResource(resource);
            closed = true;
            resource = Minecraft.getMinecraft().getResourceManager().getResource(this.source);
            closed = false;
            boolean isAnimation = Objects.nonNull(resource.getMetadata("animation"));
            this.texture.loadSprite(pngsizeinfo, isAnimation);
            IOUtils.closeQuietly(resource);
            closed = true;
            resource = Minecraft.getMinecraft().getResourceManager().getResource(this.source);
            closed = false;
            this.texture.loadSpriteFrames(resource,map.getMipmapLevels()+1);
            this.texture.generateMipmaps(map.getMipmapLevels());
            if(this.texture.hasAnimationMetadata()) map.listAnimatedSprites.add(this.texture);
        } catch (Exception e) {
            LogUtil.logInternal(Level.ERROR,"Failed to initialize sprite for resource {}!",this.source,e);
            this.failedInit = true;
        } finally {
            if(Objects.nonNull(resource) && !closed)
                IOUtils.closeQuietly(resource);
        }
    }

    @Override
    public boolean canRender() {
        return !this.failedInit && super.canRender();
    }

    @Override
    public void render(ScaledResolution res) {
        if(canRender()) {
            if(Objects.isNull(this.texture)) initSprite();
            int resX = res.getScaledWidth();
            int resY = res.getScaledHeight();
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableAlpha();
            GlStateManager.color(1f, 1f, 1f, getOpacity());
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.source);
            GlStateManager.scale(scaleX(resX,resY), scaleY(), 1f);
            int x = posX(resX,resY);
            int y = posY(resY);
            GuiScreen.drawModalRectWithCustomSizedTexture(x,y,resX,resY,resX,resY,resX,resY);
            GlStateManager.color(1F, 1F, 1F, 1);
            GlStateManager.popMatrix();
        }
    }

    private void initRenderStates() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL11.GL_SPOT_DIRECTION,0.003921569f);
        GlStateManager.depthMask(false);
    }

    private void finishRenderStates() {

    }

    @Override
    public void stop() {
        super.stop();
        //Minecraft.getMinecraft().getTextureMapBlocks().mapRegisteredSprites.remove(this.source.toString());
    }
}
