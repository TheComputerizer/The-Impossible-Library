package mods.thecomputerizer.theimpossiblelibrary.test;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.client.visual.GIF;
import mods.thecomputerizer.theimpossiblelibrary.client.visual.Renderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ClientTest {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test", Keyboard.KEY_R, "key.categories.theimpossiblelibrary");
    public static GIF testGif = null;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        if (TEST_KEYBIND.isKeyDown()) {
            //render testing
            if(testGif==null) testGif = Renderer.initializeGif(new ResourceLocation(Constants.MODID,"test.gif"));
            else Renderer.renderGifToBackground(testGif,"center","center",0,0,1f,1f,10000);
        }
    }
}
