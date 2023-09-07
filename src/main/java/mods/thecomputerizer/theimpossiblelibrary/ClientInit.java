package mods.thecomputerizer.theimpossiblelibrary;

import com.mojang.blaze3d.platform.InputConstants;
import mods.thecomputerizer.theimpossiblelibrary.client.render.Renderer;
import mods.thecomputerizer.theimpossiblelibrary.client.test.ClientEventTest;
import mods.thecomputerizer.theimpossiblelibrary.client.test.ClientTest;
import mods.thecomputerizer.theimpossiblelibrary.events.ResourcesLoadedEvent;
import mods.thecomputerizer.theimpossiblelibrary.network.NetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class ClientInit implements ClientModInitializer {
    private static KeyMapping TEST_KEYBIND;
    @Override
    public void onInitializeClient() {
        setUpClientEvents();
        if(TheImpossibleLibrary.isDevEnv()) {
            TEST_KEYBIND = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.test", InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_R, "key.categories.theimpossiblelibrary"));
        }
    }

    private void setUpClientEvents() {
        ResourcesLoadedEvent.EVENT.register(() -> {
            if(!TheImpossibleLibrary.isClientOnly()) NetworkHandler.init();
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Renderer.tickRenderables();
            if(TheImpossibleLibrary.isDevEnv() && TEST_KEYBIND.isDown())
                ClientTest.onTestKey();
        });
        HudRenderCallback.EVENT.register((graphics, delta) -> Renderer.renderAllBackgroundStuff(graphics,Minecraft.getInstance().getWindow()));
        if(TheImpossibleLibrary.isDevEnv()) ClientEventTest.init();
    }
}
