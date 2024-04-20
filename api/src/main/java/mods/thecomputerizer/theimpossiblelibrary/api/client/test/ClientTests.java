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
import java.util.Arrays;
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
            Holder transitions = TomlHelper.readFully(ResourceHelper.getResourceStream(TILRef.res("test/transitions.toml")));
            renderableTitleTest(transitions);
            renderableImageTest(transitions);
        } catch(Exception ex) {
            TILRef.logError("Renderable test failed!",ex);
        }
    }

    private static void renderableTitleTest(Holder transitions) {
        TILDev.logWarn("TITLE TEST");
        RenderHelper.addRenderable(new RenderableText(transitions.getTableByName("title").getVarMap()));
    }

    private static void renderableImageTest(Holder transitions) {
        TILDev.logWarn("IMAGE TEST");
        Table image = transitions.getTableByName("image");
        RenderHelper.addRenderable(RenderHelper.initPNG(TILRef.res(image.getValOrDefault("name","missing")),
                image.getVarMap()));
    }

    private static void guiTest() {
        //render testing
        //Minecraft.getInstance().setScreen(GuiTestClasses.createTestOtherGui());
    }

    private static void tomlTest() {
        //test smart toml reading and printing
        try {
            Holder testHolder = TomlHelper.readFully(ResourceHelper.getResourceStream(
                    TILRef.res("test/thing.toml")));
            testHolder.removeTable(testHolder.getTableByName("hello").getTableByName("next"),"furtherbeyond",-1);
            testTableCreationAndOrdering(testHolder,testHolder.getTableByName("hello"),testHolder.getTableByName("hello").getTableByName("next"));
            testHolder.addVariable(testHolder.getTableByName("hello").getTableByName("next"),"lol",3.7);
            FileHelper.writeLines(new File(TILRef.DATA_DIRECTORY, "test2.toml"),
                    testHolder.toLines(),false);
        } catch(Exception ex) {
            TILRef.logError("Toml test failed!",ex);
        }
    }

    private static void testTableCreationAndOrdering(Holder testHolder, Table testTable, Table referenceTable) {
        Table song = testHolder.addTable(testTable,"song");
        Map<String,String> testMap = new HashMap<>();
        testMap.put("first","test1");
        testMap.put("second","test2");
        testMap.put("third","test3");
        testMap.put("fifth","test5");
        List<Variable> vars = testHolder.addVariableMap(song,testMap);
        testHolder.addComment(song,Arrays.asList("c1","c2","c3"),new IndexFinder(song,vars.get(3)));
        testHolder.addVariable(song,"fourth","test44",new IndexFinder(song,vars.get(3)));
    }

    private static class GuiTests {

    }
}