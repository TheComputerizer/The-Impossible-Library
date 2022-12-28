package mods.thecomputerizer.theimpossiblelibrary.client.test;


import net.minecraft.client.Minecraft;

@SuppressWarnings({"unused"})
public class ClientTest {

    public static void onTestKey() {
        Minecraft.getInstance().setScreen(GuiTestClasses.createTestOtherGui());
    }
}
