package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ClientTestsForge {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test", KeyConflictContext.UNIVERSAL,
            InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_R,"key.categories.theimpossiblelibrary");

    public static void initClientTests() {
        ClientEventsHelper.addListener(KeyInputEvent.class, event -> {
            if(TEST_KEYBIND.isDown()) ClientTests.runTests();
        });
    }
}
