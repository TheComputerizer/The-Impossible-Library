package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.Minecraft;

import java.util.*;

/**
 * Used to simulate a title command but with more versatility
 */
public class Text extends Renderable {

    private final List<?> potentialText;
    private final List<?> potentialSubtext;
    private String text = "";
    private String subtext = "";
    public Text(Map<String, Object> parameters) {
        super(parameters);
        this.potentialText = getParameterAs("titles",new ArrayList<>(), List.class);
        this.potentialSubtext = getParameterAs("subtitles",new ArrayList<>(),List.class);
    }

    @Override
    public void initializeTimers() {
        super.initializeTimers();
        Random random = new Random();
        if(this.potentialText.size()>0)
            this.text = this.potentialText.get(random.nextInt(this.potentialText.size())).toString();
        if(this.potentialSubtext.size()>0)
            this.subtext = this.potentialSubtext.get(random.nextInt(this.potentialSubtext.size())).toString();
    }

    @Override
    public int posX(float resolutionX, float resolutionY) {
        float scaleX  = getParameterAs("scale_x", 1f, Float.class)*5f;
        String alignment = getParameterAs("horizontal_alignment","center",String.class);
        int xOffset = alignment.matches("center") ? (int) ((resolutionX/2f)-(resolutionX*(scaleX/2f))) :
                alignment.matches("right") ? (int) (resolutionX-(resolutionX*(scaleX/2f))) : 0;
        return (int)((xOffset*(1/scaleX))+getParameterAs("x",0, Integer.class));
    }

    @Override
    public int posY(float resolutionY) {
        float scaleY  = getParameterAs("scale_y", 1f, Float.class)*5f;
        String alignment = getParameterAs("vertical_alignment","center",String.class);
        int yOffset = alignment.matches("center") ? (int) (int) ((resolutionY/2f)-(resolutionY*(scaleY/2f))) :
                alignment.matches("top") ? 0 : (int) (resolutionY-(resolutionY*(scaleY/2f)));
        return (int)((yOffset*(1/scaleY))+getParameterAs("y",0, Integer.class));
    }

    @Override
    public void render(PoseStack matrix, Window res) {
        if(canRender()) GuiUtil.drawMultiLineTitle(matrix,res,this.text, this.subtext,
                getParameterAs("centered", true, Boolean.class),
                posX(res.getGuiScaledWidth(),res.getGuiScaledHeight()),
                posY(res.getGuiScaledWidth()),
                getParameterAs("scale_x", 1f, Float.class)*5f,
                getParameterAs("scale_y", 1f, Float.class)*5f,
                getParameterAs("subtitle_scale", 0.75f, Float.class),
                getParameterAs("title_color", "red", String.class),
                getParameterAs("subtitle_color", "white", String.class),getOpacity(),getOpacity(),
                Minecraft.getInstance().font.lineHeight+Minecraft.getInstance().font.lineHeight/2);
    }
}