package mods.thecomputerizer.theimpossiblelibrary.api.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderableText;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.toml.*;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum.L;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

@SuppressWarnings("unused")
public class ClientTests {

    public static final KeyAPI<?> TEST_KEY = KeyHelper.create("key.test",String.format("key.categories.%1$s",MODID),L);

    public static void runTests() {
        TILRef.logWarn("Running client tests");
        //renderableTest();
        guiTest();
        //tomlTest();
    }

    private static void renderableTest() {
        try {
            TILDev.logWarn("RENDERABLE TEST");
            Toml transitions = Toml.readStream(ResourceHelper.getResourceStream(TILRef.res("test/transitions.toml")));
            renderableTitleTest(transitions);
            renderableImageTest(transitions);
        } catch(Exception ex) {
            TILRef.logError("Renderable test failed!",ex);
        }
    }

    private static void renderableTitleTest(Toml transitions) {
        TILDev.logWarn("TITLE TEST");
        RenderHelper.addRenderable(new RenderableText(transitions.getTable("title").getEntryValuesAsMap()));
    }

    private static void renderableImageTest(Toml transitions) {
        TILDev.logWarn("IMAGE TEST");
        Toml image = transitions.getTable("image");
        RenderHelper.addRenderable(RenderHelper.initPNG(TILRef.res(image.getValueString("name")),image.getEntryValuesAsMap()));
    }

    private static void guiTest() {
        TILDev.logWarn("GUI TEST");
        ScreenHelper.open(TestScreen::new);
    }
}