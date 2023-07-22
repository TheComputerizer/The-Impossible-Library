package mods.thecomputerizer.theimpossiblelibrary;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@SuppressWarnings("unused")
public class Constants {
    public static final String MODID = "theimpossiblelibrary";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final File DATA_DIRECTORY = new File("./impossible_data");

    public static ResourceLocation res(String path) {
        return new ResourceLocation(MODID,path);
    }
}
