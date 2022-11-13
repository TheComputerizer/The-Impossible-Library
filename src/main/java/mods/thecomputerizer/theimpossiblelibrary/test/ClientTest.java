package mods.thecomputerizer.theimpossiblelibrary.test;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialButton;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialElement;
import mods.thecomputerizer.theimpossiblelibrary.client.gui.RadialProgressBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused"})
public class ClientTest {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test", Keyboard.KEY_R, "key.categories.theimpossiblelibrary");

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        if (TEST_KEYBIND.isKeyDown()) {
            //render testing
            Minecraft.getMinecraft().displayGuiScreen(createTestGui(false));
        }
    }

    private static TestGui createTestGui(boolean alt) {
        return new TestGui(null,alt);
    }

    private static RadialElement getInitialTest(TestGui parent, int[] loc) {
        return new RadialElement(parent,getTestIcon("mt2",false,true),null,getTestProgressBar(),
                loc[0],loc[1],loc[2],loc[3],(int)(((float)loc[2])/2f),null,
                Arrays.asList("Center Hover test1","Center Hover test2"),100f,0.1f,getTestButtons());
    }

    private static RadialElement getAltTest(TestGui parent, int[] loc) {
        return new RadialElement(parent,getTestIcon("mt2",false,true),null,getTestProgressBar(),
                loc[0],loc[1],loc[2],loc[3],(int)(((float)loc[2])/2f),null,
                Arrays.asList("Center Hover test1","Center Hover test2"),100f,0.1f,getTestAltButtons());
    }

    private static RadialProgressBar getTestProgressBar() {
        return new RadialProgressBar(0,25,0f,100,
                (screen, button, mousePos) -> button.setProgress(button.mousePosToProgress(mousePos)));
    }

    private static List<RadialButton> getTestButtons() {
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
                        Minecraft.getMinecraft().displayGuiScreen(createTestGui(true))),
                new RadialButton(Arrays.asList("hover4-1","hover4-2"),
                        getTestIcon("reset",false,false),getTestIcon("reset",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(createTestGui(true))));
    }

    private static List<RadialButton> getTestAltButtons() {
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
                        Minecraft.getMinecraft().displayGuiScreen(createTestGui(true))),
                new RadialButton(Arrays.asList("hover4-1","hover4-2"),
                        getTestIcon("toggle",false,false),getTestIcon("toggle",true,false),
                        0.25f,null,(screen, button) ->
                        Minecraft.getMinecraft().displayGuiScreen(createTestGui(true))));
    }

    private static ResourceLocation getTestIcon(String name, boolean hover, boolean center) {
        String base = "textures/gui_test/";
        if(center) return new ResourceLocation(Constants.MODID,base+"isotypes/"+name+".png");
        else {
            if(hover) return new ResourceLocation(Constants.MODID,base+"black_icons/"+name+".png");
            else return new ResourceLocation(Constants.MODID,base+"white_icons/"+name+".png");
        }
    }

    private static class TestGui extends GuiScreen {
        protected final GuiScreen parent;
        protected RadialElement circleButton;
        private final boolean alt;
        private int buttonIDCounter;

        public TestGui(GuiScreen parent, boolean alt) {
            this.parent = parent;
            this.alt = alt;
        }

        private int[] setCenterCircle() {
            return new int[]{(int) (((float) this.width) / 2f), (int) (((float) this.height) / 2f), 50, 100};
        }

        @Override
        public void initGui() {
            super.initGui();
            if (this.alt) this.circleButton = getInitialTest(this, setCenterCircle());
            else this.circleButton = getAltTest(this, setCenterCircle());
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

        @Override
        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            circleButton.mousePressed(mouseX, mouseY, mouseButton);
        }

        public void drawStuff(int mouseX, int mouseY, float partialTicks) {
            circleButton.render(this.zLevel, mouseX, mouseY);
        }
    }
}
