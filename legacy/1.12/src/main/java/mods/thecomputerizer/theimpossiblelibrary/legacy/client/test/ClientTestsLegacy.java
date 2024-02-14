package mods.thecomputerizer.theimpossiblelibrary.legacy.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;

@EventBusSubscriber
public class ClientTestsLegacy {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test",Keyboard.KEY_R,"key.categories.theimpossiblelibrary");

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        if(TEST_KEYBIND.isKeyDown()) ClientTests.onTestKeyDown();
    }
}
