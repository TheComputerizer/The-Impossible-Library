package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("SameParameterValue")
public class TagHelper {
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
    private static CompoundTagAPI getFileData(File directory, String modid, boolean createIfAbsent) throws IOException {
        File dataFile = new File(directory,modid+".dat");
        if(dataFile.exists()) return readFromFile(dataFile);
        if(createIfAbsent) {
            dataFile = FileHelper.get(dataFile,false);
            if(Objects.nonNull(dataFile)) return readFromFile(dataFile);
        }
        return null;
    }

    /**
     * Gets global data stored in a dat file for the modid input
     * Returns null if the file does not exist and is not set to be created
     * Will also return null if the data folder failed to initialize or the data module is turned off.
     */
    public static CompoundTagAPI getGlobalData(String modid, boolean createIfAbsent) throws IOException {
        if(!GLOBAL_LOAD_FAILED) return getFileData(TILRef.DATA_DIRECTORY,modid,createIfAbsent);
        TILRef.logWarn("There was a problem when initializing global data for {} so data for {} could not be "+
                "retrieved!",TILRef.NAME,modid);
        return null;
    }

    /**
     * Throws an IOException if the key already exists as and is not of the type CompoundTag
     */
    public static CompoundTagAPI getOrCreateCompound(CompoundTagAPI tag, String key) throws IOException {
        if(tag.contains(key)) {
            BaseTagAPI baseTag = tag.getTag(key);
            if(!baseTag.isCompound()) throw new IOException("Tried to get existing tag of the wrong type!");
            return baseTag.asCompoundTag();
        }
        CompoundTagAPI compound = makeCompoundTag();
        tag.putTag(key,compound);
        return compound;
    }

    /**
     * Throws an IOException if the key already exists as and is not of the type NBTTagList
     */
    public static ListTagAPI getOrCreateList(CompoundTagAPI tag, String key) {
        if(tag.contains(key)) return tag.getListTag(key);
        ListTagAPI list = makeListTag();
        tag.putTag(key,list);
        return list;
    }

    public static @Nullable TagAPI getTagAPI() {
        return TILRef.getCommonSubAPI("TagAPI",CommonAPI::getTagAPI);
    }

    public static CompoundTagAPI getWorldData(String modid, @Nonnull String worldName) {
        try {
            CompoundTagAPI globalTag = getGlobalData(modid,true);
            if(Objects.nonNull(globalTag))
                return getOrCreateCompound(getOrCreateCompound(globalTag,"world_data"),worldName);
        } catch(IOException ex) {
            TILRef.logError("Unable to read mod data for modid {} in world {}",modid,worldName,ex);
        }
        return null;
    }

    public static void initGlobal() {
        try {
            writeExplanation(FileHelper.get("./impossible_data/what_is_this_folder.txt",false));
        } catch(IOException ex) {
            TILRef.logError("There was an error generating the data folder or explanation file!",ex);
            GLOBAL_LOAD_FAILED = true;
        }
    }

    public static @Nullable CompoundTagAPI makeCompoundTag() {
        TagAPI api = getTagAPI();
        return Objects.nonNull(api) ? api.makeCompoundTag() : null;
    }

    public static @Nullable ListTagAPI makeListTag() {
        TagAPI api = getTagAPI();
        return Objects.nonNull(api) ? api.makeListTag() : null;
    }

    public static CompoundTagAPI readFromFile(File file) throws IOException {
        TagAPI api = getTagAPI();
        return Objects.nonNull(api) ? api.readFromFile(file) : null;
    }

    private static void writeDataFile(CompoundTagAPI data, File directory, String modid) throws IOException {
        File dataFile = new File(directory,modid+".dat");
        dataFile = FileHelper.get(dataFile,true);
        if(Objects.nonNull(dataFile)) writeToFile(data,dataFile);
        else TILRef.logError("Could not write data for {} due to an error in creating the file",modid);
    }

    private static void writeExplanation(File file) throws IOException {
        if(Objects.nonNull(file)) FileHelper.writeLines(file,EXPLANATION,false);
        else throw new IOException("Failed to create file!");
    }

    /**
     * Writes global nbt data to a dat file for a given modid
     * Will fail if the data folder failed to initialize or the data module is turned off
     */
    public static void writeGlobalData(CompoundTagAPI data, String modid) throws IOException {
        if(!GLOBAL_LOAD_FAILED) writeDataFile(data,TILRef.DATA_DIRECTORY,modid);
        else TILRef.logWarn("There was a problem when initializing global data for {} so data for {} could not "+
                "be written!",TILRef.NAME,modid);
    }

    public static void writeToFile(CompoundTagAPI data, File file) throws IOException {
        TagAPI api = getTagAPI();
        if(Objects.nonNull(api)) api.writeToFile(data,file);
    }

    public static void writeWorldData(CompoundTagAPI data, String modid, @Nonnull String worldName) throws IOException {
        CompoundTagAPI globalTag = getGlobalData(modid,true);
        if(Objects.nonNull(globalTag)) {
            getOrCreateCompound(globalTag,"world_data").putTag(worldName,data);
            writeGlobalData(data, modid);
        }
    }
}