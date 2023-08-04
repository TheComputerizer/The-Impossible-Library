package mods.thecomputerizer.theimpossiblelibrary.util.file;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DataUtil {
    private static boolean GLOBAL_LOAD_FAILED = false;
    private static boolean WORLD_LOAD_FAILED = false;
    private static final List<String> EXPLANATION = Arrays.asList("Hi!",
            "This folder is used to store data used by The Impossible Library and other mods that might use it as a " +
                    "dependency\n",
            "--------------------------------------------------\n",
            "For mod developers:",
            "If you registered any global data through The Impossible Library, this is where that gets stored! So if " +
                    "you see your modid here, everything is working as intended.\n",
            "--------------------------------------------------\n",
            "For modpack creators:",
            "This is where mods that utilize the global data system implemented by The Impossible Library have their " +
                    "data stored! If you want to quickly reset a specific mod's data, you can delete its file here.\n",
            "--------------------------------------------------\n",
            "For players:",
            "You probably do not have to worry about this folder, but if a specific mod is breaking that appears here, " +
                    "you can try deleting the data and seeing if the problem is fixed. Remember to report issues!");

    public static void initGlobal() {
        try {
            writeExplanation(FileUtil.generateNestedFile("./impossible_data/what_is_this_folder.txt",false));
        } catch (IOException e) {
            LogUtil.logInternal(Level.ERROR, "There was an error generating the data folder or explanation file");
            GLOBAL_LOAD_FAILED = true;
        }
    }

    private static void writeExplanation(File file) throws IOException {
        if(Objects.nonNull(file)) FileUtil.writeLinesToFile(file, EXPLANATION,false);
        else throw new IOException("Failed to create file");
    }

    public static void writeWorldData(CompoundTag data, String modid, @Nonnull String worldName) throws IOException {
        CompoundTag globalTag = getGlobalData(modid, true);
        if (Objects.nonNull(globalTag)) {
            getOrCreateCompound(globalTag, "world_data").put(worldName, data);
            writeGlobalData(data, modid);
        }
    }

    public static CompoundTag getWorldData(String modid, @Nonnull String worldName) {
        try {
            CompoundTag globalTag = getGlobalData(modid, true);
            if (Objects.nonNull(globalTag))
                return getOrCreateCompound(getOrCreateCompound(globalTag, "world_data"), worldName);
        } catch (IOException ex) {
            LogUtil.logInternal(Level.ERROR, "Unable to read mod data for modid {} in world {}", modid, worldName, ex);
        }
        return null;
    }

    /**
     Writes global nbt data to a dat file for a given modid
     Will fail if the data folder failed to initialize or the data module is turned off
     */
    public static void writeGlobalData(CompoundTag data, String modid) throws IOException {
        if(!GLOBAL_LOAD_FAILED) writeFileData(data, Constants.DATA_DIRECTORY, modid);
        else LogUtil.logInternal(Level.WARN, "There was a problem when initializing global data for The Impossible Library so data for {} could not be written",modid);
    }

    /**
     Gets global data stored in a dat file for the modid input
     Returns null if the file does not exist and is not set to be created
     Will also return null if the data folder failed to initialize or the data module is turned off.
     */
    public static CompoundTag getGlobalData(String modid, boolean createIfAbsent) throws IOException {
        if(!GLOBAL_LOAD_FAILED) return getFileData(Constants.DATA_DIRECTORY, modid, createIfAbsent);
        LogUtil.logInternal(Level.WARN, "There was a problem when initializing global data for The Impossible Library so data for {} could not be retrieved",modid);
        return null;
    }

    private static void writeFileData(CompoundTag data, File directory, String modid) throws IOException {
        File dataFile = new File(directory, modid + ".dat");
        dataFile = FileUtil.generateNestedFile(dataFile,true);
        if(Objects.nonNull(dataFile)) NbtIo.write(data, dataFile);
        else LogUtil.logInternal(Level.ERROR,"Could not write data for {} due to an error in creating the file",modid);
    }

    private static CompoundTag getFileData(File directory, String modid, boolean createIfAbsent) throws IOException {
        File dataFile = new File(directory, modid + ".dat");
        if (dataFile.exists()) return NbtIo.read(dataFile);
        if (createIfAbsent) {
            dataFile = FileUtil.generateNestedFile(dataFile,false);
            if (dataFile != null) return NbtIo.read(dataFile);
        }
        return null;
    }

    /**
     * Throws an IOException if the key already exists as and is not of the type CompoundTag
     */
    public static CompoundTag getOrCreateCompound(CompoundTag tag, String key) throws IOException {
        if(tag.contains(key)) {
            Tag baseTag = tag.get(key);
            if(!(baseTag instanceof CompoundTag)) throw new IOException("Tried to get existing tag of the wrong type!");
            return (CompoundTag)baseTag;
        }
        CompoundTag compound = new CompoundTag();
        tag.put(key,compound);
        return compound;
    }



    /**
     * Throws an IOException if the key already exists as and is not of the type NBTTagList
     */
    public static ListTag getOrCreateList(CompoundTag tag, String key) throws IOException {
        if(tag.contains(key)) {
            Tag baseTag = tag.get(key);
            if(!(baseTag instanceof ListTag)) throw new IOException("Tried to get existing tag of the wrong type!");
            return (ListTag)baseTag;
        }
        ListTag list = new ListTag();
        tag.put(key,list);
        return list;
    }
}