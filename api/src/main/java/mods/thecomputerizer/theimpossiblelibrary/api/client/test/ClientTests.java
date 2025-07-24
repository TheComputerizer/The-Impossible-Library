package mods.thecomputerizer.theimpossiblelibrary.api.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderablePNG;
import mods.thecomputerizer.theimpossiblelibrary.api.common.test.TestsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderableText;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.toml.*;

import java.io.InputStream;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum.L;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

@SuppressWarnings("unused")
public class ClientTests extends TestsAPI {

    public static final KeyAPI<?> TEST_KEY = KeyHelper.create("key.test","key.categories."+MODID,L);
    private static final boolean GUI = true;
    private static final boolean RENDERABLE = false;
    private static final boolean TOML = false;

    public static void runTests(Object ... args) {
        TILRef.logInfo("Initializing client tests");
        new ClientTests().runAndLog(args);
    }
    
    private ClientTests() {
        super("Client");
    }
    
    private boolean guiTest() {
        logDebug("Running GUI test");
        ScreenHelper.open(TestScreen::new);
        return true;
    }
    
    private boolean renderableTest() {
        try {
            logDebug("Running Renderable tests");
            ResourceLocationAPI<?> testLocation = TILRef.res("test/transitions.toml");
            InputStream stream = ResourceHelper.getResourceStream(testLocation);
            Toml transitions = Toml.readStream(stream);
            if(!renderableTitleTest(transitions) || !renderableImageTest(transitions)) return false;
        } catch(Exception ex) {
            logError("Renderable test failed!",ex);
            return false;
        }
        return true;
    }
    
    private boolean renderableTitleTest(Toml transitions) {
        logDebug("Running Renderable title test");
        RenderableText title = new RenderableText(transitions.getTable("title").parameterizeEntries());
        RenderHelper.addRenderable(title);
        return true;
    }
    
    private boolean renderableImageTest(Toml transitions) {
        logDebug("Running Renderable image test");
        Toml image = transitions.getTable("image");
        ResourceLocationAPI<?> location = TILRef.res(image.getValueString("name"));
        RenderablePNG png = RenderHelper.initPNG(location,image.parameterizeEntries());
        RenderHelper.addRenderable(png);
        return true;
    }
    
    @Override protected boolean run(Object... args) {
        logDebug("Running tests");
        if(RENDERABLE) {
            if(!renderableTest()) return false;
        }
        if(GUI) {
            if(!guiTest()) return false;
        }
        return !TOML || tomlTest();
    }
    
    private boolean tomlTest() {
        return true;
    }
}