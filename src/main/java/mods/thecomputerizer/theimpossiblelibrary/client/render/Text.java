package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.MainWindow;
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
    public void render(MatrixStack matrix, MainWindow res) {
        if(canRender()) GuiUtil.drawMultiLineTitle(matrix,res,this.text, this.subtext,
                getParameterAs("centered", true, Boolean.class),
                getParameterAs("x", -1, Integer.class),
                getParameterAs("y", -1, Integer.class),
                getParameterAs("scale_x", 1f, Float.class),
                getParameterAs("scale_y", 1f, Float.class),
                getParameterAs("subtitle_scale", 0.75f, Float.class),
                getParameterAs("title_color", "red", String.class),
                getParameterAs("subtitle_color", "white", String.class),getOpacity(),getOpacity(),
                Minecraft.getInstance().font.lineHeight+Minecraft.getInstance().font.lineHeight/2);
    }
}