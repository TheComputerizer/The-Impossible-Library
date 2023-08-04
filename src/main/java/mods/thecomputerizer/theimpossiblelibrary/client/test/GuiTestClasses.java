package mods.thecomputerizer.theimpossiblelibrary.client.test;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialButton;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialElement;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialProgressBar;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ClassEscapesDefinedScope")
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
                        Minecraft.getInstance().setScreen(null)),
                new RadialButton(Arrays.asList("hover2-1","hover2-2"),
                        getTestIcon("play",false,false),getTestIcon("play",true,false),
                        0.25f, null,(screen, button) ->
                        Minecraft.getInstance().setScreen(null)),
                new RadialButton(Arrays.asList("hover3-1","hover3-2"),
                        getTestIcon("edit",false,false),getTestIcon("edit",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getInstance().setScreen(createTestRadialGui(true))),
                new RadialButton(Arrays.asList("hover4-1","hover4-2"),
                        getTestIcon("reset",false,false),getTestIcon("reset",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getInstance().setScreen(createTestRadialGui(true))));
    }

    private static List<RadialButton> getTestAltRadialButtons() {
        return Arrays.asList(new RadialButton(Arrays.asList("hover1-1","hover1-2"),
                        getTestIcon("main",false,false),getTestIcon("main",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getInstance().setScreen(null)),
                new RadialButton(Arrays.asList("hover2-1","hover2-2"),
                        getTestIcon("transitions",false,false),getTestIcon("transitions",true,false),
                        0.25f, null,(screen, button) ->
                        Minecraft.getInstance().setScreen(null)),
                new RadialButton(Arrays.asList("hover3-1","hover3-2"),
                        getTestIcon("command",false,false),getTestIcon("command",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getInstance().setScreen(createTestRadialGui(true))),
                new RadialButton(Arrays.asList("hover4-1","hover4-2"),
                        getTestIcon("toggle",false,false),getTestIcon("toggle",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getInstance().setScreen(createTestRadialGui(true))));
    }

    private static ResourceLocation getTestIcon(String name, boolean hover, boolean center) {
        String base = "textures/gui_test/";
        if(center) return new ResourceLocation(Constants.MODID,base+"isotypes/"+name+".png");
        else {
            if(hover) return new ResourceLocation(Constants.MODID,base+"black_icons/"+name+".png");
            else return new ResourceLocation(Constants.MODID,base+"white_icons/"+name+".png");
        }
    }

    private static abstract class TestGui extends Screen {

        protected final Screen parent;
        protected Vector3f center;

        public TestGui(Screen parent) {
            super(Component.literal("test_gui"));
            this.parent = parent;
        }

        @Override
        public void init() {
            super.init();
            this.center = new Vector3f(((float) this.width) / 2f, ((float) this.height) / 2f,0);
        }

        @Override
        public void render(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
            this.renderBackground(matrix);
            drawStuff(matrix, mouseX, mouseY, partialTicks);
            super.render(matrix, mouseX, mouseY, partialTicks);
        }

        public abstract void drawStuff(PoseStack matrix, int mouseX, int mouseY, float partialTicks);
    }

    private static class TestRadialGui extends TestGui {
        protected RadialElement circleButton;
        private final boolean alt;
        private int buttonIDCounter;

        public TestRadialGui(Screen parent, boolean alt) {
            super(parent);
            this.alt = alt;
        }

        private int[] setCenterCircle() {
            return new int[]{(int)this.center.x(),(int)this.center.y(), 50, 100};
        }

        @Override
        public void init() {
            super.init();
            if (this.alt) this.circleButton = getFirstRadialElement(this, setCenterCircle());
            else this.circleButton = getSecondRadialElement(this, setCenterCircle());
        }

        @Override
        public boolean mouseClicked(double x, double y, int button) {
            circleButton.mousePressed((int) x, (int) y, button);
            return true;
        }

        @Override
        public void drawStuff(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
            circleButton.render(matrix, getBlitOffset(), mouseX, mouseY);
        }
    }

    private static class TestOtherGui extends TestGui {

        private EditBox textBox;
        private int copyPastaLines;
        private int scrollPos;

        public TestOtherGui(Screen parent) {
            super(parent);
            this.copyPastaLines = 0;
            this.scrollPos = 0;
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
            if(scroll!=0) {
                if(scroll<1) {
                    if (this.copyPastaLines-10>this.scrollPos) {
                        this.scrollPos++;
                        return true;
                    }
                } else if(this.scrollPos>0) {
                    this.scrollPos--;
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean mouseClicked(double x, double y, int button) {
            return this.textBox.mouseClicked(x, y, button);
        }

        @Override
        public boolean keyPressed(int keyCode, int x, int y) {
            return super.keyPressed(keyCode, x, y) || this.textBox.keyPressed(keyCode, x, y);
        }

        @Override
        public void init() {
            super.init();
            this.textBox = new EditBox(this.font,(this.width/4)*3,0,(this.width/4)-2,16,Component.literal("test_gui_text"));
            this.textBox.setMaxLength(32500);
            this.textBox.setValue("");
            this.copyPastaLines = GuiUtil.howManyLinesWillThisBe(this.font,"Here's the thing. You said a " +
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
                    0,this.width/2);
            LogUtil.logInternal(Level.INFO,"pasta lines {}",this.copyPastaLines);
            this.setInitialFocus(this.textBox);
        }

        @Override
        public void drawStuff(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
            GuiUtil.drawBoxWithOutline(this.center,100, 50, new Vector4f(0,0,0,255),
                    new Vector4f(255,255,255,255), 1f, this.getBlitOffset());
            GuiUtil.drawColoredRing(this.center,new Vector3f(199,200,0),new Vector4f(255,255,255,255),
                    360,this.getBlitOffset());
            GuiUtil.bufferSquareTexture(matrix,this.center,100f, BookEditScreen.BACKGROUND_LOCATION);
            GuiUtil.drawMultiLineString(matrix,this.font,"Here's the thing. You said a " +
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
                    0,this.width/2,0,this.font.lineHeight+2,10,this.scrollPos,GuiUtil.WHITE);
            if(this.copyPastaLines>10) drawScrollBar();
            this.textBox.render(matrix, mouseX, mouseY, partialTicks);
        }

        private void drawScrollBar() {
            float indices = this.copyPastaLines-10;
            int perIndex = (int)(((float)this.height)/indices);
            int top = perIndex*this.scrollPos;
            int x = this.width-1;
            Vector3f start = new Vector3f(x, top,0);
            Vector3f end = new Vector3f(x, Math.min(this.height,top+perIndex),0);
            GuiUtil.drawLine(start,end,new Vector4f(200,200,255,255), 2f, this.getBlitOffset());
        }
    }
}