package mods.thecomputerizer.theimpossiblelibrary;

import com.mojang.blaze3d.platform.InputConstants;
import mods.thecomputerizer.theimpossiblelibrary.client.render.Renderer;
import mods.thecomputerizer.theimpossiblelibrary.client.test.ClientTest;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class ClientInit implements ClientModInitializer {
    //public static KeyMapping TEST_KEYBIND;
    @Override
    public void onInitializeClient() {
        /*
        setUpClientEvents();
        TEST_KEYBIND = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.test", InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R, "key.categories.theimpossiblelibrary"));

         */
    }

    private static void setUpClientEvents() {
        /*
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (TEST_KEYBIND.isDown()) ClientTest.onTestKey();
        });

        WorldRenderEvents.LAST.register(context -> Renderer.renderAllBackgroundStuff(context.matrixStack(),Minecraft.getInstance().getWindow()));

         */
    }
}
