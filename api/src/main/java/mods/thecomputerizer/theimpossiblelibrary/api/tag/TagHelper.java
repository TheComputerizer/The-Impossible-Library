package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.server.ServerHelper;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.DATA_DIRECTORY;

@SuppressWarnings("SameParameterValue")
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
    
    public static File getDataDirectory() {
        return new File("/"+DATA_DIRECTORY);
    }
    
    public static File getDataDirectory(@Nullable File parent) {
        File file = Objects.nonNull(parent) ? new File(parent,"/"+DATA_DIRECTORY) : getDataDirectory();
        try {
            if(!file.exists()) Files.createDirectory(file.toPath());
        } catch(IOException ex) {
            TILRef.logError("Failed to create data directory at {}",file,ex);
        }
        return file;
    }
    
    private static CompoundTagAPI<?> getFileData(File directory, String modid, boolean createIfAbsent) throws IOException {
        File dataFile = new File(directory,modid+".dat");
        if(dataFile.exists()) return readFromFile(dataFile);
        return makeCompoundTag();
    }

    /**
     * Gets global data stored in a dat file for the modid input.
     * Returns null if the file does not exist and is not set to be created.
     * Will also return null if the data folder failed to initialize or the data module is turned off.
     */
    @IndirectCallers
    public static CompoundTagAPI<?> getGlobalData(String modid, boolean createIfAbsent) throws IOException {
        return getFileData(getDataDirectory(),modid,createIfAbsent);
    }

    /**
     * Throws an IOException if the key already exists as and is not of the type CompoundTag
     */
    @IndirectCallers
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
    @IndirectCallers
    public static ListTagAPI<?> getOrCreateList(CompoundTagAPI<?> tag, String key) {
        if(tag.contains(key)) return tag.getListTag(key);
        ListTagAPI<?> list = makeListTag();
        tag.putTag(key,list);
        return list;
    }

    public static TagAPI getTagAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getTag);
    }
    
    @IndirectCallers
    public static CompoundTagAPI<?> getWorldData(String modid) {
        File dataFile = getWorldDataFile(modid);
        try {
            return Objects.nonNull(dataFile) ? readFromFile(dataFile) : makeCompoundTag();
        } catch(IOException ex) {
            TILRef.logError("Failed to get world data for {}",modid);
        }
        return makeCompoundTag();
    }
    
    public static File getWorldDataFile(String modid) {
        File saveDir = ServerHelper.getSaveDir();
        if(Objects.isNull(saveDir)) {
            TILRef.logError("Failed to get world directory!");
            return null;
        }
        return new File(getDataDirectory(saveDir),modid+".dat");
    }
    
    public static <T> BaseTagAPI<T> getWrapped(T tag) {
        return getTagAPI().getWrapped(tag);
    }

    public static void initGlobal() {
        try {
            writeExplanation(FileHelper.get(getDataDirectory()+"/"+"what_is_this_folder.txt"));
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
    
    @IndirectCallers
    public static PrimitiveTagAPI<?> makePrimitiveTag(boolean b) {
        return getTagAPI().makePrimitiveTag(b);
    }
    
    @IndirectCallers
    public static PrimitiveTagAPI<?> makePrimitiveTag(byte b) {
        return getTagAPI().makePrimitiveTag(b);
    }
    
    @IndirectCallers
    public static PrimitiveTagAPI<?> makePrimitiveTag(double d) {
        return getTagAPI().makePrimitiveTag(d);
    }
    
    @IndirectCallers
    public static PrimitiveTagAPI<?> makePrimitiveTag(float f) {
        return getTagAPI().makePrimitiveTag(f);
    }
    
    @IndirectCallers
    public static PrimitiveTagAPI<?> makePrimitiveTag(int i) {
        return getTagAPI().makePrimitiveTag(i);
    }
    
    @IndirectCallers
    public static PrimitiveTagAPI<?> makePrimitiveTag(long l) {
        return getTagAPI().makePrimitiveTag(l);
    }
    
    @IndirectCallers
    public static PrimitiveTagAPI<?> makePrimitiveTag(short s) {
        return getTagAPI().makePrimitiveTag(s);
    }
    
    @IndirectCallers
    public static StringTagAPI<?> makeStringTag(String value) {
        return getTagAPI().makeStringTag(value);
    }

    public static CompoundTagAPI<?> readFromFile(File file) throws IOException {
        return getTagAPI().readFromFile(file);
    }

    private static void writeDataFile(CompoundTagAPI<?> data, File directory, String modid) throws IOException {
        File dataFile = FileHelper.get(directory,modid+".dat",true);
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
    @IndirectCallers
    public static void writeGlobalData(CompoundTagAPI<?> data, String modid) throws IOException {
        writeDataFile(data,getDataDirectory(),modid);
    }

    public static void writeToFile(CompoundTagAPI<?> data, File file) throws IOException {
        getTagAPI().writeToFile(data,file);
    }

    @IndirectCallers
    public static void writeWorldData(CompoundTagAPI<?> data, String modid) throws IOException {
        File dataFile = getWorldDataFile(modid);
        if(Objects.nonNull(dataFile)) writeToFile(data,dataFile);
        else TILRef.logError("Failed to write world data for {}! Data file is null",modid);
    }
}