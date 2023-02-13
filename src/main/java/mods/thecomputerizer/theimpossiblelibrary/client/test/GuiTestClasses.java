package mods.thecomputerizer.theimpossiblelibrary.client.test;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialButton;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialElement;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialProgressBar;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;
import javax.vecmath.Point2i;
import javax.vecmath.Point4i;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GuiTestClasses {

    public static TestOtherGui createTestOtherGui() {
        return new TestOtherGui(null);
    }

    public static TestRadialGui createTestRadialGui(boolean alt) {
        return new TestRadialGui(null,alt);
    }

    private static RadialElement getFirstRadialElement(TestRadialGui parent, int[] loc) {
        return new RadialElement(parent,getTestIcon("mt2",false,true),null, getTestRadialProgressBar(),
                loc[0],loc[1],loc[2],loc[3],(int)(((float)loc[2])/2f),null,
                Arrays.asList("Center Hover test1","Center Hover test2"),100f,0.1f, getTestRadialButtons());
    }

    private static RadialElement getSecondRadialElement(TestRadialGui parent, int[] loc) {
        return new RadialElement(parent,getTestIcon("mt2",false,true),null, getTestRadialProgressBar(),
                loc[0],loc[1],loc[2],loc[3],(int)(((float)loc[2])/2f),null,
                Arrays.asList("Center Hover test1","Center Hover test2"),100f,0.1f, getTestAltRadialButtons());
    }

    private static RadialProgressBar getTestRadialProgressBar() {
        return new RadialProgressBar(0,25,0f,100,
                (screen, button, mousePos) -> button.setProgress(button.mousePosToProgress(mousePos)));
    }

    private static List<RadialButton> getTestRadialButtons() {
        return Arrays.asList(new RadialButton(Arrays.asList("hover1-1","hover1-2"),
                        getTestIcon("log",false,false),getTestIcon("log",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(null)),
                new RadialButton(Arrays.asList("hover2-1","hover2-2"),
                        getTestIcon("play",false,false),getTestIcon("play",true,false),
                        0.25f, null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(null)),
                new RadialButton(Arrays.asList("hover3-1","hover3-2"),
                        getTestIcon("edit",false,false),getTestIcon("edit",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(createTestRadialGui(true))),
                new RadialButton(Arrays.asList("hover4-1","hover4-2"),
                        getTestIcon("reset",false,false),getTestIcon("reset",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(createTestRadialGui(true))));
    }

    private static List<RadialButton> getTestAltRadialButtons() {
        return Arrays.asList(new RadialButton(Arrays.asList("hover1-1","hover1-2"),
                        getTestIcon("main",false,false),getTestIcon("main",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(null)),
                new RadialButton(Arrays.asList("hover2-1","hover2-2"),
                        getTestIcon("transitions",false,false),getTestIcon("transitions",true,false),
                        0.25f, null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(null)),
                new RadialButton(Arrays.asList("hover3-1","hover3-2"),
                        getTestIcon("command",false,false),getTestIcon("command",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(createTestRadialGui(true))),
                new RadialButton(Arrays.asList("hover4-1","hover4-2"),
                        getTestIcon("toggle",false,false),getTestIcon("toggle",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(createTestRadialGui(true))));
    }

    private static ResourceLocation getTestIcon(String name, boolean hover, boolean center) {
        String base = "textures/gui_test/";
        if(center) return new ResourceLocation(Constants.MODID,base+"isotypes/"+name+".png");
        else {
            if(hover) return new ResourceLocation(Constants.MODID,base+"black_icons/"+name+".png");
            else return new ResourceLocation(Constants.MODID,base+"white_icons/"+name+".png");
        }
    }

    private static abstract class TestGui extends GuiScreen {

        protected final GuiScreen parent;
        protected Point2i center;

        public TestGui(GuiScreen parent) {
            this.parent = parent;
        }

        @Override
        public void setWorldAndResolution(@Nonnull Minecraft mc, int width, int height) {
            super.setWorldAndResolution(mc, width, height);
            this.center = new Point2i((int) (((float) this.width) / 2f), (int) (((float) this.height) / 2f));
        }

        @Override
        public void updateScreen() {
            super.updateScreen();
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            this.drawDefaultBackground();
            drawStuff(mouseX, mouseY, partialTicks);
            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        public abstract void drawStuff(int mouseX, int mouseY, float partialTicks);
    }

    private static class TestRadialGui extends TestGui {
        protected RadialElement circleButton;
        private final boolean alt;
        private int buttonIDCounter;

        public TestRadialGui(GuiScreen parent, boolean alt) {
            super(parent);
            this.alt = alt;
        }

        private int[] setCenterCircle() {
            return new int[]{this.center.x, this.center.y, 50, 100};
        }

        @Override
        public void initGui() {
            super.initGui();
            if (this.alt) this.circleButton = getFirstRadialElement(this, setCenterCircle());
            else this.circleButton = getSecondRadialElement(this, setCenterCircle());
        }

        @Override
        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            circleButton.mousePressed(mouseX, mouseY, mouseButton);
        }

        @Override
        public void drawStuff(int mouseX, int mouseY, float partialTicks) {
            circleButton.render(this.zLevel, mouseX, mouseY);
        }
    }

    private static class TestOtherGui extends TestGui {

        private GuiTextField textBox;
        private int copyPastaLines;
        private int scrollPos;

        public TestOtherGui(GuiScreen parent) {
            super(parent);
            this.copyPastaLines = 0;
            this.scrollPos = 0;
        }

        @Override
        public void handleMouseInput() throws IOException {
            super.handleMouseInput();
            int scroll = Mouse.getEventDWheel();
            if(scroll!=0) {
                if(scroll<1) {
                    if (this.copyPastaLines-10>this.scrollPos) this.scrollPos++;
                } else if(this.scrollPos>0) this.scrollPos--;
            }
        }

        @Override
        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            this.textBox.mouseClicked(mouseX, mouseY, mouseButton);
        }

        @Override
        protected void keyTyped(char typedChar, int keyCode) throws  IOException {
            super.keyTyped(typedChar, keyCode);
            this.textBox.textboxKeyTyped(typedChar, keyCode);
        }

        @Override
        public void initGui() {
            super.initGui();
            this.textBox = new GuiTextField(69,this.fontRenderer,(this.width/4)*3,0,
                    (this.width/4)-2,16);
            this.textBox.setMaxStringLength(32500);
            this.textBox.setText("");
            this.copyPastaLines = GuiUtil.howManyLinesWillThisBe(this.fontRenderer,"Here's the thing. You said a " +
                    "\"jackdaw is a crow.\" Is it in the same family? Yes. No one's arguing that. As someone who is a " +
                    "scientist who studies crows, I am telling you, specifically, in science, no one calls jackdaws " +
                    "crows. If you want to be \"specific\" like you said, then you shouldn't either. They're not the " +
                    "same thing. If you're saying \"crow family\" you're referring to the taxonomic grouping of " +
                    "Corvidae, which includes things from nutcrackers to blue jays to ravens. So your reasoning for " +
                    "calling a jackdaw a crow is because random people \"call the black ones crows?\" Let's get grackles " +
                    "and blackbirds in there, then, too. Also, calling someone a human or an ape? It's not one or the " +
                    "other, that's not how taxonomy works. They're both. A jackdaw is a jackdaw and a member of the crow " +
                    "family. But that's not what you said. You said a jackdaw is a crow, which is not true unless you're " +
                    "okay with calling all members of the crow family crows, which means you'd call blue jays, ravens, " +
                    "and other birds crows, too. Which you said you don't. It's okay to just admit you're wrong, you know?",
                    0,this.width/2,0,4);
            LogUtil.logInternal(Level.INFO,"pasta lines {}",this.copyPastaLines);
        }

        @Override
        public void drawStuff(int mouseX, int mouseY, float partialTicks) {
            /*
            GuiUtil.drawBoxWithOutline(this.center,100, 50, new Point4i(0,0,0,255),
                    new Point4i(255,255,255,255), 1f, this.zLevel);
            GuiUtil.drawColoredRing(this.center,new Point2i(199,200),new Point4i(255,255,255,255),
                    360,this.zLevel);
             */
            GuiUtil.drawMultiLineString(this.fontRenderer,"Here's the thing. You said a " +
                            "\"jackdaw is a crow.\" Is it in the same family? Yes. No one's arguing that. As someone who is a " +
                            "scientist who studies crows, I am telling you, specifically, in science, no one calls jackdaws " +
                            "crows. If you want to be \"specific\" like you said, then you shouldn't either. They're not the " +
                            "same thing. If you're saying \"crow family\" you're referring to the taxonomic grouping of " +
                            "Corvidae, which includes things from nutcrackers to blue jays to ravens. So your reasoning for " +
                            "calling a jackdaw a crow is because random people \"call the black ones crows?\" Let's get grackles " +
                            "and blackbirds in there, then, too. Also, calling someone a human or an ape? It's not one or the " +
                            "other, that's not how taxonomy works. They're both. A jackdaw is a jackdaw and a member of the crow " +
                            "family. But that's not what you said. You said a jackdaw is a crow, which is not true unless you're " +
                            "okay with calling all members of the crow family crows, which means you'd call blue jays, ravens, " +
                            "and other birds crows, too. Which you said you don't. It's okay to just admit you're wrong, you know?",
                    0,this.width/2,0,this.fontRenderer.FONT_HEIGHT+2,10,this.scrollPos,GuiUtil.WHITE);
            if(this.copyPastaLines>10) drawScrollBar();
            this.textBox.drawTextBox();
        }

        private void drawScrollBar() {
            float indices = this.copyPastaLines-10;
            int perIndex = (int)(((float)this.height)/indices);
            int top = perIndex*this.scrollPos;
            int x = this.width-1;
            Point2i start = new Point2i(x, top);
            Point2i end = new Point2i(x, Math.min(this.height,top+perIndex));
            GuiUtil.drawLine(start,end,new Point4i(200,200,255,255), 2f, this.zLevel);
        }
    }
}
