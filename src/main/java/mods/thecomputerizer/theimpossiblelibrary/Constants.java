package mods.thecomputerizer.theimpossiblelibrary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class Constants {
    public static final String MODID = "theimpossiblelibrary";
    public static final String NAME = "The Impossible Library";
    public static final String VERSION = "1.12.2-1.0";
    public static final String DEPENDENCIES = "required-after:mixinbooter";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final Random RANDOM = new Random();
}
