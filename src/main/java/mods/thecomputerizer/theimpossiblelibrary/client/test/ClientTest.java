package mods.thecomputerizer.theimpossiblelibrary.client.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings({"unused"})
public class ClientTest {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test", KeyConflictContext.UNIVERSAL,
            InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.categories.theimpossiblelibrary");

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        if (TEST_KEYBIND.isDown()) {
            //render testing
            Minecraft.getInstance().setScreen(GuiTestClasses.createTestOtherGui());
        }
    }
}
