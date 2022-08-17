package mods.thecomputerizer.theimpossiblelibrary.client.visual;

import com.sksamuel.scrimage.nio.AnimatedGif;
import com.sksamuel.scrimage.nio.AnimatedGifReader;
import com.sksamuel.scrimage.nio.ImageSource;
import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;
import mods.thecomputerizer.theimpossiblelibrary.util.CustomTick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class GIF {

    private final AnimatedGif status;
    private final long delay;
    private final ResourceLocation source;
    private final int glTextureId;
    private int frame;
    private long milliStatus;
    private String horizontal;
    private String vertical;
    private int x;
    private int y;
    private float scaleX;
    private float scaleY;
    private long millis;

    public GIF(ResourceLocation location) throws IOException {
        this.status = AnimatedGifReader.read(ImageSource.of(Minecraft.getMinecraft().mcDefaultResourcePack.getInputStream(location)));
        this.delay = this.status.getDelay(0).toMillis();
        this.frame = 0;
        this.milliStatus = 0;
        this.scaleX = 1f;
        this.scaleY = 1f;
        if(!CustomTick.isRegistered(this.delay)) CustomTick.addCustomTickEvent(this.delay);
        this.source = location;
        this.glTextureId = TextureUtil.glGenTextures();
    }

    public boolean incrementFrame() {
        this.frame++;
        this.milliStatus+=this.delay;
        if(millis<=0) return this.frame<this.status.getFrameCount();
        else if (this.frame>=this.status.getFrameCount()) this.frame = 0;
        return this.milliStatus<=this.millis;
    }

    public void loadCurrentFrame(boolean blur, boolean clamp) {
        try {
            TextureUtil.deleteTexture(this.glTextureId);
            TextureUtil.uploadTextureImageAllocate(this.glTextureId,this.status.getFrame(this.frame).awt(),blur,clamp);
            GlStateManager.bindTexture(this.glTextureId);
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Something went wrong when trying to render a frame of the gif at location "+this.source,e);
        }
    }

    public long getDelay() {
        return delay;
    }

    public int getScaledWidth() {
        return (int)(this.status.getDimensions().width*this.scaleX);
    }

    public int getScaledHeight() {
        return (int)(this.status.getDimensions().height*this.scaleY);
    }

    public float getWidthRatio() {
        return (float) getScaledWidth()/(float) getScaledHeight();
    }

    public float getHeightRatio() {
        return (float) getScaledHeight()/(float) getScaledWidth();
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public String getHorizontal() {
        return this.horizontal;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getVertical() {
        return this.vertical;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }
}
