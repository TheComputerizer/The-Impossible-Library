package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.KEY_INPUT;

public class ClientTests1_16_5 {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test", KeyConflictContext.UNIVERSAL,
            InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_R,"key.categories.theimpossiblelibrary");

    public static void initClientTests() {
        EventHelper.addListener(KEY_INPUT,wrapper -> {
            if(TEST_KEYBIND.isDown()) ClientTests.runTests();
        });
    }
}
