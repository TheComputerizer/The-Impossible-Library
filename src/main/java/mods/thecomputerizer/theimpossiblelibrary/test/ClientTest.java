package mods.thecomputerizer.theimpossiblelibrary.test;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.client.visual.GIF;
import mods.thecomputerizer.theimpossiblelibrary.client.visual.MP4;
import mods.thecomputerizer.theimpossiblelibrary.client.visual.Renderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ClientTest {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test", Keyboard.KEY_R, "key.categories.theimpossiblelibrary");
    public static GIF testGif = null;
    public static MP4 testMp4 = null;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        if (TEST_KEYBIND.isKeyDown()) {
            //render testing
            if(testMp4==null) testMp4 = Renderer.initializeMp4(new ResourceLocation(Constants.MODID,"test.mp4"));
            else Renderer.renderMP4ToBackground(testMp4,"center","center",0,0,1f,1f,10000);
        }
    }
}
