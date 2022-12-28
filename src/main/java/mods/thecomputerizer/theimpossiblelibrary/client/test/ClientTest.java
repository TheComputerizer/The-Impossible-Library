package mods.thecomputerizer.theimpossiblelibrary.client.test;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings({"unused"})
public class ClientTest {

    public static final KeyMapping TEST_KEYBIND = new KeyMapping("key.test", KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.categories.theimpossiblelibrary");

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key e) {
        if (TEST_KEYBIND.isDown()) {
            //render testing
            Minecraft.getInstance().setScreen(GuiTestClasses.createTestOtherGui());
        }
    }
}
