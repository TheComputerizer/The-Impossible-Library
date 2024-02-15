package mods.thecomputerizer.theimpossiblelibrary.legacy.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;

public class ClientTestsLegacy {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test",Keyboard.KEY_R,"key.categories.theimpossiblelibrary");

    public static void initClientTests() {
        ClientEventsHelper.addListener(KeyInputEvent.class,event -> {
            if(TEST_KEYBIND.isKeyDown()) ClientTests.runTests();
        });
    }
}
