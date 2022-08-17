package mods.thecomputerizer.theimpossiblelibrary.client.visual;

import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;
import mods.thecomputerizer.theimpossiblelibrary.util.CustomTick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.ByteBufferSeekableByteChannel;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;

public class MP4 {

    private final FrameGrab frameGrab;
    private final long milliDelay;
    private final ResourceLocation source;
    private final int glTextureId;
    private Picture curFrame;
    private long milliStatus;
    private String horizontal;
    private String vertical;
    private int x;
    private int y;
    private float scaleX;
    private float scaleY;
    private long millis;

    public MP4(ResourceLocation location) throws IOException, JCodecException {
        this.frameGrab = FrameGrab.createFrameGrab(ByteBufferSeekableByteChannel.readFromByteBuffer(fromInputStream(Minecraft.getMinecraft().mcDefaultResourcePack.getInputStream(location))));
        this.milliDelay = (long) (1000d/((double)this.frameGrab.getVideoTrack().getMeta().getTotalFrames()/this.frameGrab.getVideoTrack().getMeta().getTotalDuration()));
        this.curFrame = this.frameGrab.getNativeFrame();
        this.milliStatus = 0;
        this.scaleX = 1f;
        this.scaleY = 1f;
        if(!CustomTick.isRegistered(this.milliDelay)) CustomTick.addCustomTickEvent(this.milliDelay);
        this.source = location;
        this.glTextureId = TextureUtil.glGenTextures();
    }

    private ByteBuffer fromInputStream(InputStream stream) throws IOException {
        return NIOUtils.fetchFromChannel(Channels.newChannel(stream),stream.available());
    }

    public boolean incrementFrame() {
        try {
            this.curFrame = this.frameGrab.getNativeFrame();
            this.milliStatus += this.milliDelay;
            if (millis <= 0) return this.curFrame != null;
            else if (this.curFrame == null) {
                this.frameGrab.getVideoTrack().seek(0d);
                this.curFrame = this.frameGrab.getNativeFrame();
            }
            return this.milliStatus <= this.millis;
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Could not increment frames for video file at "+this.source,e);
        }
        return false;
    }

    public void loadCurrentFrame(boolean blur, boolean clamp) {
        try {
            TextureUtil.deleteTexture(this.glTextureId);
            TextureUtil.uploadTextureImageAllocate(this.glTextureId, AWTUtil.toBufferedImage(this.curFrame),blur,clamp);
            GlStateManager.bindTexture(this.glTextureId);
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Something went wrong when trying to render a frame of the gif at location "+this.source,e);
        }
    }

    public long getDelay() {
        return this.milliDelay;
    }

    public int getScaledWidth() {
        return (int)(this.frameGrab.getMediaInfo().getDim().getWidth()*this.scaleX);
    }

    public int getScaledHeight() {
        return (int)(this.frameGrab.getMediaInfo().getDim().getHeight()*this.scaleY);
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
