package mods.thecomputerizer.theimpossiblelibrary.api.client.test;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderableText;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.toml.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum.R;

public class ClientTests {

    public static KeyAPI<?> TEST_KEY = KeyHelper.create("key.test","key.categories.theimpossiblelibrary",R);

    public static void runTests() {
        TILRef.logWarn("Running client tests");
        renderableTest();
        //guiTest();
        //tomlTest();
    }

    private static void renderableTest() {
        try {
            TILDev.logWarn("RENDERABLE TEST");
            Toml transitions = Toml.readStream(ResourceHelper.getResourceStream(TILRef.res("test/transitions.toml")));
            //renderableTitleTest(transitions);
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
        //render testing
        //Minecraft.getInstance().setScreen(GuiTestClasses.createTestOtherGui());
    }

    private static void tomlTest() {
        //test smart toml reading and printing
        try {
            Toml testHolder = Toml.readStream(ResourceHelper.getResourceStream(TILRef.res("test/thing.toml")));
            testHolder.getTable("hello").getTable("next").removeTables("furtherbeyond");
            testTableCreationAndOrdering(testHolder,testHolder.getTable("hello"),testHolder.getTable("hello").getTable("next"));
            testHolder.getTable("hello").getTable("next").addEntry("lol",3.7);
            List<String> lines = new ArrayList<>();
            testHolder.write(lines,0);
            FileHelper.writeLines(new File(TILRef.DATA_DIRECTORY,"test2.toml"),lines,false);
        } catch(Exception ex) {
            TILRef.logError("Toml test failed!",ex);
        }
    }

    private static void testTableCreationAndOrdering(Toml testHolder, Toml testTable, Toml referenceTable)
            throws TomlWritingException {
        Toml song = testHolder.addTable("song",true);
        Map<String,String> testMap = new HashMap<>();
        testHolder.addEntry("first","test1");
        testHolder.addEntry("second","test2");
        testHolder.addEntry("third","test3");
        testHolder.addEntry("fifth","test5");
        //testHolder.addComment(song,Arrays.asList("c1","c2","c3"),new IndexFinder(song,vars.get(3)));
        //testHolder.addEntry(song,"fourth","test44",new IndexFinder(song,vars.get(3)));
    }

    private static class GuiTests {

    }
}