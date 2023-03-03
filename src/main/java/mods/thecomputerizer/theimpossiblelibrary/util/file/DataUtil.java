package mods.thecomputerizer.theimpossiblelibrary.util.file;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataUtil {
    private static boolean GLOBAL_LOAD_FAILED = false;
    private static boolean WORLD_LOAD_FAILED = false;
    private static final List<String> explanation = Stream.of("Hi!\n",
            "This folder is used to store data used by The Impossible Library and other mods that might use it as a " +
                    "dependency\n",
            "--------------------------------------------------\n",
            "For mod developers:\n",
            "If you registered any global data through The Impossible Library, this is where that gets stored! So if " +
                    "you see your modid here, everything is working as intended.\n",
            "--------------------------------------------------\n",
            "For modpack creators:\n",
            "This is where mods that utilize the global data system implemented by The Impossible Library have their " +
                    "data stored! If you want to quickly reset a specific mod's data, you can delete its file here.\n",
            "--------------------------------------------------\n",
            "For players:\n",
            "You probably do not have to worry about this folder, but if a specific mod is breaking that appears here, " +
                    "you can try deleting the data and seeing if the problem is fixed. Remember to report issues!").collect(Collectors.toList());

    public static void initGlobal() {
        try {
            writeExplanation(FileUtil.generateNestedFile("./impossible_data/what_is_this_folder.txt",false));
        } catch (IOException e) {
            LogUtil.logInternal(Level.ERROR, "There was an error generating the data folder or explanation file");
            GLOBAL_LOAD_FAILED = true;
        }
    }

    private static void writeExplanation(File file) throws IOException {
        if(file!=null) FileUtil.writeLinesToFile(file,explanation,false);
        else throw new IOException("Failed to create file");
    }

    /**
        Writes global nbt data to a dat file for a given modid
        Will fail if the data folder failed to initialize or the data module is turned off
     */
    public static void writeGlobalData(CompoundNBT data, String modid) throws IOException {
        if(!GLOBAL_LOAD_FAILED) writeFileData(data, Constants.DATA_DIRECTORY, modid);
        else LogUtil.logInternal(Level.WARN, "There was a problem when initializing global data for The Impossible Library so data for {} could not be written",modid);
    }

    /**
        Gets global data stored in a dat file for the modid input
        Returns null if the file does not exist and is not set to be created
        Will also return null if the data folder failed to initialize or the data module is turned off
     */
    public static CompoundNBT getGlobalData(String modid, boolean createIfAbsent) throws IOException {
        if(!GLOBAL_LOAD_FAILED) return getFileData(Constants.DATA_DIRECTORY, modid, createIfAbsent);
        LogUtil.logInternal(Level.WARN, "There was a problem when initializing global data for The Impossible Library so data for {} could not be retrieved",modid);
        return null;
    }

    private static void writeFileData(CompoundNBT data, File directory, String modid) throws IOException {
        File dataFile = new File(directory, modid + ".dat");
        dataFile = FileUtil.generateNestedFile(dataFile,true);
        if (dataFile != null) CompressedStreamTools.write(data, dataFile);
        else LogUtil.logInternal(Level.ERROR,"Could not write data for {} due to an error in creating the file",modid);
    }

    private static CompoundNBT getFileData(File directory, String modid, boolean createIfAbsent) throws IOException {
        File dataFile = new File(directory, modid + ".dat");
        if (dataFile.exists()) return CompressedStreamTools.read(dataFile);
        if (createIfAbsent) {
            dataFile = FileUtil.generateNestedFile(dataFile,false);
            if (dataFile != null) return CompressedStreamTools.read(dataFile);
        }
        return null;
    }
}
