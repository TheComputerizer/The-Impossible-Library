package mods.thecomputerizer.theimpossiblelibrary.legacy.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.KEY_INPUT;

public class ClientTestsLegacy {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test",Keyboard.KEY_R,"key.categories.theimpossiblelibrary");

    public static void initClientTests() {
        EventHelper.addListener(KEY_INPUT, wrapper -> {
            if(TEST_KEYBIND.isKeyDown()) ClientTests.runTests();
        });
    }
}
