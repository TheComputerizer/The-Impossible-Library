package mods.thecomputerizer.theimpossiblelibrary.test;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@SuppressWarnings({"unused", "StatementWithEmptyBody"})
public class ClientTest {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test", Keyboard.KEY_R, "key.categories.theimpossiblelibrary");

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        if (TEST_KEYBIND.isKeyDown()) {
            //render testing
            //Minecraft.getMinecraft().displayGuiScreen(getInitialTest());
        }
    }
    /*

    private static RadialTest getInitialTest() {
        return new RadialTest(getTestButtons());
    }

    private static RadialTest getAltTest() {
        return new RadialTest(getTestAltButtons());
    }

    private static List<RadialButtonTest> getTestButtons() {
        return Arrays.asList(new RadialButtonTest(Arrays.asList("hover1-1","hover1-2"),null,"test1",
                (screen) -> {
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    return null;
                }), new RadialButtonTest(Arrays.asList("hover2-1","hover2-2"),null,"test2",
                (screen) -> {
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    return null;
                }), new RadialButtonTest(Arrays.asList("hover3-1","hover3-2"),null,"test3",
                (screen) -> {
                    Minecraft.getMinecraft().displayGuiScreen(getAltTest());
                    return null;
                }), new RadialButtonTest(Arrays.asList("hover4-1","hover4-2"),null,"test4",
                (screen) -> {
                    Minecraft.getMinecraft().displayGuiScreen(getAltTest());
                    return null;
                }));
    }

    private static List<RadialButtonTest> getTestAltButtons() {
        return Arrays.asList(new RadialButtonTest(Arrays.asList("hover2-1","hover2-2"),null,"test2",
                (screen) -> {
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    return null;
                }), new RadialButtonTest(Arrays.asList("hover3-1","hover3-2"),null,"test3",
                (screen) -> {
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    return null;
                }), new RadialButtonTest(Arrays.asList("hover4-1","hover4-2"),null,"test4",
                (screen) -> {
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    return null;
                }), new RadialButtonTest(Arrays.asList("hover5-1","hover5-2"),null,"test5",
                (screen) -> {
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    return null;
                }));
    }

     */
}
