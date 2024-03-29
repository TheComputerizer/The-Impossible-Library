package mods.thecomputerizer.theimpossiblelibrary.client.test;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.client.render.Renderer;
import mods.thecomputerizer.theimpossiblelibrary.client.render.Text;
import mods.thecomputerizer.theimpossiblelibrary.common.toml.Holder;
import mods.thecomputerizer.theimpossiblelibrary.common.toml.IndexFinder;
import mods.thecomputerizer.theimpossiblelibrary.common.toml.Table;
import mods.thecomputerizer.theimpossiblelibrary.common.toml.Variable;
import mods.thecomputerizer.theimpossiblelibrary.util.file.FileUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.file.TomlUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused"})
public class ClientTest {

    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test", Keyboard.KEY_R, "key.categories.theimpossiblelibrary");

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        if (TEST_KEYBIND.isKeyDown()) {
            renderableTest();
            //guiTest();
            //tomlTest();
        }
    }

    private static void renderableTest() {
        try {
            Holder transitions = TomlUtil.readFully(Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation(Constants.MODID, "test/transitions.toml")).getInputStream());
            Renderer.addRenderable(new Text(transitions.getTableByName("title").getVarMap()));
            Table image = transitions.getTableByName("image");
            Renderer.addRenderable(Renderer.initializePng(new ResourceLocation(Constants.MODID,
                    image.getValOrDefault("name","missing")),image.getVarMap()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void guiTest() {
        //render testing
        Minecraft.getMinecraft().displayGuiScreen(GuiTestClasses.createTestOtherGui());
    }

    private static void tomlTest() {
        //test smart toml reading and printing
        try {
            Holder testHolder = TomlUtil.readFully(Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation(Constants.MODID,"test/thing.toml")).getInputStream());
            testHolder.removeTable(testHolder.getTableByName("hello").getTableByName("next"),"furtherbeyond",-1);
            testTableCreationAndOrdering(testHolder,testHolder.getTableByName("hello"),testHolder.getTableByName("hello").getTableByName("next"));
            testHolder.addVariable(testHolder.getTableByName("hello").getTableByName("next"),"lol",3.7);
            FileUtil.writeLinesToFile(new File(Constants.DATA_DIRECTORY, "test2.toml"),
                    testHolder.toLines(),false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testTableCreationAndOrdering(Holder testHolder, Table testTable, Table referenceTable) {
        Table song = testHolder.addTable(testTable,"song");
        Map<String, String> testMap = new HashMap<>();
        testMap.put("first", "test1");
        testMap.put("second", "test2");
        testMap.put("third", "test3");
        testMap.put("fifth", "test5");
        List<Variable> vars = testHolder.addVariableMap(song,testMap);
        testHolder.addComment(song, Arrays.asList("c1","c2","c3"),new IndexFinder(song,vars.get(3)));
        testHolder.addVariable(song,"fourth","test44",new IndexFinder(song,vars.get(3)));
    }
}
