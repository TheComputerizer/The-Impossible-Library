package mods.thecomputerizer.theimpossiblelibrary.util.file;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DataUtil {
    private static boolean GLOBAL_LOAD_FAILED = false;
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

    /**
     * This has to be called from the server
     */
    public static void writeWorldData(NBTTagCompound data, String modid, @Nonnull World world) throws IOException {
        if(FMLCommonHandler.instance().getSide()==Side.CLIENT) LogUtil.logInternal(Level.ERROR,"Mod with id {}" +
                " tried to write data to the wrong side! World data can only be written from the server!");
        else {
            NBTTagCompound globalTag = getGlobalData(modid, true);
            if (Objects.nonNull(globalTag)) {
                WorldInfo info = world.getSaveHandler().loadWorldInfo();
                if(Objects.nonNull(info)) {
                    getOrCreateCompound(globalTag, "world_data").setTag(info.getWorldName(), data);
                    writeGlobalData(data, modid);
                }
            }
        }
    }

    /**
     * Returns null if the data is unable to be read for some reason. This has to be called from the server
     */
    public static NBTTagCompound readWorldData(NBTTagCompound data, String modid, @Nonnull World world) {
        if(FMLCommonHandler.instance().getSide()==Side.CLIENT) LogUtil.logInternal(Level.ERROR,"Mod with id {}" +
                " tried to write data to the wrong side! World data can only be written from the server!");
        else {
            WorldInfo info = world.getSaveHandler().loadWorldInfo();
            if(Objects.nonNull(info)) {
                try {
                    NBTTagCompound globalTag = getGlobalData(modid, true);
                    if (Objects.nonNull(globalTag))
                        return getOrCreateCompound(getOrCreateCompound(globalTag, "world_data"), info.getWorldName());
                } catch (IOException ex) {
                    LogUtil.logInternal(Level.ERROR, "Unable to read mod data for modid {} in world {}", modid, info.getWorldName(), ex);
                }
            }
        }
        return null;
    }

    /**
        Writes global nbt data to a dat file for a given modid
        Will fail if the data folder failed to initialize or the data module is turned off
     */
    public static void writeGlobalData(NBTTagCompound data, String modid) throws IOException {
        if(!GLOBAL_LOAD_FAILED) writeFileData(data, Constants.DATA_DIRECTORY, modid);
        else LogUtil.logInternal(Level.WARN, "There was a problem when initializing global data for The Impossible Library so data for {} could not be written",modid);
    }

    /**
        Gets global data stored in a dat file for the modid input
        Returns null if the file does not exist and is not set to be created
        Will also return null if the data folder failed to initialize or the data module is turned off.
     */
    public static NBTTagCompound getGlobalData(String modid, boolean createIfAbsent) throws IOException {
        if(!GLOBAL_LOAD_FAILED) return getFileData(Constants.DATA_DIRECTORY, modid, createIfAbsent);
        LogUtil.logInternal(Level.WARN, "There was a problem when initializing global data for The Impossible Library so data for {} could not be retrieved",modid);
        return null;
    }

    private static void writeFileData(NBTTagCompound data, File directory, String modid) throws IOException {
        File dataFile = new File(directory, modid + ".dat");
        dataFile = FileUtil.generateNestedFile(dataFile,true);
        if (Objects.nonNull(dataFile)) CompressedStreamTools.write(data, dataFile);
        else LogUtil.logInternal(Level.ERROR,"Could not write data for {} due to an error in creating the file",modid);
    }

    private static NBTTagCompound getFileData(File directory, String modid, boolean createIfAbsent) throws IOException {
        File dataFile = new File(directory, modid + ".dat");
        if (dataFile.exists()) return CompressedStreamTools.read(dataFile);
        if (createIfAbsent) {
            dataFile = FileUtil.generateNestedFile(dataFile,false);
            if (Objects.nonNull(dataFile)) return CompressedStreamTools.read(dataFile);
        }
        return null;
    }

    /**
     * Throws an IOException if the key already exists as and is not of the type NBTTagCompound
     */
    public static NBTTagCompound getOrCreateCompound(NBTTagCompound tag, String key) throws IOException {
        if(tag.hasKey(key)) {
            NBTBase baseTag = tag.getTag(key);
            if(!(baseTag instanceof NBTTagCompound)) throw new IOException("Tried to get existing tag of the wrong type!");
            return (NBTTagCompound)baseTag;
        }
        NBTTagCompound compound = new NBTTagCompound();
        tag.setTag(key,compound);
        return compound;
    }
}
