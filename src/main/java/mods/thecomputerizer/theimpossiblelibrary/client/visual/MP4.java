package mods.thecomputerizer.theimpossiblelibrary.client.visual;

import com.zakgof.velvetvideo.IDemuxer;
import com.zakgof.velvetvideo.IVelvetVideoLib;
import com.zakgof.velvetvideo.IVideoDecoderStream;
import com.zakgof.velvetvideo.IVideoFrame;
import com.zakgof.velvetvideo.impl.VelvetVideoLib;
import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;
import mods.thecomputerizer.theimpossiblelibrary.common.Files;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class MP4 {
    private final ResourceLocation source;
    private final int glTextureId;
    private long milliStatus;
    private long milliCounter;
    private String horizontal;
    private String vertical;
    private int x;
    private int y;
    private float scaleX;
    private float scaleY;
    private long millis;
    public boolean isFinished;
    private final IVideoDecoderStream videoStream;
    private final File intermediary;
    private IVideoFrame curFrame;

    public MP4(ResourceLocation location) throws IOException {
        this.source = location;
        this.intermediary = new File(Constants.DATA_DIRECTORY,location.getResourceDomain() + "/" + location.getResourcePath());
        if(!this.intermediary.exists()) Files.generateNestedFile(this.intermediary);
        FileUtils.copyInputStreamToFile(Minecraft.getMinecraft().mcDefaultResourcePack.getInputStream(location), this.intermediary);
        this.videoStream = getVideo(this.intermediary);
        this.curFrame = this.videoStream.nextFrame();
        this.glTextureId = TextureUtil.glGenTextures();
        this.intermediary.deleteOnExit();
    }

    private IVideoDecoderStream getVideo(File file) {
        IVelvetVideoLib lib = VelvetVideoLib.getInstance();
        IDemuxer demuxer = lib.demuxer(file);
        return demuxer.videoStream(0);
    }

    public boolean checkMilli(long millis) {
        boolean status = true;
        for(int i=0;i<millis;i++) {
            if (this.milliCounter <= 0 && status) status = incrementFrame();
            if (this.milliCounter>0) {
                this.milliCounter--;
                this.milliStatus++;
            }
        }
        return status;
    }

    private boolean incrementFrame() {
        boolean pass = false;
        try {
            this.curFrame = this.videoStream.nextFrame();
            if (millis <= 0) pass = this.curFrame!=null;
            else if (this.curFrame==null) {
                this.videoStream.seekNano(0);
                this.curFrame = this.videoStream.nextFrame();
            }
            if (this.curFrame!=null) this.milliCounter = (long) ((float)this.curFrame.nanoduration()/1000000f);
            pass = this.milliStatus <= this.millis;
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Could not increment frames for video file at "+this.source,e);
        }
        return pass;
    }

    public void loadCurrentFrame(boolean blur, boolean clamp) {
        try {
            TextureUtil.deleteTexture(this.glTextureId);
            TextureUtil.uploadTextureImageAllocate(this.glTextureId, this.curFrame.image(),blur,clamp);
            GlStateManager.bindTexture(this.glTextureId);
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Something went wrong when trying to render a frame of the gif at location "+this.source,e);
        }
    }

    public int getScaledWidth() {
        if(this.curFrame!=null) return (int)(this.curFrame.image().getWidth()*this.scaleX);
        return 0;
    }

    public int getScaledHeight() {
        if(this.curFrame!=null) return (int)(this.curFrame.image().getHeight()*this.scaleY);
        return 0;
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

    public void cleanup() {
        if(!this.intermediary.delete())
            TheImpossibleLibrary.logWarning("Could not remove intermediary file! File will be removed upon exit if it still exists",null);
    }
}
