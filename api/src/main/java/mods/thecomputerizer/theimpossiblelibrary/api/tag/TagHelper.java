package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.server.ServerHelper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"SameParameterValue", "unused"})
public class TagHelper {
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
    
    private static CompoundTagAPI<?> getFileData(File directory, String modid, boolean createIfAbsent) throws IOException {
        File dataFile = new File(directory,modid+".dat");
        if(dataFile.exists()) return readFromFile(dataFile);
        if(createIfAbsent) {
            dataFile = FileHelper.get(dataFile,false);
            if(Objects.nonNull(dataFile)) return readFromFile(dataFile);
        }
        return makeCompoundTag();
    }

    /**
     * Gets global data stored in a dat file for the modid input.
     * Returns null if the file does not exist and is not set to be created.
     * Will also return null if the data folder failed to initialize or the data module is turned off.
     */
    public static CompoundTagAPI<?> getGlobalData(String modid, boolean createIfAbsent) throws IOException {
        return getFileData(TILRef.DATA_DIRECTORY,modid,createIfAbsent);
    }

    /**
     * Throws an IOException if the key already exists as and is not of the type CompoundTag
     */
    public static CompoundTagAPI<?> getOrCreateCompound(CompoundTagAPI<?> tag, String key) throws IOException {
        if(tag.contains(key)) {
            BaseTagAPI<?> baseTag = tag.getTag(key);
            if(!baseTag.isCompound()) throw new IOException("Tried to get existing tag of the wrong type!");
            return baseTag.asCompoundTag();
        }
        CompoundTagAPI<?> compound = makeCompoundTag();
        tag.putTag(key,compound);
        return compound;
    }

    /**
     * Throws an IOException if the key already exists as and is not of the type NBTTagList
     */
    public static ListTagAPI<?> getOrCreateList(CompoundTagAPI<?> tag, String key) {
        if(tag.contains(key)) return tag.getListTag(key);
        ListTagAPI<?> list = makeListTag();
        tag.putTag(key,list);
        return list;
    }

    public static TagAPI getTagAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getTag);
    }
    
    private static CompoundTagAPI<?> getWorldData() {
        File saveDir = ServerHelper.getSaveDir();
        if(Objects.isNull(saveDir)) {
            TILRef.logError("Failed to get world directory!");
            return makeCompoundTag();
        }
        return getWorldData(saveDir);
    }
    
    private static CompoundTagAPI<?> getWorldData(File saveDir) {
        try {
            return readFromFile(getWorldFile(saveDir,false));
        } catch(IOException ex) {
            TILRef.logFatal("Unable to read world data!",ex);
        }
        return makeCompoundTag();
    }

    public static CompoundTagAPI<?> getWorldData(String modid) {
        CompoundTagAPI<?> data = getWorldData();
        try {
            return Objects.nonNull(data) ? getOrCreateCompound(data,modid) : makeCompoundTag();
        } catch(IOException ex) {
            TILRef.logError("Failed to get world data for {}",modid);
        }
        return makeCompoundTag();
    }
    
    public static File getWorldFile(File saveDir, boolean overwrite) {
        return FileHelper.get(new File(saveDir,TILRef.NAME+"/mod_data.dat"),overwrite);
    }
    
    public static <T> BaseTagAPI<T> getWrapped(T tag) {
        return getTagAPI().getWrapped(tag);
    }

    public static void initGlobal() {
        try {
            writeExplanation(FileHelper.get("impossible_data/what_is_this_folder.txt",false));
        } catch(IOException ex) {
            TILRef.logError("There was an error generating the data folder or explanation file!",ex);
        }
    }

    public static CompoundTagAPI<?> makeCompoundTag() {
        return getTagAPI().makeCompoundTag();
    }

    public static ListTagAPI<?> makeListTag() {
        return getTagAPI().makeListTag();
    }
    
    public static PrimitiveTagAPI<?> makePrimitiveTag(boolean b) {
        return getTagAPI().makePrimitiveTag(b);
    }
    
    public static PrimitiveTagAPI<?> makePrimitiveTag(byte b) {
        return getTagAPI().makePrimitiveTag(b);
    }
    
    public static PrimitiveTagAPI<?> makePrimitiveTag(double d) {
        return getTagAPI().makePrimitiveTag(d);
    }
    
    public static PrimitiveTagAPI<?> makePrimitiveTag(float f) {
        return getTagAPI().makePrimitiveTag(f);
    }
    
    public static PrimitiveTagAPI<?> makePrimitiveTag(int i) {
        return getTagAPI().makePrimitiveTag(i);
    }
    
    public static PrimitiveTagAPI<?> makePrimitiveTag(long l) {
        return getTagAPI().makePrimitiveTag(l);
    }
    
    public static PrimitiveTagAPI<?> makePrimitiveTag(short s) {
        return getTagAPI().makePrimitiveTag(s);
    }
    
    public static StringTagAPI<?> makeStringTag(String value) {
        return getTagAPI().makeStringTag(value);
    }
    

    public static CompoundTagAPI<?> readFromFile(File file) throws IOException {
        return getTagAPI().readFromFile(file);
    }

    private static void writeDataFile(CompoundTagAPI<?> data, File directory, String modid) throws IOException {
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
    public static void writeGlobalData(CompoundTagAPI<?> data, String modid) throws IOException {
        writeDataFile(data,TILRef.DATA_DIRECTORY,modid);
    }

    public static void writeToFile(CompoundTagAPI<?> data, File file) throws IOException {
        getTagAPI().writeToFile(data,file);
    }

    public static void writeWorldData(CompoundTagAPI<?> data, String modid) throws IOException {
        File saveDir = ServerHelper.getSaveDir();
        if(Objects.isNull(saveDir)) {
            TILRef.logError("Failed to get world directory!");
            return;
        }
        File dataFile = getWorldFile(saveDir,false);
        CompoundTagAPI<?> fileData = getWorldData(dataFile);
        fileData.putTag(modid,data);
        writeToFile(fileData,dataFile);
    }
}