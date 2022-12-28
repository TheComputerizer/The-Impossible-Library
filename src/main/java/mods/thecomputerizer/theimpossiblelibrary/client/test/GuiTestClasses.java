package mods.thecomputerizer.theimpossiblelibrary.client.test;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialButton;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialElement;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialProgressBar;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;


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
            super(MutableComponent.create(new LiteralContents("test_gui")));
            this.parent = parent;
        }

        @Override
        public void init() {
            super.init();
            this.center = new Vector3f((int) (((float) this.width) / 2f), (int) (((float) this.height) / 2f),0);
        }

        @Override
        public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
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
            return new int[]{(int) this.center.x(), (int) this.center.y(), 50, 100};
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

        public TestOtherGui(Screen parent) {
            super(parent);
        }

        @Override
        public void drawStuff(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
            GuiUtil.drawBoxWithOutline(this.center,100, 50, new Vector4f(0,0,0,255),
                    new Vector4f(255,255,255,255), 5f, getBlitOffset());
            GuiUtil.drawColoredRing(this.center,new Vector3f(195,200,0),new Vector4f(255,255,255,255),
                    360,getBlitOffset());
        }
    }
}
